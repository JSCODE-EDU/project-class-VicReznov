package toyboard.yoon.service.member;

import toyboard.yoon.dto.member.MemberRequestDto;
import toyboard.yoon.dto.member.MemberResponseDto;

import java.util.List;

public interface MemberService {
    MemberResponseDto createMember(MemberRequestDto userRequest);

    MemberResponseDto findMember(String email);

    MemberResponseDto findByEmailAndPassword(String email, String password);

    List<MemberResponseDto> findAll();
}
