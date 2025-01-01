package ject.componote.infra.oauth.error.token;

import ject.componote.infra.oauth.error.OAuthClientException;
import ject.componote.infra.oauth.error.token.response.OAuthTokenIssueErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OAuthTokenIssueException extends OAuthClientException {
    public OAuthTokenIssueException(final OAuthTokenIssueErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        log.error("소셜 토큰 발급 실패. 에러 코드 : {}, 에러 메시지 : {}", errorResponse.getErrorCode(), errorResponse.getMessage());
    }

    private OAuthTokenIssueException(final String message) {
        super(message);
    }

    public static OAuthTokenIssueException createWhenResponseIsNullOrEmpty() {
        return new OAuthTokenIssueException("소셜 토큰 발급 API 호출은 성공했으나 응답값에 엑세스 토큰이 존재하지 않습니다. 네이버, gitHub 소셜 로그인의 경우 인가 코드를 확인하세요.");
    }
}
