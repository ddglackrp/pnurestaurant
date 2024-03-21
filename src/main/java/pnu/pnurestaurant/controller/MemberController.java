package pnu.pnurestaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pnu.pnurestaurant.domain.Member;
import pnu.pnurestaurant.service.MemberService;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public String memberInfo(@PathVariable("memberId") Long id,
                             Model model){

        Member findMember = memberService.getMember(id);
        model.addAttribute("member",findMember);

        return "member/memberInfo";
    }

}
