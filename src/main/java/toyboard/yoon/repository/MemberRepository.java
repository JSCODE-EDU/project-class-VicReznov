package toyboard.yoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyboard.yoon.entity.member.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailValueAndPasswordValue(String email, String password);

    boolean existsByEmailValue(String email);

    Member findByEmailValue(String email);
}
