package com.grepp.team08.app.controller.web.member;

import com.grepp.team08.app.model.auth.domain.Principal;
import com.grepp.team08.app.model.member.entity.Member;
import com.grepp.team08.app.model.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MemberService memberService;

    private void setMemberInfo(Model model, Principal principal) {
        String userId = principal.getUsername();
        Member member = memberService.findByUserId(userId);
        model.addAttribute("member", member);
    }

    @GetMapping
    public String mypageMain(Model model, @AuthenticationPrincipal Principal principal) {
        setMemberInfo(model, principal);
        return "mypage";
    }

}
