package depth.mvp.thinkerbell.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String ssaid;
    private String deviceToken;

    @Builder
    public UserDto(String ssaid, String deviceToken) {
        this.ssaid = ssaid;
        this.deviceToken = deviceToken;
    }
}
