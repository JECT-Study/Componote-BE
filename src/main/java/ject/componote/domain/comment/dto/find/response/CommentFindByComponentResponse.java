package ject.componote.domain.comment.dto.find.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import ject.componote.domain.comment.dao.CommentFindByComponentDao;

import java.time.LocalDateTime;

public record CommentFindByComponentResponse(
        CommentProfileResponse profile,
        Long id,
        @JsonInclude(JsonInclude.Include.NON_NULL) Long parentId,
        @JsonInclude(JsonInclude.Include.NON_NULL) String imageUrl,
        String content,
        LocalDateTime createdAt,
        Long likeCount,
        boolean isReply) {
    public static CommentFindByComponentResponse from(final CommentFindByComponentDao dto) {
        return new CommentFindByComponentResponse(
                createProfileResponse(dto),
                dto.commentId(),
                dto.parentId(),
                dto.commentImage().getImage().toUrl(),
                dto.content().getValue(),
                dto.createdAt(),
                dto.likeCount().getValue(),
                dto.isReply()
        );
    }

    private static CommentProfileResponse createProfileResponse(final CommentFindByComponentDao dto) {
        return new CommentProfileResponse(
                dto.memberId(),
                dto.nickname().getValue(),
                dto.profileImage().getImage().toUrl(),
                dto.job().name()
        );
    }
}
