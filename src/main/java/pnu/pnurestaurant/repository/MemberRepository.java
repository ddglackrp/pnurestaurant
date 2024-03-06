package pnu.pnurestaurant.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.domain.Member;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberRepository {

    private final EntityManager em;

    @Transactional
    public Long save(Member member){
        em.persist(member);
        log.info("member = {}",member.getId());
        return member.getId();
    }

    public Member findByMemberId(String memberId){
        Member member = em.createQuery("select m from Member m where m.memberId = :memberId", Member.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
        return member;
    }

}
