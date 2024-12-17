package ject.componote.infra.oauth.model.profile;

import java.util.Map;

public abstract class OAuthProfile {
    protected final Map<String, Object> attributes;

    protected OAuthProfile(final Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getProviderType();
    public abstract String getSocialId();
}
