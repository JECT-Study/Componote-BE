package ject.componote.domain.auth.dto.login.response;

import ject.componote.domain.auth.domain.Member;

public record MemberLoginResponse(String accessToken, Long memberId) {
    public static MemberLoginResponse of(final String accessToken, final Member member) {
        return new MemberLoginResponse(accessToken, member.getId());
    }
}
