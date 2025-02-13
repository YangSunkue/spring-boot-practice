package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 게시글 생성 요청 데이터를 전달받는 dto 입니다.
 */
@AllArgsConstructor
@Getter
@ToString
public class ArticleForm {

    private Long id; // id
    private String title; // 제목
    private String content; // 내용

    // DTO 객체를 엔티티로 변환하는 함수
    public Article toEntity() {
        return new Article(id, title, content);
    }

}