package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * 엔티티 클래스입니다.
 * DB의 테이블과 연결됩니다.
 */
@Entity
public class Article {

    @Id             // primary key
    @GeneratedValue // autoincrement 어노테이션
    private Long id;
    @Column
    private String title;
    @Column
    private String content;

    // 생성자
    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
