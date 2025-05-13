package com.grepp.team08.app.controller.web.recommend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class RecommendController {

    @GetMapping("/recommend")
    public String recommend() {
        return "forward:/select_category_user_input.html";
    }
}
