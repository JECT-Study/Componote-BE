package ject.componote.fixture;

import ject.componote.domain.auth.domain.Member;
import ject.componote.domain.common.model.Image;

public enum MemberFixture {
    KIM("김민우", "DEVELOPER", "permanent/123.jpg");

    private final String nickname;
    private final String job;
    private final String profileImage;

    MemberFixture(final String nickname, final String job, final String profileImage) {
        this.nickname = nickname;
        this.job = job;
        this.profileImage = profileImage;
    }

    public Member 생성(final Long socialAccountId) {
        return Member.of(nickname, job, Image.from(profileImage), socialAccountId);
    }
}
