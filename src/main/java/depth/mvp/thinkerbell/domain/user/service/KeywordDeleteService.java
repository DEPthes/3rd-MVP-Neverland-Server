package depth.mvp.thinkerbell.domain.user.service;

import depth.mvp.thinkerbell.domain.user.entity.User;
import depth.mvp.thinkerbell.domain.user.repository.KeywordRepository;
import depth.mvp.thinkerbell.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordDeleteService {

    private final KeywordRepository keywordRepository;

    private final UserRepository userRepository;

    public Boolean isDeleted(String keyword, String userSSAID) {
        User user = userRepository.findBySsaid(userSSAID)
                .orElseThrow(() -> new IllegalArgumentException("주어진 ID로 사용자를 찾을 수 없습니다."));

        keywordRepository.deleteByKeywordAndUserId(keyword, user.getId());

        if (keywordRepository.existsByKeywordAndUserId(keyword, user.getId())) {
            return false;
        } else {
            return true;
        }
    }
}
