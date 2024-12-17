package ject.componote.domain.auth.error;

import org.springframework.http.HttpStatus;

public class DuplicatedSignupException extends AuthException {
    public DuplicatedSignupException(final Long socialAccountId) {
        super("해당 소셜 ID로 이미 가입된 계정이 있습니다. 소셜 ID: " + socialAccountId, HttpStatus.BAD_REQUEST);
    }
}
