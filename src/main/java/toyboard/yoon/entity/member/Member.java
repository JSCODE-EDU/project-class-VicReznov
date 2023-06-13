package toyboard.yoon.entity.member;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    @Builder
    public Member(String email, String password, LocalDateTime regTime) {
        this.email = new Email(email);
        this.password = new Password(password);
        this.regTime = LocalDateTime.now();
        this.roles = Roles.USER;
    }
}
