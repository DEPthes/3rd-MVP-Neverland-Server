package depth.mvp.thinkerbell.domain.notice.service;

import depth.mvp.thinkerbell.domain.notice.dto.BiddingNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.StudentActsNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.entity.BiddingNotice;
import depth.mvp.thinkerbell.domain.notice.entity.StudentActsNotice;
import depth.mvp.thinkerbell.domain.notice.repository.BiddingNoticeRepository;
import depth.mvp.thinkerbell.domain.notice.repository.StudentActsNoticeRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BiddingNoticeService {
    private final BiddingNoticeRepository biddingNoticeRepository;

    public BiddingNoticeService(BiddingNoticeRepository biddingNoticeRepository) {
        this.biddingNoticeRepository = biddingNoticeRepository;
    }

    public List<BiddingNoticeDTO> getAllBiddingNotices() throws NotFoundException {
        List<BiddingNotice> notices = biddingNoticeRepository.findAll();
        if (notices.isEmpty()) {
            throw new NotFoundException("저장된 공지사항이 없습니다.");
        }
        return notices.stream().map(notice -> new BiddingNoticeDTO(
                notice.getId(), notice.getPubDate(), notice.getTitle(), notice.getUrl()
        )).collect(Collectors.toList());
    }

}
