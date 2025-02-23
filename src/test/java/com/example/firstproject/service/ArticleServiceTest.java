package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleRequestDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.exception.NotFoundException;
import com.example.firstproject.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


/**
 * 게시글 관련 서비스 함수 테스트 클래스입니다.
 *
 * TODO
 * 각종 성공/실패 케이스 테스트 케이스를 추가해야 합니다.
 */
@Transactional
@SpringBootTest
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleRepository articleRepository;

    @Test
    @DisplayName("모든 게시글을 성공적으로 조회합니다.")
    void showArticlesSuccess() {

        // given
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        Article d = new Article(4L, "당신의 인생 영화는?", "댓글 고");
        Article e = new Article(5L, "당신의 소울 푸드는?", "댓글 고고");
        Article f = new Article(6L, "당신의 취미는?", "댓글 고고고");
        List<Article> expected = List.of(a, b, c, d, e, f);

        // when
        List<Article> articles = articleService.index();

        // then
        assertThat(articles)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("단일 게시글을 성공적으로 조회합니다.")
    void showArticleSuccess() {

        // given
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");

        // when
        Article article = articleService.show(id);

        // then
        assertThat(article)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("단일 게시글을 성공적으로 생성합니다.")
    void createArticleSuccess() {

        // given
        String title = "라라라라";
        String content = "4444";
        Article expected = new Article(7L, title, content);
        ArticleRequestDto dto = new ArticleRequestDto(null, title, content);

        // when
        Article article = articleService.create(dto);

        // then
        assertThat(article)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }


    @Test
    @DisplayName("단일 게시글을 성공적으로 수정합니다.")
    void updateArticleSuccess() {

        // given
        Long id = 1L;
        String newTitle = "새로운 제목";
        String newContent = "새로운 내용";
        ArticleRequestDto dto = new ArticleRequestDto(id, newTitle, newContent);
        Article expected = new Article(id, newTitle, newContent);

        // when
        articleService.update(id, dto);

        // then
        Article updated = articleRepository.findById(id).get();
        assertThat(updated)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("단일 게시글을 성공적으로 삭제합니다.")
    void deleteArticleSuccess() {

        // given
        Long id = 1L;

        // when
        Article deleted = articleService.delete(id);

        // then
        // 1. 반환된 Article이 우리가 삭제하려던 것이 맞는지 확인
        assertThat(deleted.getId()).isEqualTo(id);

        // 2. 실제로 DB에서 삭제되었는지 확인
        assertThat(articleRepository.findById(id)).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 게시글을 삭제 시도하면 404 예외가 발생합니다.")
    void deleteArticleThrowsNotFoundException() {

        // given
        Long nonExistentId = -1L;

        // when & then
        assertThatThrownBy(() -> {
            articleService.delete(nonExistentId);
        }).isInstanceOf(NotFoundException.class);
    }
}