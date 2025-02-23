package com.example.firstproject.api;

import com.example.firstproject.constant.ArticleResponseMessage;
import com.example.firstproject.dto.ApiResponseDto;
import com.example.firstproject.dto.ArticleRequestDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


/**
 * 게시글 관련 Rest Api 컨트롤러 입니다.
 * 뷰 템플릿이 아닌 JSON 데이터를 반환합니다.
 * @RestController : 해당 어노테이션이 붙은 컨트롤러의 리턴값은 자동으로 JSON 변환 대상이 됩니다.
 */
@RestController
@ResponseStatus(HttpStatus.OK)
public class ArticleApiController {

    private final ArticleService articleService;

    public ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 모든 게시글 조회
    @GetMapping("/api/articles")
    public ApiResponseDto<List<Article>> index() {

        List<Article> articles = articleService.index();

        return ApiResponseDto.of(ArticleResponseMessage.ARTICLES_RETRIEVED, articles);
    }

    // 단일 게시글 조회
    @GetMapping("/api/articles/{id}")
    public ApiResponseDto<Article> show(@PathVariable Long id) {

        Article article = articleService.show(id);

        return ApiResponseDto.of(ArticleResponseMessage.ARTICLE_RETRIEVED, article);
    }

    // 게시글 생성
    @PostMapping("/api/articles")
    public ResponseEntity<ApiResponseDto<Article>> create(@RequestBody ArticleRequestDto dto) {

        // 게시글 생성
        Article created = articleService.create(dto);

        // 응답 형태로 변환
        ApiResponseDto<Article> response = ApiResponseDto.of(
                ArticleResponseMessage.ARTICLE_CREATED,
                created
        );

        // URI는 헤더의 Location에 포함되어 응답된다.
        // REST 규약 준수 : 생성된 리소스의 경로를 포함하는 것은 RestAPI 아키텍처 권장사항이다.
        // 클라이언트 편의성 : 클라이언트 측에서 생성된 리소스를 즉시 조회할 수 있고 경로를 예상할 필요가 없다.
        return ResponseEntity
                .created(URI.create("/api/articles/" + created.getId()))
                .body(response);
    }

    // 게시글 수정
    @PatchMapping("/api/articles/{id}")
    public ApiResponseDto<Article> update(
            @PathVariable Long id,
            @RequestBody ArticleRequestDto dto
    ) {
        Article target = articleService.update(id, dto);

        return ApiResponseDto.of(ArticleResponseMessage.ARTICLE_UPDATED, target);
    }

    // 게시글 삭제
    @DeleteMapping("/api/articles/{id}")
    public ApiResponseDto<Article> delete(@PathVariable Long id) {

        Article target = articleService.delete(id);

        return ApiResponseDto.of(ArticleResponseMessage.ARTICLE_DELETED, target);
    }

    /**
     * 트랜잭션 테스트
     */
    @PostMapping("/api/transaction-test")
    public ApiResponseDto<List<Article>> transactionTest(
            @RequestBody List<ArticleRequestDto> dtos
    ) {
        List<Article> savedArticles = articleService.createArticles(dtos);

        return ApiResponseDto.of("저장 완료", savedArticles);
    }
}