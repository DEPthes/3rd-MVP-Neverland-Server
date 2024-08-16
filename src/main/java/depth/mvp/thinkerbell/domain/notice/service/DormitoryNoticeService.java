package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.common.pagination.PaginationDTO;
import depth.mvp.thinkerbell.domain.notice.dto.DormitoryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.DormitoryNotice;
import depth.mvp.thinkerbell.domain.notice.repository.DormitoryNoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DormitoryNoticeService {

    @Autowired
    private DormitoryNoticeRepository dormitoryNoticeRepository;

//    public List<DormitoryNoticeDTO> getAllNotices() {
//        return dormitoryNoticeRepository.findAll().stream().map(notice -> new DormitoryNoticeDTO(
//                notice.getId(), notice.getPubDate(), notice.getTitle(), notice.getUrl(),
//                notice.isImportant(), notice.getCampus()
//        )).collect(Collectors.toList());
//    }

    public PaginationDTO<DormitoryNoticeDTO> getImportantNotices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DormitoryNotice> resultPage = dormitoryNoticeRepository.findAllByOrderByImportantDescPubDateDesc(pageable);

        List<DormitoryNoticeDTO> dtoList = resultPage.stream()
                .map(DormitoryNoticeDTO::fromEntity)  // DTO 클래스 내의 메서드 호출
                .collect(Collectors.toList());

        return new PaginationDTO<>(
                dtoList,
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalElements()
        );
    }
}
