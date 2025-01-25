package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;

/**
 * 게시글 생성 요청 데이터를 전달받는 dto 입니다.
 */
public class ArticleForm {
    private String title; // 제목
    private String content; // 내용

    // 생성자
    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    // DTO 객체를 엔티티로 변환하는 함수
    public Article toEntity() {
        return new Article(null, title, content);
    }
}