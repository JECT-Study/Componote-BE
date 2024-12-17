package ject.componote.fixture;

import ject.componote.domain.auth.domain.SocialAccount;

public enum SocialAccountFixture {
    KIM("123", "google");

    private final String socialId;
    private final String providerType;

    SocialAccountFixture(final String socialId, final String providerType) {
        this.socialId = socialId;
        this.providerType = providerType;
    }

    public SocialAccount 생성() {
        return SocialAccount.of(socialId, providerType);
    }

    public SocialAccount 생성(final String providerType) {
        return SocialAccount.of(socialId, providerType);
    }
}
