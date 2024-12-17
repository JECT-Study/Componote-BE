package ject.componote.domain.auth.util;

import ject.componote.domain.auth.domain.Member;
import ject.componote.domain.auth.dto.signup.request.MemberSignupRequest;
import ject.componote.domain.common.model.Image;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    private static final Image DEFAULT_PROFILE_IMAGE = Image.from("/permanent/default-profile-image.png");

    public Member mapFrom(final MemberSignupRequest request, final String permanentKey) {
        final Image profileImage = getProfileImage(request, permanentKey);
        return Member.of(
                request.nickname(),
                request.job(),
                profileImage,
                request.socialAccountId()
        );
    }

    private Image getProfileImage(final MemberSignupRequest request, final String permanentKey) {
        if (request.profileImageTempKey() == null) {
            return DEFAULT_PROFILE_IMAGE;
        }

        return Image.from(permanentKey);
    }
}
