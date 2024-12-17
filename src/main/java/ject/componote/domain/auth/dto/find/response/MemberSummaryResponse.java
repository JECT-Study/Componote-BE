package ject.componote.domain.auth.dto.find.response;

import ject.componote.domain.auth.dto.find.MemberSummaryDto;

public record MemberSummaryResponse(String nickname, String profilePhotoUrl) {
    public static MemberSummaryResponse from(MemberSummaryDto memberSummaryDto) {
        return new MemberSummaryResponse(
                memberSummaryDto.nickname().getValue(),
                memberSummaryDto.profilePhoto().getObjectKey()
        );
    }
}
