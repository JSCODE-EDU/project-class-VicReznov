package toyboard.yoon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import toyboard.yoon.dto.member.MemberRequestDto;
import toyboard.yoon.dto.member.MemberResponseDto;
import toyboard.yoon.entity.Member;
import toyboard.yoon.enumeration.MemberRole;
import toyboard.yoon.repository.MemberRepository;
import toyboard.yoon.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MemberController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    // 회원가입
    @PostMapping("/register")
    public Long register(@RequestBody MemberRequestDto memberRequestDto) {
        return memberRepository.save(Member.builder()
                .email(memberRequestDto.getEmail())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .memberRole(memberRequestDto.getMemberRole())
                .build()).getId();
    }

    // 로그인
    @PostMapping("/login")
    public MemberResponseDto login(@RequestBody MemberRequestDto memberRequestDto) {

        Member member = memberRepository.findByEmail(memberRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 이메일입니다."));
        if (!passwordEncoder.matches(memberRequestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 맞지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(member.getEmail(), member.getMemberRole());

//        return jwtTokenProvider.createToken(member.getEmail(), member.getMemberRole());
        return MemberResponseDto.builder()
                .id(member.getId())
                .token(token).build();
    }

    @GetMapping("/about")
//    @PreAuthorize("hasRole('USER')")
    public MemberResponseDto about(HttpServletRequest request) {
        log.info("request log={}", request.getHeader("X-AUTH-TOKEN"));

        String token = jwtTokenProvider.resolveToken(request);
        String userPK = jwtTokenProvider.getUserPK(token);
        Member member = memberRepository.findById(Long.parseLong(userPK))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .build();
    }


}
