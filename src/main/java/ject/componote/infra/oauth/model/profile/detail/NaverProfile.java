package ject.componote.infra.oauth.model.profile.detail;

import ject.componote.infra.oauth.model.profile.OAuthProfile;

import java.util.Map;

public class NaverProfile extends OAuthProfile {
    public NaverProfile(final Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getSocialId() {
        return (String) getResponse().get("id");
    }

    @Override
    public String getProviderType() {
        return "NAVER";
    }

    private Map<String, Object> getResponse() {
        return (Map<String, Object>) attributes.get("response");
    }
}
