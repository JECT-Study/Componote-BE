package ject.componote.domain.report.application;

import ject.componote.domain.auth.model.AuthPrincipal;
import ject.componote.domain.comment.dao.CommentRepository;
import ject.componote.domain.comment.error.NotFoundCommentException;
import ject.componote.domain.report.dao.ReportRepository;
import ject.componote.domain.report.domain.Report;
import ject.componote.domain.report.domain.ReportReason;
import ject.componote.domain.comment.dto.report.event.CommentReportEvent;
import ject.componote.domain.report.dto.request.ReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {
    private final ApplicationEventPublisher eventPublisher;
    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;

    @Transactional
    public void create(final AuthPrincipal authPrincipal, final Long commentId, final ReportRequest request) {
        if (!existsCommentById(commentId)) {
            throw new NotFoundCommentException(commentId);
        }

        final Long memberId = authPrincipal.id();
        final ReportReason reason = request.reason();
        reportRepository.save(Report.of(reason, commentId, memberId));
        eventPublisher.publishEvent(CommentReportEvent.from(commentId));
    }

    private boolean existsCommentById(final Long commentId) {
        return commentRepository.existsById(commentId);
    }
}
