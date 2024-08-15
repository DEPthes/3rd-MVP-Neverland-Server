package depth.mvp.thinkerbell.domain.user.service;

import depth.mvp.thinkerbell.domain.user.dto.KeywordDto;
import depth.mvp.thinkerbell.domain.user.entity.Keyword;
import depth.mvp.thinkerbell.domain.user.entity.User;
import depth.mvp.thinkerbell.domain.user.repository.KeywordRepository;
import depth.mvp.thinkerbell.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    private final UserRepository userRepository;

    public List<KeywordDto> getKeywords(String userSSAID) {

        User user = userRepository.findBySsaid(userSSAID)
                .orElseThrow(() -> new IllegalArgumentException("주어진 ID로 사용자를 찾을 수 없습니다."));

        try {
            List<Keyword> keywords = keywordRepository.findAllByUserId(user.getId());

            return keywords.stream()
                    .map(keyword -> new KeywordDto(keyword.getKeyword()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
