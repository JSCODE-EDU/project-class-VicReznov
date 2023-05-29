package toyboard.yoon.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import toyboard.yoon.enumeration.MemberRole;

@Setter
@Getter
public class MemberResponseDto {

    private Long id;

    private String email;

    private String password;

    private MemberRole memberRole;

    private String token;

    @Builder
    private MemberResponseDto(Long id, String email, String password, MemberRole memberRole, String token) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.memberRole = memberRole;
        this.token = token;
    }
}
