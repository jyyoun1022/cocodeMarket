package cocode.cocodeMarket.repository.member;

import cocode.cocodeMarket.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {//1

    Optional<Member> findByEmail(String email); // 2
    Optional<Member> findByNickname(String nickname); // 3

    boolean existsByEmail(String email); // 4
    boolean existsByNickname(String nickname); // 5
}