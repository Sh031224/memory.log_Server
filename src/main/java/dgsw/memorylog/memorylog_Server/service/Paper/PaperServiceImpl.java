package dgsw.memorylog.memorylog_Server.service.Paper;

import dgsw.memorylog.memorylog_Server.domain.entity.Paper;
import dgsw.memorylog.memorylog_Server.domain.repository.PaperRepository;
import dgsw.memorylog.memorylog_Server.domain.vo.paper.PaperCreatePaperVo;
import dgsw.memorylog.memorylog_Server.enums.PaperScope;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperServiceImpl implements PaperService{
    @Autowired
    private PaperRepository paperRepo;

    /**
     * 롤링페이퍼 생성
     * @return 롤링페이퍼 인덱스
     */
    @Override
    public void createPaper(PaperCreatePaperVo paperCreatePaperVo) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            Paper mappedPaper = modelMapper.map(paperCreatePaperVo, Paper.class);
            paperRepo.save(mappedPaper);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 롤링페이퍼 조회
     * @return 내 롤링페이퍼 글
     */
    @Override
    public List<Paper> getMyPaper(String name) {
        try {
            return paperRepo.findAllByMember_Name(name);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 롤링페이퍼 조회
     * @return 하나의 롤링페이퍼 글
     */
    @Override
    public Paper showOnePaper(Integer paper_idx) {
        try {
            return paperRepo.findByIdx(paper_idx);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 롤링페이퍼 조회
     * @return 공개 범위가 ONLYCODE인 롤링페이퍼 글
     */
    @Override
    public Paper showOnlyCodePaper(Integer paper_idx, String code) {
        try {
            return paperRepo.findByIdxAndCode(paper_idx, code);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 롤링페이퍼 조회
     * @return 공개 범위가 PUBLIC인 롤링페이퍼 글
     */
    @Override
    public List<Paper> showPublicPaper() {
        try {
            return paperRepo.findAllByScope(PaperScope.PUBLIC);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 롤링페이퍼 검색
     * @return 롤링페이퍼 정보
     */
    @Override
    public List<Paper> searchPaperByTitle(String title) {
        try {
            return paperRepo.findAllByTitleContaining(title);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Paper> searchPaperByMemberName(String name) {
        try {
            return paperRepo.findAllByMember_Name(name);
        } catch (Exception e) {
            throw e;
        }
    }
}
