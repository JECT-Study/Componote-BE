package ject.componote.infra.oauth.error;

public class InvalidAuthorizationCodeException extends OAuthClientException {
    public InvalidAuthorizationCodeException(final String code) {
        super("인가 코드가 잘못되었습니다. code: " + code);
    }
}
