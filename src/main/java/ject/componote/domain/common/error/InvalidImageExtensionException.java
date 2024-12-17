package ject.componote.domain.common.error;

import org.springframework.http.HttpStatus;

public class InvalidImageExtensionException extends ImageException {
    public InvalidImageExtensionException(final String extension) {
        super("이미지 확장자가 올바르지 않습니다. 입력된 확장자: " + extension, HttpStatus.BAD_REQUEST);
    }
}
