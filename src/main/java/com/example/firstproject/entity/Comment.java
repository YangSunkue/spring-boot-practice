package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Article(게시글)과 연동되는 댓글 엔티티 클래스 입니다.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 참조 대상 테이블과 다대일 관계 설정
    @JoinColumn(name="article_id") // 외래키 설정
    private Article article; // 부모 게시글

    @Column
    private String nickname;

    @Column
    private String body;

}