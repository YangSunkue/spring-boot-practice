package com.example.firstproject.entity;

import com.example.firstproject.dto.ArticleForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 엔티티 클래스입니다.
 * DB의 테이블과 연결됩니다.
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Getter
public class Article {

    @Id             // primary key
    @GeneratedValue // autoincrement 어노테이션
    private Long id;
    @Column
    private String title;
    @Column
    private String content;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // UPDATE 작업을 위한 엔티티 값 변경 (PATCH) 메서드
    public void patch(ArticleForm form) {
        if (form.getTitle() != null) {
            this.title = form.getTitle();
        }
        if (form.getContent() != null) {
            this.content = form.getContent();
        }
    }
}