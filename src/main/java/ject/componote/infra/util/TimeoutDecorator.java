package ject.componote.infra.util;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Component
public class TimeoutDecorator {
    public <T> Mono<T> decorate(final Mono<T> mono, final int maxRetry, final int timeout) {
        return mono.timeout(Duration.ofMillis(timeout))
                .retryWhen(Retry.max(maxRetry).filter(this::isRetryable))
                .onErrorMap(TimeoutException.class, e -> new IllegalStateException("요청 시간이 초과되었습니다.", e));
    }

    private boolean isRetryable(final Throwable ex) {
        return (ex instanceof IllegalStateException) || (ex instanceof TimeoutException);
    }
}
