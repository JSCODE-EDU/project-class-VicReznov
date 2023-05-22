package toyboard.yoon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

}
