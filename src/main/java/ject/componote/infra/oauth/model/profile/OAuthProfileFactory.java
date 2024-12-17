package ject.componote.infra.oauth.model.profile;

import ject.componote.infra.oauth.error.UnsupportedProviderException;
import ject.componote.infra.oauth.model.OAuthProvider;
import ject.componote.infra.oauth.model.profile.detail.GoogleProfile;
import ject.componote.infra.oauth.model.profile.detail.GithubProfile;
import ject.componote.infra.oauth.model.profile.detail.NaverProfile;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthProfileFactory {
    KAKAO("github", GithubProfile::new),
    NAVER("naver", NaverProfile::new),
    GOOGLE("google", GoogleProfile::new);

    private final String providerName;
    private final Function<Map<String, Object>, OAuthProfile> mapper;

    OAuthProfileFactory(final String providerName,
                        final Function<Map<String, Object>, OAuthProfile> mapper) {
        this.providerName = providerName;
        this.mapper = mapper;
    }

    public static OAuthProfile of(final Map<String, Object> attributes,
                                  final OAuthProvider oAuthProvider) {
        return Arrays.stream(values())
                .filter(value -> value.providerName.equals(oAuthProvider.name().toLowerCase()))
                .findAny()
                .map(value -> value.mapper.apply(attributes))
                .orElseThrow(() -> new UnsupportedProviderException(oAuthProvider));
    }
}
