package ject.componote.infra.oauth.error.profile.response.detail;

import ject.componote.infra.oauth.error.profile.response.OAuthProfileErrorResponse;

public record GithubProfileErrorResponse(String error, String error_description, String error_uri) implements OAuthProfileErrorResponse {
    @Override
    public String getErrorCode() {
        return error;
    }

    @Override
    public String getMessage() {
        return error_description + "에러 URI: " + error_uri;
    }
}
