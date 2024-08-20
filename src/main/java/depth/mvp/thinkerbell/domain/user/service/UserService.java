package depth.mvp.thinkerbell.domain.user.service;

import depth.mvp.thinkerbell.domain.user.dto.UserDto;
import depth.mvp.thinkerbell.domain.user.entity.User;
import depth.mvp.thinkerbell.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(UserDto userDto) {
        String ssaid = userDto.getSsaid();
        String token = userDto.getDeviceToken();

        Optional<User> existUser = userRepository.findBySsaid(ssaid);

        if (existUser.isPresent()) {
            //유저 정보 가져오기
            User user = existUser.get();
            if (user.getFcmToken().equals(token)) {
                //동일한 경우
                return;
            } else {
               user.setFcmToken(token);
               userRepository.save(user);
               return;
            }
        }

        User newUser = new User(userDto.getSsaid(), userDto.getDeviceToken());
        userRepository.save(newUser);
    }
}
