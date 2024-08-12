package depth.mvp.thinkerbell.domain.user.service;

import depth.mvp.thinkerbell.domain.notice.entity.AllNoticesView;
import depth.mvp.thinkerbell.domain.notice.repository.AllNoticeViewRepository;
import depth.mvp.thinkerbell.domain.user.entity.Keyword;
import depth.mvp.thinkerbell.domain.user.entity.User;
import depth.mvp.thinkerbell.domain.user.repository.KeywordRepository;
import depth.mvp.thinkerbell.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordSaveService {

    private final KeywordRepository keywordRepository;

    private final AllNoticeViewRepository allNoticeViewRepository;

    private final UserRepository userRepository;

    //userID는 user SSAID를 의미한다.
    public Boolean saveKeywordToDB(String keyword, String userID) {
        String keywordWithoutSpaces = keyword.replace(" ", "");

        Optional<User> user = userRepository.findBySsaid(userID);

        if (user.isPresent()) {
            User userEntity = user.get();
            keywordRepository.save(new Keyword(keywordWithoutSpaces, userEntity));

            return true;
        } else {
            return false;
        }
    }

    //키워드가 존재하는지 확인하기
    public Boolean isExistKeyword(String keyword) {
        String keywordWithoutSpaces = keyword.replace(" ", "");

        List<AllNoticesView> view = allNoticeViewRepository.findByTitleContainingKeyword(keywordWithoutSpaces);

        if (view.isEmpty()){
            return false;
        } else {
            return true;
        }

    }

    //키워드 길이 확인하기
    public Boolean countKeyword(String keyword) {
        String keywordWithoutSpaces = keyword.replace(" ", "");

        if (keywordWithoutSpaces.length() >= 2){
            return true;
        } else {
            return false;
        }
    }
}
