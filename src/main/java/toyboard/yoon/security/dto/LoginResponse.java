package toyboard.yoon.security.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    private final String accessToken;

    private final String refreshToken;

    private final String grantType;

    @Builder
    public LoginResponse(String accessToken, String refreshToken, String grantType) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.grantType = grantType;
    }
}
