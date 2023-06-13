package toyboard.yoon.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import toyboard.yoon.entity.member.Member;
import toyboard.yoon.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmailValue(username);

        if (member == null) {
            throw new UsernameNotFoundException("해당 이메일을 찾을 수 없습니다.");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(member.getEmail().getValue())
                .authorities(member.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
