package toyboard.yoon.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import toyboard.yoon.entity.Member;

public class SecurityUser extends User {
    private Member member;

    public SecurityUser(Member member) {
        super(member.getId().toString(), member.getPassword(),
                AuthorityUtils.createAuthorityList(member.getMemberRole().toString()));
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
