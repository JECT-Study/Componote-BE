package ject.componote.domain.auth.dto.authorize.response;

import ject.componote.infra.oauth.dto.authorize.response.OAuthAuthorizePayload;

public record OAuthAuthorizationUrlResponse(String method, String url) {
    public static OAuthAuthorizationUrlResponse from(final OAuthAuthorizePayload oAuthAuthorizePayload) {
        return new OAuthAuthorizationUrlResponse(oAuthAuthorizePayload.method().name(), oAuthAuthorizePayload.url().toString());
    }
}
