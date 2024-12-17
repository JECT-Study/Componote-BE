package ject.componote.infra.oauth.error.token.response.detail;

import ject.componote.infra.oauth.error.token.response.OAuthTokenIssueErrorResponse;

public record GithubTokenIssueErrorResponse(String error, String error_description, String error_uri) implements OAuthTokenIssueErrorResponse {
    @Override
    public String getErrorCode() {
        return error;
    }

    @Override
    public String getMessage() {
        return error_description + " 에러 URI: " + error_uri;
    }
}
