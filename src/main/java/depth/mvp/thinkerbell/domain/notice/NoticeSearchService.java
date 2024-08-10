package depth.mvp.thinkerbell.domain.notice;

import depth.mvp.thinkerbell.domain.notice.dto.DormitoryEntryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.DormitoryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.dto.LibraryNoticeDTO;
import depth.mvp.thinkerbell.domain.notice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeSearchService {
    private final DormitoryEntryNoticeRepository dormitoryEntryNoticeRepository;
    private final DormitoryNoticeRepository dormitoryNoticeRepository;
    private final LibraryNoticeRepository libraryNoticeRepository;

    public Map<String, List<?>> searchNotices(String keyword) {
        Map<String, List<?>> result = new HashMap<>();

        // DormitoryEntryNotice 검색 및 DTO 변환
        List<DormitoryEntryNoticeDTO> dormitoryEntryNotices = dormitoryEntryNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> new DormitoryEntryNoticeDTO(
                        notice.getId(),
                        notice.getPubDate(),
                        notice.getTitle(),
                        notice.getUrl(),
                        null,
                        null
                )).collect(Collectors.toList());

        if (!dormitoryEntryNotices.isEmpty()) {
            result.put("dormitoryEntryNotices", dormitoryEntryNotices);
        }

        // DormitoryNotice 검색 및 DTO 변환
        List<DormitoryNoticeDTO> dormitoryNotices = dormitoryNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> new DormitoryNoticeDTO(
                        notice.getId(),
                        notice.getPubDate(),
                        notice.getTitle(),
                        notice.getUrl(),
                        null,
                        null
                )).collect(Collectors.toList());

        if (!dormitoryNotices.isEmpty()) {
            result.put("dormitoryNotices", dormitoryNotices);
        }

        // LibraryNotice 검색 및 DTO 변환
        List<LibraryNoticeDTO> libraryNotices = libraryNoticeRepository.searchByTitle(keyword)
                .stream()
                .map(notice -> new LibraryNoticeDTO(
                        notice.getId(),
                        notice.getPubDate(),
                        notice.getTitle(),
                        notice.getUrl(),
                        null
                )).collect(Collectors.toList());

        if (!libraryNotices.isEmpty()) {
            result.put("libraryNotices", libraryNotices);
        }

        return result;
    }
}