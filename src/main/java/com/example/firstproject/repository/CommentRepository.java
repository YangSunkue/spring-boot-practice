package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Article(게시글)과 연동되는 댓글 리파지터리 입니다.
 *
 * JPA는 구현체가 아닌 인터페이스를 상속받아도, Impl에 실제 구현된 메서드를 사용할 수 있다.
 * Impl을 붙임으로써, JPA는 커스텀 구현체 클래스를 찾는다.
 */
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    // 특정 게시글의 모든 댓글 조회
    @Query(
            value = "SELECT * FROM comment WHERE article_id = :articleId",
            nativeQuery = true
    )
    List<Comment> findByArticleId(Long articleId);

    // 특정 닉네임의 모든 댓글 조회
    List<Comment> findByNickname(String nickname);
}
