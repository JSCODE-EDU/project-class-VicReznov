package toyboard.yoon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toyboard.yoon.config.security.JwtConfig;
import toyboard.yoon.dto.member.MemberRequestDto;
import toyboard.yoon.dto.member.MemberResponseDto;
import toyboard.yoon.service.member.impl.MemberServiceImpl;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;
    private final JwtConfig jwtConfig;

    @PostMapping("/signup")
    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {
        return memberService.createMember(memberRequestDto);
    }

    @GetMapping("/my")
    public MemberResponseDto findMember(Authentication authentication) {
        if (authentication == null) {
            throw new BadCredentialsException("회원 정보를 찾을 수 없습니다.");
        }
        return memberService.findMember(authentication.getName());
    }

    @GetMapping("/admin")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')") // 권한을 통제
    public List<MemberResponseDto> findAllMember() {
        return memberService.findAll();
    }

    @PostMapping("/login")
    public String login(MemberRequestDto memberRequestDto) {
        MemberResponseDto users = memberService.findByEmailAndPassword(memberRequestDto.getEmail(), memberRequestDto.getPassword());
        return jwtConfig.createToken(users.getEmail(), Arrays.asList(users.getMemberRole().getValue()));
    }
}
