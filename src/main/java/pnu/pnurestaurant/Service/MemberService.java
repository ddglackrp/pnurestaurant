package pnu.pnurestaurant.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pnu.pnurestaurant.domain.Member;
import pnu.pnurestaurant.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member join(Member member, String password){
        member.setPassword(password);
        member.setRole("ROLE_USER");
        memberRepository.save(member);
        return member;
    }
}