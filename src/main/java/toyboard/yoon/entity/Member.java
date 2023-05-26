package toyboard.yoon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import toyboard.yoon.enumeration.MemberRole;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotBlank
    @Email
    @Pattern(regexp = "^([^@]+)@([^@]+)$", message = "E-mail에 @ 문자는 하나만 있어야 합니다.")
    private String email;

    @NotBlank
    @Min(8)
    @Max(15)
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Builder
    private Member(Long id, String email, String password, MemberRole memberRole) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.memberRole = memberRole;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMemberRole(MemberRole memberRole) {
        this.memberRole = memberRole;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
