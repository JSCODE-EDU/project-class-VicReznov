package toyboard.yoon.service.member.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyboard.yoon.entity.Member;
import toyboard.yoon.dto.member.MemberRequestDto;
import toyboard.yoon.dto.member.MemberResponseDto;
import toyboard.yoon.repository.MemberRepository;
import toyboard.yoon.service.member.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {
        Member member = Member.builder()
//                .password(bCryptPasswordEncoder.encode(memberRequestDto.getPassword()))
                .password(memberRequestDto.getPassword())
                .email(memberRequestDto.getEmail())
                .memberRole(memberRequestDto.getMemberRole())
                .build();

        Member savedMember = memberRepository.save(member);

        return MemberResponseDto.builder()
                .id(savedMember.getId())
                .password(savedMember.getPassword())
                .memberRole(savedMember.getMemberRole())
                .email(savedMember.getEmail()).build();
    }

    @Override
    public MemberResponseDto findMember(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("회원 정보를 찾을 수 없습니다."));

        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .memberRole(member.getMemberRole())
                .build();
    }

    @Override
    public MemberResponseDto findByEmailAndPassword(String email, String password) {
        Member member = memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new BadCredentialsException("회원 정보를 찾을 수 없습니다."));

        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .memberRole(member.getMemberRole())
                .build();
    }

    @Override
    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll().stream()
                .map(m->MemberResponseDto.builder()
                        .id(m.getId())
                        .password(m.getPassword())
                        .memberRole(m.getMemberRole())
                        .email(m.getEmail()).build())
                .collect(Collectors.toList());
    }
}
