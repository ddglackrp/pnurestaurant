package pnu.pnurestaurant.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.domain.Member;
import pnu.pnurestaurant.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(Member member){
        memberRepository.save(member);
        return member;
    }

    public Member getMember(Long id){
        return memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 회원이 없습니다."));
    }
}