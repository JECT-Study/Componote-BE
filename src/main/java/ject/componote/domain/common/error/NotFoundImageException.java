package ject.componote.domain.common.error;

import org.springframework.http.HttpStatus;

public class NotFoundImageException extends ImageException {
    public NotFoundImageException() {
        super("이미지의 objectKey가 없습니다.", HttpStatus.NOT_FOUND);
    }
}
