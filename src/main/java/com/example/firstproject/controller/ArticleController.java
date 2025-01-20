package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 게시글 관련 컨트롤러 입니다.
 */
@Controller
public class ArticleController {

    /**
     * 게시글 생성 페이지입니다.
     */
    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    /**
     * 게시글을 생성합니다.
     */
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        System.out.println(form.toString());
        return "articles/new";
    }
}
