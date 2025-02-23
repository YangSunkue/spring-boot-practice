package com.example.firstproject.service;

import com.example.firstproject.dto.ApiResponseDto;
import com.example.firstproject.dto.ArticleRequestDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.exception.NotFoundException;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 게시글 관련 서비스 클래스입니다.
 */
@Service
@Slf4j
@Transactional(readOnly = true) // 읽기 전용 기본값 설정
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    /**
     * 모든 게시글을 조회합니다.
     */
    public List<Article> index() {
        return articleRepository.findAll();
    }

    /**
     * 단일 게시글을 조회합니다.
     */
    public Article show(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Article", id));
    }

    /**
     * 게시글을 생성합니다.
     */
    @Transactional
    public Article create(ArticleRequestDto dto) {

        Article article = dto.toEntity();
        Article saved = articleRepository.save(article);

        return saved;
    }

    /**
     * 게시글을 수정합니다.
     */
    @Transactional
    public Article update(Long id, ArticleRequestDto dto) {

        Article target = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Article", id)); // 커스텀 예외 적용
        log.info("UPDATE 대상 데이터 : {}", target);

        target.patch(dto);
        log.info("UPDATE 결과 데이터 : {}", target);

        return target;
    }

    /**
     * 게시글을 삭제합니다.
     */
    @Transactional
    public Article delete(Long id) {

        Article target = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Article", id));
        log.info("DELETE 대상 데이터 : {}", target);

        articleRepository.delete(target);

        return target;
    }


    @Transactional
    public List<Article> createArticles(List<ArticleRequestDto> dtos) {

        // 1. dto 묶음을 엔티티 묶음으로 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        // 2. 엔티티 묶음을 DB에 저장
        List<Article> savedArticles = articleRepository.saveAll(articleList); // 이게 더 효율적이다.

        // 3. 강제 예외 발생시키기
        articleRepository.findById(-1L)
                .orElseThrow(() -> new NotFoundException("Article", -1L));

        // 4. 결과 값 반환하기
        return savedArticles;
    }
}


















