package com.grepp.team08.app.controller.web.member;

import com.grepp.team08.app.controller.web.member.payload.SigninRequest;
import com.grepp.team08.app.controller.web.member.payload.SignupRequest;
import com.grepp.team08.app.model.auth.code.Role;
import com.grepp.team08.app.model.member.dto.MemberDto;
import com.grepp.team08.app.model.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signin")
    public String signin(Model model){
        model.addAttribute("signinRequest", new SigninRequest());
        return "signin";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(
        @Valid @ModelAttribute("signupRequest") SignupRequest form,
        BindingResult bindingResult,
        Model model) {

        log.info("회원가입 시도: {}", form);

        if (bindingResult.hasErrors()) {
            log.warn("회원가입 오류", bindingResult.getAllErrors());
            return "signup";
        }

        memberService.signup(form.toDto(), Role.ROLE_USER);
        log.info("회원가입 완료");
        return "redirect:/";
    }

}
