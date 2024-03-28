package pnu.pnurestaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pnu.pnurestaurant.dto.response.MemberResponseDto;
import pnu.pnurestaurant.dto.response.ReviewResponseDto;
import pnu.pnurestaurant.service.MemberService;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public String memberInfo(@PathVariable("memberId") Long id,
                             Model model){

        MemberResponseDto findMember = memberService.getMember(id);
        model.addAttribute("member",findMember);

        return "member/memberInfo";
    }

    @GetMapping("/{memberId}/reviews")
    public String memberReviewsInfo(@PathVariable("memberId") Long id,
                                    Model model){

        List<ReviewResponseDto> reviewDtos = memberService.getMemberWithRelation(id);
        model.addAttribute("reviews",reviewDtos);
        return "member/mypage/reviews";
    }


}
