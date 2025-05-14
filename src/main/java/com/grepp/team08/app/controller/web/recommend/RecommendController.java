package com.grepp.team08.app.controller.web.recommend;

import com.grepp.team08.app.model.recommend.code.Category;
import com.grepp.team08.app.model.recommend.code.Dong;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecommendController {

    @GetMapping("/recommend")
    public String recommend(Model model) {
        model.addAttribute("categories", Category.values());
        model.addAttribute("regions", Dong.values());
        return "select_category_user_input";
    }
}
