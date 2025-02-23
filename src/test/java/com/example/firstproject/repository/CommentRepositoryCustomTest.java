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

@DataJpaTest
@Import(QuerydslConfig.class)
class CommentRepositoryCustomTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("nickname, body를 모두 포함하는(LIKE) 모든 댓글을 성공적으로 조회합니다.")
    void searchCommentsByNicknameAndBodySuccess() {

        // given
        String nickname = "Choi";
        String body = "크";
        Article article = new Article(4L, "당신의 인생 영화는?", "댓글 고");
        Comment comment = new Comment(3L, article, "Choi", "쇼생크 탈출");
        List<Comment> expected = List.of(comment);

        // when
        List<Comment> comments = commentRepository.search(nickname, body);

        // then
        assertThat(comments)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Body 데이터가 일치하는 모든 댓글을 성공적으로 조회합니다.")
    void findCommentsByBodySuccess() {

        // given
        String body = "초밥";
        Article article = new Article(5L, "당신의 소울 푸드는?", "댓글 고고");
        Comment comment = new Comment(6L, article, "Choi", "초밥");
        List<Comment> expected = List.of(comment);

        // when
        List<Comment> comments = commentRepository.findByBody(body);

        // then
        assertThat(comments)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}