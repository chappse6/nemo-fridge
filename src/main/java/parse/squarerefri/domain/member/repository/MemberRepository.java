package parse.squarerefri.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import parse.squarerefri.domain.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByEmailAuthKey(String emailAuthKey);
    Optional<Member> findByIdAndMemberName(String id, String memberName);

}
