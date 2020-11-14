package dgsw.memorylog.memorylog_Server.service.PaperComment;

import dgsw.memorylog.memorylog_Server.domain.entity.Member;
import dgsw.memorylog.memorylog_Server.domain.entity.PaperComment;
import dgsw.memorylog.memorylog_Server.domain.vo.paperComment.PaperCommentCreateVo;
import dgsw.memorylog.memorylog_Server.domain.vo.paperComment.PaperCommentEditVo;

import java.util.List;

public interface PaperCommentService {
    public void createPaperComment(Member member, PaperCommentCreateVo paperCommentCreateVo);
    public List<PaperComment> getPaperComments(Integer paperIdx);
    public void editPaperComment(Member member, Integer paperCommentIdx, PaperCommentEditVo paperCommentEditVo);
    public void deletePaperComment(Member member, Integer paperCommentIdx);
}
