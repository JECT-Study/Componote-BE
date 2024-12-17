package ject.componote.domain.auth.error;

import org.springframework.http.HttpStatus;

public class NotFoundMemberException extends AuthException {
    private NotFoundMemberException(final String message, final HttpStatus status) {
        super(message, status);
    }

    public static NotFoundMemberException createWhenInvalidMemberId(final Long memberId) {
        return new NotFoundMemberException("일치하는 회원을 찾을 수 없습니다. 회원 ID: " + memberId, HttpStatus.NOT_FOUND);
    }

    public static NotFoundMemberException createWhenInvalidSocialAccountId(final Long socialAccountId) {
        return new NotFoundMemberException("소셜 ID에 해당하는 회원이 없습니다. 소셜 ID: " + socialAccountId, HttpStatus.NOT_FOUND);
    }
}
