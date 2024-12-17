package ject.componote.domain.auth.application;

import ject.componote.domain.auth.domain.SocialAccount;
import ject.componote.domain.auth.domain.SocialAccountRepository;
import ject.componote.domain.auth.util.SocialAccountMapper;
import ject.componote.infra.oauth.model.profile.OAuthProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OAuthResultHandler {
    private final SocialAccountRepository socialAccountRepository;
    private final SocialAccountMapper socialAccountMapper;

    @Transactional
    public SocialAccount saveOrGet(final OAuthProfile oAuthProfile) {
        return socialAccountRepository.findBySocialIdAndProviderType(oAuthProfile.getSocialId(), oAuthProfile.getProviderType())
                .orElseGet(() -> socialAccountRepository.save(socialAccountMapper.mapFrom(oAuthProfile)));
    }
}
