package ject.componote.infra.oauth.application;

import ject.componote.infra.oauth.application.authorize.OAuthAuthorizeService;
import ject.componote.infra.oauth.application.profile.OAuthProfileService;
import ject.componote.infra.oauth.application.token.OAuthTokenService;
import ject.componote.infra.oauth.dto.authorize.response.OAuthAuthorizePayload;
import ject.componote.infra.oauth.error.param.OAuthParamException;
import ject.componote.infra.oauth.model.OAuthProvider;
import ject.componote.infra.oauth.model.profile.OAuthProfile;
import ject.componote.infra.oauth.repository.InMemoryOAuthProviderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static ject.componote.infra.oauth.error.param.OAuthParamErrorCode.INVALID_AUTHORIZATION_CODE;
import static ject.componote.infra.oauth.error.param.OAuthParamErrorCode.INVALID_PROVIDER_TYPE;

@Service
public class OAuthClient {
    private final int timeout;
    private final int maxRetry;
    private final InMemoryOAuthProviderRepository inMemoryOAuthProviderRepository;
    private final OAuthAuthorizeService oAuthAuthorizeService;
    private final OAuthProfileService oAuthProfileService;
    private final OAuthTokenService oAuthTokenService;

    public OAuthClient(@Value("${oauth.timeout}") final int timeout,
                       @Value("${oauth.max-retry}") final int maxRetry,
                       final InMemoryOAuthProviderRepository inMemoryOAuthProviderRepository,
                       final OAuthAuthorizeService oAuthAuthorizeService,
                       final OAuthProfileService oAuthProfileService,
                       final OAuthTokenService oAuthTokenService) {
        this.timeout = timeout;
        this.maxRetry = maxRetry;
        this.inMemoryOAuthProviderRepository = inMemoryOAuthProviderRepository;
        this.oAuthAuthorizeService = oAuthAuthorizeService;
        this.oAuthProfileService = oAuthProfileService;
        this.oAuthTokenService = oAuthTokenService;
    }

    public OAuthAuthorizePayload getAuthorizePayload(final String providerType) {
        validateProviderType(providerType);
        final OAuthProvider oAuthProvider = getProviderByName(providerType);
        return oAuthAuthorizeService.getAuthorizePayload(oAuthProvider);
    }

    public Mono<OAuthProfile> getProfile(final String providerType, final String code) {
        validateAuthorizationCode(code);
        validateProviderType(providerType);
        final OAuthProvider oAuthProvider = getProviderByName(providerType);
        return oAuthTokenService.getToken(oAuthProvider, code, maxRetry, timeout)
                .flatMap(response -> oAuthProfileService.getProfile(oAuthProvider, response, maxRetry, timeout));
    }

    private OAuthProvider getProviderByName(final String providerType) {
        return inMemoryOAuthProviderRepository.findByProviderType(providerType);
    }

    private void validateAuthorizationCode(final String code) {
        if (isNullOrBlank(code)) {
            throw new OAuthParamException(INVALID_AUTHORIZATION_CODE);   // 예외 처리 커스텀 예정
        }
    }

    private void validateProviderType(final String providerType) {
        if (isNullOrBlank(providerType)) {
            throw new OAuthParamException(INVALID_PROVIDER_TYPE);   // 예외 처리 커스텀 예정
        }
    }

    private boolean isNullOrBlank(final String value) {
        return value == null || value.isBlank();
    }
}
