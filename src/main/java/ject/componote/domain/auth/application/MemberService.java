package ject.componote.domain.auth.application;

import ject.componote.domain.auth.domain.MemberRepository;
import ject.componote.domain.auth.dto.find.response.MemberSummaryResponse;
import ject.componote.domain.auth.error.NotFoundMemberException;
import ject.componote.domain.auth.model.AuthPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberSummaryResponse getSummary(final AuthPrincipal authPrincipal) {
        final Long memberId = authPrincipal.id();
        return memberRepository.findSummaryById(memberId)
                .map(MemberSummaryResponse::from)
                .orElseThrow(() -> NotFoundMemberException.createWhenInvalidMemberId(memberId));
    }
}
