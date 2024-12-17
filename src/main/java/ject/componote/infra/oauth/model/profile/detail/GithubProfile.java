package ject.componote.infra.oauth.model.profile.detail;

import ject.componote.infra.oauth.model.profile.OAuthProfile;

import java.util.Map;

public class GithubProfile extends OAuthProfile {
    public GithubProfile(final Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getProviderType() {
        return "GITHUB";
    }

    @Override
    public String getSocialId() {
        return String.valueOf(attributes.get("id"));
    }
}
