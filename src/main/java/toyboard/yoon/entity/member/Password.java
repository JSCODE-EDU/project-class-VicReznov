package toyboard.yoon.entity.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    private static final String PASSWORD_REGEX = "^(?!.*\\s).{8,15}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    @Column(name = "password")
    private String value;

    public Password(String value) {
        validate(value);
        this.value = value;
    }

    public void validate(String value) {

        if (!PASSWORD_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException();
        }
    }
}
