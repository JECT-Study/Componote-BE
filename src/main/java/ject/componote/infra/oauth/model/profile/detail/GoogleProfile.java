package ject.componote.infra.oauth.model.profile.detail;

import ject.componote.infra.oauth.model.profile.OAuthProfile;

import java.util.Map;

public class GoogleProfile extends OAuthProfile {
    public GoogleProfile(final Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getProviderType() {
        return "GOOGLE";
    }

    @Override
    public String getSocialId() {
        return (String) attributes.get("id");
    }
}
