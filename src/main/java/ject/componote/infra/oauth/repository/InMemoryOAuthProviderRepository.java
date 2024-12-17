package ject.componote.infra.oauth.repository;

import ject.componote.infra.oauth.error.UnsupportedProviderException;
import ject.componote.infra.oauth.model.OAuthProvider;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class InMemoryOAuthProviderRepository {
    private final Map<String, OAuthProvider> providers;

    public OAuthProvider findByProviderType(final String providerType) {
        if (!providers.containsKey(providerType)) {
            throw new UnsupportedProviderException(providerType);
        }
        return providers.get(providerType);
    }
}
