package pnu.pnurestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pnu.pnurestaurant.domain.Member;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberName(String memberName);

}
