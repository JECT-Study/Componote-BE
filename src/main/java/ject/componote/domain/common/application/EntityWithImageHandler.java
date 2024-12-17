package ject.componote.domain.common.application;

import ject.componote.global.util.ObjectKeyTransformer;
import ject.componote.infra.file.application.FileClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.scheduler.Schedulers;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EntityWithImageHandler {
    private final FileClient fileClient;

    @Transactional
    public <T> T persist(final String tempKey, final Function<String, T> callback) {
        if (tempKey == null) {
            return callback.apply(null);
        }

        final String permanentKey = ObjectKeyTransformer.toPermanentKey(tempKey);
        fileClient.moveFile(tempKey, permanentKey)
                .publishOn(Schedulers.boundedElastic())
                .block();
        return callback.apply(permanentKey);
    }
}
