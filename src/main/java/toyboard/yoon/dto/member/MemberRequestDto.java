package toyboard.yoon.dto.member;

import lombok.*;
import toyboard.yoon.enumeration.MemberRole;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequestDto {

    private String email;

    private String password;

    private MemberRole memberRole;

}
