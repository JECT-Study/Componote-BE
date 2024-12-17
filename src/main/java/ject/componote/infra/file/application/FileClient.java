package ject.componote.infra.file.application;

import ject.componote.global.error.ErrorResponse;
import ject.componote.infra.file.dto.move.request.MoveRequest;
import ject.componote.infra.file.error.FileClientException;
import ject.componote.infra.util.TimeoutDecorator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class FileClient {
    private final int maxRetry;
    private final int timeout;
    private final HttpMethod method;
    private final String uri;
    private final TimeoutDecorator timeoutDecorator;
    private final WebClient webClient;

    public FileClient(@Value("${file.max-retry}") final int maxRetry,
                      @Value("${file.timeout}") final int timeout,
                      @Value("${file.client.move.method}") final HttpMethod method,
                      @Value("${file.client.move.uri}") final String uri,
                      final TimeoutDecorator timeoutDecorator,
                      final WebClient webClient) {
        this.maxRetry = maxRetry;
        this.timeout = timeout;
        this.method = method;
        this.uri = uri;
        this.timeoutDecorator = timeoutDecorator;
        this.webClient = webClient;
    }

    public Mono<Void> moveFile(final String tempKey, final String permanentKey) {
        return timeoutDecorator.decorate(move(tempKey, permanentKey), maxRetry, timeout);
    }

    private Mono<Void> move(final String tempKey, final String permanentKey) {
        return webClient.method(method)
                .uri(uri)
                .bodyValue(MoveRequest.of(tempKey, permanentKey))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                .toBodilessEntity()
                .then()
                .doOnSuccess(ignore -> handleSuccess(permanentKey))
                .doOnError(error -> handleError(tempKey, permanentKey, error));
    }

    private Mono<? extends Throwable> handle5xxError(final ClientResponse clientResponse) {
        return clientResponse.bodyToMono(ErrorResponse.class)
                .map(ErrorResponse::getMessage)
                .map(IllegalStateException::new);
    }

    private Mono<? extends Throwable> handle4xxError(final ClientResponse clientResponse) {
        return clientResponse.bodyToMono(ErrorResponse.class)
                .map(FileClientException::new);
    }

    private void handleSuccess(final String permanentKey) {
        log.info("File moved successfully: {}", permanentKey);
    }


    private void handleError(final String tempKey, final String permanentKey, final Throwable error) {
        log.error("Failed to move file from {} to {}: {}", tempKey, permanentKey, error.getMessage());
    }
}
