package pnu.pnurestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pnu.pnurestaurant.domain.Member;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberName(String memberName);

    @Query(
            "select m from Member m" +
                    " left join fetch m.reviews r" +
                    " left join fetch r.restaurant" +
                    " where m.id = :id"
    )
    Optional<Member> findMemberWithRelation(@Param("id") Long id);


}
