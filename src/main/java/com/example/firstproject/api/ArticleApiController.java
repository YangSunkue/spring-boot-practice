package com.example.firstproject.api;

import com.example.firstproject.dto.ApiResponseDto;
import com.example.firstproject.dto.ArticleRequestDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.service.ArticleService;

import org.apache.coyote.Response;
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

    @Autowired
    private ArticleService articleService;

    // 모든 게시글 조회
    @GetMapping("/api/articles")
    public ApiResponseDto<List<Article>> index() {
        return articleService.index();
    }

    // 단일 게시글 조회
    @GetMapping("/api/articles/{id}")
    public ApiResponseDto<Article> show(@PathVariable Long id) {
        return articleService.show(id);
    }

    // 게시글 생성
    @PostMapping("/api/articles")
    public ResponseEntity<ApiResponseDto<Article>> create(@RequestBody ArticleRequestDto dto) {

        ApiResponseDto<Article> response = articleService.create(dto);
        Article created = response.getData();

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
        return articleService.update(id, dto);
    }

    // 게시글 삭제
    @DeleteMapping("/api/articles/{id}")
    public ApiResponseDto<Article> delete(@PathVariable Long id) {
        return articleService.delete(id);
    }

    /**
     * 트랜잭션 테스트
     */
    @PostMapping("/api/transaction-test")
    public ApiResponseDto<List<Article>> transactionTest(
            @RequestBody List<ArticleRequestDto> dtos
    ) {
        return articleService.createArticles(dtos);
    }
}