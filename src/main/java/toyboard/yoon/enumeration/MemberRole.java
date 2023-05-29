package toyboard.yoon.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberRole {
    USER("USER"),
    ADMIN("ADMIN");

    private String value;
}
