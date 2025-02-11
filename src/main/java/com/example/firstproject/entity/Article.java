package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
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
public class Article {

    @Id             // primary key
    @GeneratedValue // autoincrement 어노테이션
    private Long id;
    @Column
    private String title;
    @Column
    private String content;

}