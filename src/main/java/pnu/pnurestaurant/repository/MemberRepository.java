package pnu.pnurestaurant.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.domain.Member;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    @Transactional
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member findByMemberId(String memberId){
        return em.createQuery("select m from Member m where m.memberId = :memberId", Member.class)
                .setParameter("memberId",memberId)
                .getSingleResult();
    }

}
