package pnu.pnurestaurant.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pnu.pnurestaurant.domain.Member;
import pnu.pnurestaurant.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberName(memberId).orElseThrow(()->new UsernameNotFoundException("유저를 찾을 수 없습니다 username : "+memberId));
        if(member != null){
            return new PrincipalDetails(member);
        }
        return null;
    }
}
