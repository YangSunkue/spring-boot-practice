package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 첫 번째 컨트롤러 클래스입니다.
 * 기본적인 웹 요청을 처리합니다.
 */
@Controller
public class FirstController {

    /**
     * 환영 인사를 출력합니다.
     */
    @GetMapping("/hi")
    public String niceToMeetYou(Model model) {
        model.addAttribute("username", "선규")
                .addAttribute("job", "developer");
        return "greetings";
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("username", "선규");
        return "goodbye";
    }
}