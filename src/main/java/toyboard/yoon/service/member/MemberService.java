package toyboard.yoon.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyboard.yoon.entity.Member;
import toyboard.yoon.repository.MemberRepository;
import toyboard.yoon.security.JwtTokenProvider;
import toyboard.yoon.security.dto.TokenInfo;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public String join(Member member) {

        //email 중복 check
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException(member.getEmail() + "이 이미 존재합니다.");
                });

        //저장
        memberRepository.save(member);

        return "SUCCESS";
    }

    @Transactional
    public TokenInfo login(String email, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        log.info("authenticationToken={} ", authenticationToken);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        log.info("authentication={} ", authentication);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }

//    public Member profile(HttpServletRequest request) {
//        Long memberId = jwtTokenProvider.getMemberId(request);
//        return memberRepository.findById(memberId)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
//    }
}
