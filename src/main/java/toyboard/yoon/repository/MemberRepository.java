package toyboard.yoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyboard.yoon.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByEmailAndPassword(String email, String password);
}
