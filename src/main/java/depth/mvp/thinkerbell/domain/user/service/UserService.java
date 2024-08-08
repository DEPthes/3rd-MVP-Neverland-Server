package depth.mvp.thinkerbell.domain.user.service;

import depth.mvp.thinkerbell.domain.user.dto.UserDto;
import depth.mvp.thinkerbell.domain.user.entity.User;
import depth.mvp.thinkerbell.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(UserDto userDto) {
        User user = new User(userDto.getSsaid(), userDto.getDeviceToken());
        userRepository.save(user);
    }
}
