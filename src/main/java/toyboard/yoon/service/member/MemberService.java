package toyboard.yoon.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyboard.yoon.entity.member.Member;
import toyboard.yoon.repository.MemberRepository;
import toyboard.yoon.security.JwtTokenProvider;
import toyboard.yoon.security.dto.*;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String signUp(MemberRequest memberRequest){

        if(!memberRepository.existsByEmailValue(memberRequest.getEmail())) {
            Member member = Member.builder()
                    .email(memberRequest.getEmail())
                    .password(memberRequest.getPassword())
                    .regTime(memberRequest.getRegTime())
                    .build();

            memberRepository.save(member);
            return "회원가입이 되었습니다!";
        } else {
            throw new IllegalArgumentException();
        }
    }

    public LoginResponse logIn(LoginRequest loginRequest){

        Member member = memberRepository.findByEmailValueAndPasswordValue(loginRequest.getEmail(),
                loginRequest.getPassword()).orElseThrow(IllegalArgumentException::new);

        return jwtTokenProvider.createToken(member.getId(), member.getRoles().getAuthority());
    }

    public UserInfoResponse findUserInfo(HttpServletRequest httpRequest){

        Long id = jwtTokenProvider.getMemberId(httpRequest);
        log.error("provider 뒤");

        Member member = memberRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return UserInfoResponse.builder()
                .id(member.getId())
                .email(member.getEmail().getValue())
                .regTime(member.getRegTime())
                .build();
    }

}
