package ject.componote.domain.auth.dto.signup.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemberSignupRequest(
        @NotBlank String nickname,
        @NotBlank String job,
        @Nullable String profileImageTempKey,
        @NotNull Long socialAccountId) {
}
