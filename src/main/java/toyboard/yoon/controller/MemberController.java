package toyboard.yoon.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import toyboard.yoon.dto.member.MemberRequestDto;
import toyboard.yoon.dto.member.MemberResponseDto;
import toyboard.yoon.repository.MemberRepository;
import toyboard.yoon.security.dto.*;
import toyboard.yoon.service.member.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody MemberRequest memberRequest){
        return ResponseEntity.ok(memberService.signUp(memberRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> logIn(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(memberService.logIn(loginRequest));
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> findUserInfo(HttpServletRequest httpRequest) {
        return ResponseEntity.ok(memberService.findUserInfo(httpRequest));
    }

}
