package com.example.firstproject.repository;

import com.example.firstproject.config.QuerydslConfig;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // 리파지터리 테스트용 어노테이션
@Import(QuerydslConfig.class)
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글을 성공적으로 조회합니다.")
    void findByArticleIdSuccess() {

        // given
        Long articleId = 4L;
        Article article = new Article(4L, "당신의 인생 영화는?", "댓글 고");
        Comment a = new Comment(1L, article, "Park", "굿 윌 헌팅");
        Comment b = new Comment(2L, article, "Kim", "아이 엠 샘");
        Comment c = new Comment(3L, article, "Choi", "쇼생크 탈출");
        List<Comment> expected = List.of(a, b, c);

        // when
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        // then
        assertThat(comments)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("특정 사용자가 작성한 모든 댓글을 성공적으로 조회합니다.")
    void findByNicknameSuccess() {

        // given
        String nickname = "Park";
        Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 고"), nickname, "굿 윌 헌팅");
        Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글 고고"), nickname, "치킨");
        Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글 고고고"), nickname, "조깅");
        List<Comment> expected = List.of(a, b, c);

        // when
        List<Comment> comments = commentRepository.findByNickname(nickname);

        // then
        assertThat(comments)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}