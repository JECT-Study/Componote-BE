package ject.componote.domain.auth.dto.signup.response;

import ject.componote.domain.auth.domain.Member;

public record MemberSignupResponse(Long memberId) {
    public static MemberSignupResponse from(final Member member) {
        return new MemberSignupResponse(member.getId());
    }
}
