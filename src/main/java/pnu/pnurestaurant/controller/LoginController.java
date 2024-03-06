package pnu.pnurestaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pnu.pnurestaurant.Dto.MemberDto;
import pnu.pnurestaurant.domain.Member;
import pnu.pnurestaurant.service.MemberService;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MemberService memberService;

    @GetMapping("/admin")
    @ResponseBody
    public String admin(){
        return "admin";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "login/loginForm";
    }

    @GetMapping("/join")
    public String joinForm(){
        return "login/joinForm";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute MemberDto memberDto){
        Member member = new Member();
        member.setMemberId(memberDto.getMemberId());
        member.setPassword(memberDto.getPassword());
        member.setEmail(memberDto.getEmail());
        memberService.join(member, bCryptPasswordEncoder.encode(member.getPassword()));
        return "redirect:/";
    }

}
