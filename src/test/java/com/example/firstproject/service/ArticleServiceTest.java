package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleRequestDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.exception.NotFoundException;
import com.example.firstproject.repository.ArticleRepository;
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
    void index_모든_게시글을_성공적으로_조회한다() {

        // given
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        List<Article> expected = List.of(a, b, c);

        // when
        List<Article> articles = articleService.index();

        // then
        assertThat(articles)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void show_단일_게시글을_성공적으로_조회한다() {

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
    void create_단일_게시글을_성공적으로_생성한다() {

        // given
        String title = "라라라라";
        String content = "4444";
        Article expected = new Article(4L, title, content);
        ArticleRequestDto dto = new ArticleRequestDto(null, title, content);

        // when
        Article article = articleService.create(dto);

        // then
        assertThat(article)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }


    @Test
    void update_단일_게시글을_성공적으로_수정한다() {

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
    void delete_단일_게시글을_성공적으로_삭제한다() {

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
    void delete_존재하지_않는_게시글_삭제시도하면_404_예외발생() {

        // given
        Long nonExistentId = -1L;

        // when & then
        assertThatThrownBy(() -> {
            articleService.delete(nonExistentId);
        }).isInstanceOf(NotFoundException.class);
    }
}