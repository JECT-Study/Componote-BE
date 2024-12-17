package ject.componote.domain.auth.dto.login.response;

import ject.componote.domain.auth.domain.SocialAccount;

public record OAuthLoginResponse(boolean isRegister, Long socialAccountId) {
    public static OAuthLoginResponse of(final boolean isRegister, final SocialAccount socialAccount) {
        return new OAuthLoginResponse(isRegister, socialAccount.getId());
    }
}
