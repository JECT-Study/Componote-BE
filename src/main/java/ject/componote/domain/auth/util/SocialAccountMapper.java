package ject.componote.domain.auth.util;

import ject.componote.domain.auth.domain.SocialAccount;
import ject.componote.infra.oauth.model.profile.OAuthProfile;
import org.springframework.stereotype.Component;

@Component
public class SocialAccountMapper {
    public SocialAccount mapFrom(final OAuthProfile oAuthProfile) {
        return SocialAccount.of(oAuthProfile.getSocialId(), oAuthProfile.getProviderType());
    }
}
