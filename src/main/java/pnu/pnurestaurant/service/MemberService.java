package pnu.pnurestaurant.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pnu.pnurestaurant.Dto.response.MemberResponseDto;
import pnu.pnurestaurant.Dto.response.ReviewResponseDto;
import pnu.pnurestaurant.domain.Member;
import pnu.pnurestaurant.domain.Review;
import pnu.pnurestaurant.repository.MemberRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void saveMember(Member member){
        memberRepository.save(member);
    }

    public MemberResponseDto getMember(Long id){
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 회원이 없습니다."));
        return new MemberResponseDto(findMember);
    }

    public List<ReviewResponseDto> getMemberWithRelation(Long id){
        Member findMember = memberRepository.findMemberWithRelation(id).orElseThrow(() -> new EntityNotFoundException("해당 회원이 없습니다."));

        List<ReviewResponseDto> reviewDtos = findMember.getReviews().stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());

        return reviewDtos;
    }
}