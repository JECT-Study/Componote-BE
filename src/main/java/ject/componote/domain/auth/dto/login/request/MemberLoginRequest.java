package ject.componote.domain.auth.dto.login.request;

import jakarta.validation.constraints.NotNull;

public record MemberLoginRequest(@NotNull Long socialAccountId) {
}
