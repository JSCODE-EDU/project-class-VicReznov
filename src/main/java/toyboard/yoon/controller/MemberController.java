package toyboard.yoon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import toyboard.yoon.dto.member.MemberRequestDto;
import toyboard.yoon.dto.member.MemberResponseDto;
import toyboard.yoon.entity.Member;
import toyboard.yoon.repository.MemberRepository;
import toyboard.yoon.security.JwtTokenProvider;
import toyboard.yoon.security.dto.TokenInfo;
import toyboard.yoon.service.member.MemberService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/register")
    public Long register(@RequestBody MemberRequestDto memberRequestDto) {
        log.info("회원가입 들어옴?");

        List<String> roles = new ArrayList<>();
        roles.add("USER");

        return memberRepository.save(Member.builder()
                .email(memberRequestDto.getEmail())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .roles(roles)
                .build()).getId();
    }

    // 로그인
    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberRequestDto memberRequestDto) {
        log.info("로그인 들어옴?");
        String email = memberRequestDto.getEmail();
        String password = memberRequestDto.getPassword();

        log.info("email={} password={}", email, password);
        TokenInfo tokenInfo = memberService.login(email, password);
        log.info("token={} ", tokenInfo);
        return tokenInfo;
    }

//    @GetMapping("/profile")
////    @PreAuthorize("hasRole('USER')")
//    public MemberResponseDto about(HttpServletRequest request) {
//        log.info("request log={}", request.getHeader("X-AUTH-TOKEN"));
//
//        String token = jwtTokenProvider.resolveToken(request);
//        String userPK = jwtTokenProvider.getUserPK(token);
//        Member member = memberRepository.findById(Long.parseLong(userPK))
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//        return MemberResponseDto.builder()
//                .id(member.getId())
//                .email(member.getEmail())
//                .build();
//    }


}
