package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.pagination.PaginationDTO;
import depth.mvp.thinkerbell.domain.notice.dto.DormitoryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.DormitoryNotice;
import depth.mvp.thinkerbell.domain.notice.repository.DormitoryNoticeRepository;
import depth.mvp.thinkerbell.domain.user.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DormitoryNoticeService {
    private final BookmarkService bookmarkService;
    @Autowired
    private DormitoryNoticeRepository dormitoryNoticeRepository;

    public PaginationDTO<DormitoryNoticeDTO> getImportantNotices(int page, int size) {
        if (page == 0) {
            // 첫 페이지: 중요 공지사항 모두 가져오기
            List<DormitoryNotice> importantNotices = dormitoryNoticeRepository.findAllByImportantTrueOrderByPubDateDesc();

            // 최신 공지사항 가져오기 (최대 10개)
            Pageable latestPageable = PageRequest.of(0, 10);
            Page<DormitoryNotice> latestNoticesPage = dormitoryNoticeRepository.findAllByImportantFalseOrderByPubDateDesc(latestPageable);

            // DTO 변환
            List<DormitoryNoticeDTO> dtoList = importantNotices.stream()
                    .map(DormitoryNoticeDTO::fromEntity)
                    .collect(Collectors.toList());

            dtoList.addAll(latestNoticesPage.stream()
                    .map(DormitoryNoticeDTO::fromEntity)
                    .collect(Collectors.toList()));

            return new PaginationDTO<>(
                    dtoList,
                    0, // 첫 페이지 번호
                    dtoList.size(), // 가져온 항목의 개수
                    importantNotices.size() + latestNoticesPage.getTotalElements() // 총 항목 수
            );
        } else {
            // 그 이후 페이지: 최신 공지사항만 페이지네이션
            Pageable pageable = PageRequest.of(page - 1, size);  // 첫 페이지는 이미 처리했으므로 page - 1
            Page<DormitoryNotice> resultPage = dormitoryNoticeRepository.findAllByImportantFalseOrderByPubDateDesc(pageable);

            List<DormitoryNoticeDTO> dtoList = resultPage.stream()
                    .map(DormitoryNoticeDTO::fromEntity)
                    .collect(Collectors.toList());

            return new PaginationDTO<>(
                    dtoList,
                    resultPage.getNumber() + 1, // 페이지 번호를 1 추가하여 반환
                    resultPage.getSize(),
                    resultPage.getTotalElements()
            );
        }
    }
}
