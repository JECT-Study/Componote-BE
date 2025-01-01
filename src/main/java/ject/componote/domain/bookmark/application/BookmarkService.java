package ject.componote.domain.bookmark.application;

import ject.componote.domain.auth.domain.Member;
import ject.componote.domain.auth.domain.MemberRepository;
import ject.componote.domain.auth.model.AuthPrincipal;
import ject.componote.domain.bookmark.domain.Bookmark;
import ject.componote.domain.bookmark.domain.BookmarkRepository;
import ject.componote.domain.bookmark.dto.response.BookmarkResponse;
import ject.componote.domain.bookmark.error.ExistedComponentError;
import ject.componote.domain.bookmark.error.NotFoundBookmarkException;
import ject.componote.domain.bookmark.error.NotFoundComponentException;
import ject.componote.domain.bookmark.error.NotFoundMemberException;
import ject.componote.domain.common.dto.response.PageResponse;
import ject.componote.domain.component.domain.Component;
import ject.componote.domain.component.domain.ComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final ComponentRepository componentRepository;

    public BookmarkResponse addBookmark(AuthPrincipal authPrincipal, Long componentId) {
        if (bookmarkRepository.existsByMemberIdAndComponentId(authPrincipal.id(), componentId)) {
            throw new ExistedComponentError(authPrincipal.id(), componentId);
        }

        Member member = findMemberOrThrow(authPrincipal.id());
        Component component = findComponentOrThrow(componentId);

        Bookmark bookmark = Bookmark.of(member, component);
        bookmarkRepository.save(bookmark);

        return BookmarkResponse.from(bookmark);
    }

    @Transactional(readOnly = true)
    public PageResponse<BookmarkResponse> getBookmark(AuthPrincipal authPrincipal, Pageable pageable) {
        Page<BookmarkResponse> bookmarkResponsePage = bookmarkRepository.findAllByMemberIdWithPagination(authPrincipal.id(), pageable)
                .map(BookmarkResponse::from);

        return PageResponse.from(bookmarkResponsePage);
    }

    public BookmarkResponse deleteBookmark(AuthPrincipal authPrincipal, Long componentId) {
        Bookmark bookmark = bookmarkRepository.findByMemberIdAndComponentId(authPrincipal.id(), componentId)
                .orElseThrow(() -> new NotFoundBookmarkException(authPrincipal.id(), componentId));
        bookmarkRepository.delete(bookmark);
        return BookmarkResponse.from(bookmark);
    }

    private Member findMemberOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(memberId));
    }

    private Component findComponentOrThrow(Long componentId) {
        return componentRepository.findById(componentId)
                .orElseThrow(() -> new NotFoundComponentException(componentId));
    }
}