package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 게시글 관련 컨트롤러 입니다.
 */
@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

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

        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        System.out.println(article.toString());

        // 2. 리파지터리를 이용해 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        System.out.println(saved.toString());

        return "articles/new";
    }
}
