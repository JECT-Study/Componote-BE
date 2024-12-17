package ject.componote.domain.auth.error;

import org.springframework.http.HttpStatus;

public class NotFoundSocialAccountException extends AuthException {
    public NotFoundSocialAccountException(final Long socialAccountId) {
        super("일치하는 소셜 정보를 찾을 수 없습니다. 소셜 ID: " + socialAccountId, HttpStatus.NOT_FOUND);
    }
}
