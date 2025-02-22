package com.example.firstproject.service;

import com.example.firstproject.dto.ApiResponseDto;
import com.example.firstproject.dto.ArticleRequestDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.exception.NotFoundException;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * 모든 게시글을 조회합니다.
     */
    public ApiResponseDto<List<Article>> index() {

        List<Article> articleList = articleRepository.findAll();

        return ApiResponseDto.of("Articles successfully retrieved", articleList);
    }

    /**
     * 단일 게시글을 조회합니다.
     */
    public ApiResponseDto<Article> show(Long id) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Article", id));

        return ApiResponseDto.of("Article successfully retrieved", article);
    }

    /**
     * 게시글을 생성합니다.
     */
    @Transactional
    public ApiResponseDto<Article> create(ArticleRequestDto dto) {

        Article article = dto.toEntity();
        Article saved = articleRepository.save(article);

        return ApiResponseDto.of("Article successfully created", saved);
    }

    /**
     * 게시글을 수정합니다.
     */
    @Transactional
    public ApiResponseDto<Article> update(Long id, ArticleRequestDto dto) {

        Article target = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Article", id)); // 커스텀 예외 적용
        log.info("UPDATE 대상 데이터 : {}", target);

        target.patch(dto);
        log.info("UPDATE 결과 데이터 : {}", target);

        return ApiResponseDto.of("Article successfully updated", target);
    }

    /**
     * 게시글을 삭제합니다.
     */
    @Transactional
    public ApiResponseDto<Article> delete(Long id) {

        Article target = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Article", id));
        log.info("DELETE 대상 데이터 : {}", target);

        articleRepository.delete(target);

        return ApiResponseDto.of("Article successfully deleted", target);
    }


    @Transactional
    public ApiResponseDto<List<Article>> createArticles(List<ArticleRequestDto> dtos) {

        // 1. dto 묶음을 엔티티 묶음으로 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        // 2. 엔티티 묶음을 DB에 저장
//        articleList.stream()
//                .forEach(article -> articleRepository.save(article)); // 이건 다른 작업도 추가로 할 때 사용
        List<Article> savedArticles = articleRepository.saveAll(articleList); // 이게 더 효율적이다.

        // 3. 강제 예외 발생시키기
        articleRepository.findById(-1L)
                .orElseThrow(() -> new NotFoundException("Article", -1L));

        // 4. 결과 값 반환하기
        return ApiResponseDto.of("저장 완료", savedArticles);
    }
}


















