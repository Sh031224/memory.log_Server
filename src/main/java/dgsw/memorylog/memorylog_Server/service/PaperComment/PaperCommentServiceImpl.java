package dgsw.memorylog.memorylog_Server.service.PaperComment;

import dgsw.memorylog.memorylog_Server.domain.entity.Member;
import dgsw.memorylog.memorylog_Server.domain.entity.Paper;
import dgsw.memorylog.memorylog_Server.domain.entity.PaperComment;
import dgsw.memorylog.memorylog_Server.domain.repository.PaperCommentRepository;
import dgsw.memorylog.memorylog_Server.domain.repository.PaperRepository;
import dgsw.memorylog.memorylog_Server.domain.vo.paperComment.PaperCommentCreateVo;
import dgsw.memorylog.memorylog_Server.domain.vo.paperComment.PaperCommentEditVo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class PaperCommentServiceImpl implements PaperCommentService {

    @Autowired
    private PaperCommentRepository paperCommentRepo;

    @Autowired
    private PaperRepository paperRepo;

    /**
     * 코멘트 생성
     * @param member
     * @param paperCommentCreateVo
     */
    @Override
    public void createPaperComment(Member member, PaperCommentCreateVo paperCommentCreateVo) {
        try {
            if ((StringUtils.isEmpty(paperCommentCreateVo.getImage()) && StringUtils.isEmpty(paperCommentCreateVo.getComment())) ||
                    (StringUtils.isEmpty(paperCommentCreateVo.getComment()) && StringUtils.isEmpty(paperCommentCreateVo.getFontFamily()))) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.");
            }
            ModelMapper modelMapper = new ModelMapper();
            PaperComment mappedPaper = modelMapper.map(paperCommentCreateVo, PaperComment.class);
            paperCommentRepo.save(mappedPaper);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 코멘트 조회
     * @param paperIdx
     * @return PaperComments
     */
    @Override
    public List<PaperComment> getPaperComments(Integer paperIdx) {
        try {
            Paper paper = paperRepo.findByIdx(paperIdx);
            if (paper == null) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "페이지 없음.");
            }

            return paperCommentRepo.findAllByPaperIdx(paper.getIdx());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 코멘트 수정
     * @param member
     * @param paperCommentIdx
     */
    @Override
    public void editPaperComment(Member member, Integer paperCommentIdx, PaperCommentEditVo paperCommentEditVo) {
        try {
            PaperComment paperComment = paperCommentRepo.findByIdx(paperCommentIdx);
            if (paperComment == null) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "코멘트 없음.");
            }

            if (paperComment.getMember() != member) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "권한 없음.");
            }

            if (paperCommentEditVo == null) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.");
            }

            paperComment.setComment(StringUtils.isEmpty(paperCommentEditVo.getComment()) ? paperComment.getComment(): paperCommentEditVo.getComment());
            paperComment.setLocationX(paperCommentEditVo.getLocationX() != null ? paperComment.getLocationX(): paperCommentEditVo.getLocationX());
            paperComment.setLocationY(paperCommentEditVo.getLocationY() != null ? paperComment.getLocationY(): paperCommentEditVo.getLocationY());
            paperComment.setFontFamily(StringUtils.isEmpty(paperCommentEditVo.getFontFamily()) ? paperComment.getFontFamily(): paperCommentEditVo.getFontFamily());
            paperComment.setImage(StringUtils.isEmpty(paperCommentEditVo.getImage()) ? paperComment.getImage(): paperCommentEditVo.getImage());
            paperComment.setUpdatedAt((java.sql.Date) new Date());
            paperCommentRepo.save(paperComment);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 코멘트 삭제
     * @param member
     * @param paperCommentIdx
     */
    @Override
    public void deletePaperComment(Member member, Integer paperCommentIdx) {
        try {
            PaperComment paperComment = paperCommentRepo.findByIdx(paperCommentIdx);
            if (paperComment == null) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "코멘트 없음.");
            }

            if (paperComment.getMember() != member && paperComment.getPaper().getMember() != member) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "권한 없음.");
            }

            // 페이지 만든 사람이거나, 코멘트 만든 사람이면
            paperCommentRepo.delete(paperComment);
        } catch (Exception e) {
            throw e;
        }
    }
}
