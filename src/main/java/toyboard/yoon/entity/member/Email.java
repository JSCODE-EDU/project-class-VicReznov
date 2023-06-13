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
public class Email {

    private static final String EMAIL_REGEX = "^[^\\s@]+@[^\\s@]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Column(name = "email")
    private String value;

    public Email(String value) {
        validate(value);
        this.value = value;
    }

    public void validate(String value){

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException();
        }
    }
}
