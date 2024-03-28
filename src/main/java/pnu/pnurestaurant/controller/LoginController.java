package pnu.pnurestaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pnu.pnurestaurant.dto.request.MemberRequestDto;
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
    public String join(@ModelAttribute @Valid MemberRequestDto memberRequestDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "login/joinForm";
        }

        Member member = Member.builder()
                .memberName(memberRequestDto.getMemberId())
                .password(bCryptPasswordEncoder.encode(memberRequestDto.getPassword()))
                .email(memberRequestDto.getEmail())
                .role("ROLE_USER")
                .build();

        memberService.saveMember(member);
        return "redirect:/";
    }

}
