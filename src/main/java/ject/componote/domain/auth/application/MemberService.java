package ject.componote.domain.auth.application;

import ject.componote.domain.auth.domain.Member;
import ject.componote.domain.auth.dao.MemberRepository;
import ject.componote.domain.auth.dto.find.response.MemberSummaryResponse;
import ject.componote.domain.auth.dto.update.request.MemberNicknameUpdateRequest;
import ject.componote.domain.auth.dto.update.request.MemberProfileImageUpdateRequest;
import ject.componote.domain.auth.error.DuplicatedNicknameException;
import ject.componote.domain.auth.error.NotFoundMemberException;
import ject.componote.domain.auth.model.AuthPrincipal;
import ject.componote.domain.auth.model.Nickname;
import ject.componote.domain.auth.model.ProfileImage;
import ject.componote.infra.file.application.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final FileService fileService;
    private final MemberRepository memberRepository;

    public MemberSummaryResponse getMemberSummary(final AuthPrincipal authPrincipal) {
        final Long memberId = authPrincipal.id();
        return memberRepository.findSummaryById(memberId)
                .map(MemberSummaryResponse::from)
                .orElseThrow(() -> NotFoundMemberException.createWhenInvalidMemberId(memberId));
    }

    @Transactional
    public void updateProfileImage(final AuthPrincipal authPrincipal, final MemberProfileImageUpdateRequest request) {
        final Member member = findMemberById(authPrincipal.id());
        final ProfileImage profileImage = ProfileImage.from(request.profileImageObjectKey());
        if (member.equalsProfileImage(profileImage)) {
            return;
        }

        fileService.moveImage(profileImage);
        member.updateProfileImage(profileImage);
        memberRepository.save(member);
    }

    @Transactional
    public void updateNickname(final AuthPrincipal authPrincipal, final MemberNicknameUpdateRequest request) {
        final Member member = findMemberById(authPrincipal.id());
        final Nickname nickname = Nickname.from(request.nickname());
        if (member.equalsNickname(nickname)) {
            return;
        }

        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicatedNicknameException(nickname);
        }

        member.updateNickname(nickname);
        memberRepository.save(member);
    }

    public Member findMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> NotFoundMemberException.createWhenInvalidMemberId(memberId));
    }
}
