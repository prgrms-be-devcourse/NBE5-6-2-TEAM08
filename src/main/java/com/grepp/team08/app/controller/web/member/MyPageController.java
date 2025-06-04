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
@RequestMapping
@RequiredArgsConstructor
public class MyPageController {
    @GetMapping("/my-page")
    public String myPageMain(Model model, @AuthenticationPrincipal Principal principal) {
        model.addAttribute("userId", principal.getUsername());
        return "my_page";
    }

}
