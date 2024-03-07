package pnu.pnurestaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pnu.pnurestaurant.Dto.MemberJoinDto;
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
    public String join(@ModelAttribute @Valid MemberJoinDto memberJoinDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "login/joinForm";
        }

        Member member = Member.builder()
                .memberId(memberJoinDto.getMemberId())
                .password(bCryptPasswordEncoder.encode(memberJoinDto.getPassword()))
                .email(memberJoinDto.getEmail())
                .role("ROLE_USER")
                .build();

        memberService.join(member);
        return "redirect:/";
    }

}
