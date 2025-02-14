package com.example.firstproject.repository;


import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;


/**
 * Article 엔티티의 리파지터리 입니다.
 * CRUD 기능을 가진 인터페이스를 상속받습니다.
 * 제네릭 첫 번째 인자는 엔티티 이름, 두 번째 인자는 primary key의 자료형입니다.
 */
public interface ArticleRepository extends CrudRepository<Article, Long> {
    // 책에서 ArrayList로 오버라이딩 하라고 했는데, 왜 굳이?
    // 그냥 Iterable 써도 되잖아.
//    @Override
//    ArrayList<Article> findAll();
}
