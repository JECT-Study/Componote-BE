package ject.componote.infra.oauth.error;

import ject.componote.infra.oauth.error.profile.response.OAuthProfileErrorResponse;
import ject.componote.infra.oauth.error.profile.response.detail.GoogleProfileErrorResponse;
import ject.componote.infra.oauth.error.profile.response.detail.GithubProfileErrorResponse;
import ject.componote.infra.oauth.error.profile.response.detail.NaverProfileErrorResponse;
import ject.componote.infra.oauth.error.token.response.OAuthTokenIssueErrorResponse;
import ject.componote.infra.oauth.error.token.response.detail.GoogleTokenIssueErrorResponse;
import ject.componote.infra.oauth.error.token.response.detail.GithubTokenIssueErrorResponse;
import ject.componote.infra.oauth.error.token.response.detail.NaverTokenIssueErrorResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OAuthErrorResponseFactory {
    private static final Map<String, ProviderErrorResponseConfig> errorResponseConfigMap = new HashMap<>();

    static {
        errorResponseConfigMap.put("github", new ProviderErrorResponseConfig(
                GithubProfileErrorResponse.class, GithubTokenIssueErrorResponse.class)
        );
        errorResponseConfigMap.put("naver", new ProviderErrorResponseConfig(
                NaverProfileErrorResponse.class, NaverTokenIssueErrorResponse.class)
        );
        errorResponseConfigMap.put("google", new ProviderErrorResponseConfig(
                GoogleProfileErrorResponse.class, GoogleTokenIssueErrorResponse.class)
        );
    }

    public static Class<? extends OAuthProfileErrorResponse> getProfileResponseClassFrom(final String providerName) {
        return getProviderErrorResponseConfig(providerName).profileErrorResponseClass;
    }

    public static Class<? extends OAuthTokenIssueErrorResponse> getTokenIssueResponseClassFrom(final String providerName) {
        return getProviderErrorResponseConfig(providerName).tokenIssueErrorResponseClass;
    }

    private static ProviderErrorResponseConfig getProviderErrorResponseConfig(final String providerName) {
        final ProviderErrorResponseConfig config = errorResponseConfigMap.get(providerName);
        if (config == null) {
            throw new UnsupportedProviderException(providerName);
        }
        return config;
    }

    private record ProviderErrorResponseConfig(
            Class<? extends OAuthProfileErrorResponse> profileErrorResponseClass,
            Class<? extends OAuthTokenIssueErrorResponse> tokenIssueErrorResponseClass) {
    }
}
