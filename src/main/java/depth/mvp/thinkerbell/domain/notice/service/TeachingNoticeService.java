package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.pagination.PaginationDTO;
import depth.mvp.thinkerbell.domain.notice.dto.TeachingNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.TeachingNotice;
import depth.mvp.thinkerbell.domain.notice.repository.TeachingNoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeachingNoticeService {

    @Autowired
    private TeachingNoticeRepository teachingNoticeRepository;

    public PaginationDTO<TeachingNoticeDTO> getImportantNotices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TeachingNotice> resultPage = teachingNoticeRepository.findAllByOrderByIsImportantDescPubDateDesc(pageable);

        List<TeachingNoticeDTO> dtoList = resultPage.stream()
                .map(TeachingNoticeDTO::fromEntity)  // DTO 클래스 내의 메서드 호출
                .collect(Collectors.toList());

        return new PaginationDTO<>(
                dtoList,
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalElements()
        );
    }
}
