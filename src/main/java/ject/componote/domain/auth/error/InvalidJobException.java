package ject.componote.domain.auth.error;

import org.springframework.http.HttpStatus;

public class InvalidJobException extends AuthException {

    public InvalidJobException(final String value) {
        super("직종이 올바르지 않습니다. 입력한 직종: " + value, HttpStatus.BAD_REQUEST);
    }
}
