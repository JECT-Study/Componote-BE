package ject.componote.infra.oauth.application.token;

import ject.componote.infra.util.TimeoutDecorator;
import ject.componote.infra.oauth.dto.token.response.OAuthTokenResponse;
import ject.componote.infra.oauth.error.token.OAuthTokenIssueException;
import ject.componote.infra.oauth.model.OAuthProvider;
import ject.componote.infra.oauth.util.converter.OAuthParamConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OAuthTokenService {
    private final OAuthParamConverter oAuthParamConverter;
    private final TimeoutDecorator timeoutDecorator;
    private final TokenIssueFailHandler tokenIssueFailHandler;
    private final WebClient webClient;

    public Mono<OAuthTokenResponse> getToken(final OAuthProvider oAuthProvider, final String code, final int maxRetry, final int timeout) {
        return timeoutDecorator.decorate(getTokenFromCode(oAuthProvider, code), maxRetry, timeout)
                .flatMap(response -> {
                    if (failedTokenIssue(response)) {
                        return Mono.error(OAuthTokenIssueException.createWhenResponseIsNullOrEmpty());
                    }

                    return Mono.just(response);
                });
    }

    private Mono<OAuthTokenResponse> getTokenFromCode(final OAuthProvider provider, final String code) {
        return webClient.method(provider.tokenMethod())
                .uri(provider.tokenUrl())
                .accept(MediaType.parseMediaType("application/vnd.github+json"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(oAuthParamConverter.convertToTokenParams(provider, code)))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> tokenIssueFailHandler.handle4xxError(clientResponse, provider))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> tokenIssueFailHandler.handle5xxError(clientResponse, provider))
                .bodyToMono(OAuthTokenResponse.class);
    }

    private boolean failedTokenIssue(final OAuthTokenResponse oAuthTokenResponse) {
        return Objects.isNull(oAuthTokenResponse) || oAuthTokenResponse.access_token() == null || oAuthTokenResponse.access_token().isEmpty();
    }
}
