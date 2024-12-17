package ject.componote.domain.auth.application;

import ject.componote.domain.auth.domain.MemberRepository;
import ject.componote.domain.auth.domain.SocialAccount;
import ject.componote.domain.auth.dto.login.response.OAuthLoginResponse;
import ject.componote.domain.auth.dto.authorize.response.OAuthAuthorizationUrlResponse;
import ject.componote.infra.oauth.application.OAuthClient;
import ject.componote.infra.oauth.dto.authorize.response.OAuthAuthorizePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final MemberRepository memberRepository;
    private final OAuthClient oAuthClient;
    private final OAuthResultHandler oauthResultHandler;

    public OAuthAuthorizationUrlResponse getOAuthAuthorizationCodeUrl(final String providerType) {
        final OAuthAuthorizePayload oAuthAuthorizePayload = oAuthClient.getAuthorizePayload(providerType);
        return OAuthAuthorizationUrlResponse.from(oAuthAuthorizePayload);
    }

    public OAuthLoginResponse login(final String providerType, final String code) {
        final SocialAccount socialAccount = oAuthClient.getProfile(providerType, code)
                .publishOn(Schedulers.boundedElastic())
                .map(oauthResultHandler::saveOrGet)
                .block();
        final boolean isRegister = memberRepository.existsBySocialAccountId(socialAccount.getId());
        return OAuthLoginResponse.of(isRegister, socialAccount);
    }
}
