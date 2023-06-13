package toyboard.yoon.security.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserInfoResponse {

    private final Long id;

    private final String email;

    private final LocalDateTime regTime;

    @Builder
    public UserInfoResponse(Long id, String email, LocalDateTime regTime) {
        this.id = id;
        this.email = email;
        this.regTime = regTime;
    }
}
