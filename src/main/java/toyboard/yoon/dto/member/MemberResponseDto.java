package toyboard.yoon.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberResponseDto {

    private Long id;

    private String email;

    private String password;


    private String token;

    @Builder
    private MemberResponseDto(Long id, String email, String password, String token) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.token = token;
    }
}
