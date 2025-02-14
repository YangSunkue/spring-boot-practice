package com.example.firstproject.api;

import com.example.firstproject.dto.ApiResponseDto;
import com.example.firstproject.dto.ArticleRequestDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.exception.NotFoundException;
import com.example.firstproject.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


/**
 * 게시글 관련 Rest Api 컨트롤러 입니다.
 * 뷰 템플릿이 아닌 JSON 데이터를 반환합니다.
 * @RestController : 해당 어노테이션이 붙은 컨트롤러의 리턴값은 자동으로 JSON 변환 대상이 됩니다.
 */
@RestController
@Slf4j
public class ArticleApiController {

    @Autowired
    private ArticleRepository articleRepository;

    // GET
    // 모든 게시글 조회
    @GetMapping("/api/articles")
    public ResponseEntity<ApiResponseDto<Iterable<Article>>> index() {

        Iterable<Article> articleList = articleRepository.findAll();

        return ResponseEntity.ok(
                ApiResponseDto.of("Articles successfully retrieved", articleList)
        );
    }

    // 단일 게시글 조회
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ApiResponseDto<Article>> show(@PathVariable Long id) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Article", id));

        return ResponseEntity.ok(
                ApiResponseDto.of("Article successfully retrieved", article)
        );
    }


    // POST
    // 게시글 생성
    @PostMapping("/api/articles")
    public ResponseEntity<ApiResponseDto<Article>> create(@RequestBody ArticleRequestDto dto) {
        Article article = dto.toEntity();
        Article saved = articleRepository.save(article);

        // URI는 헤더의 Location에 포함되어 응답된다.
        // REST 규약 준수 : 생성된 리소스의 경로를 포함하는 것은 RestAPI 아키텍처 권장사항이다.
        // 클라이언트 편의성 : 클라이언트 측에서 생성된 리소스를 즉시 조회할 수 있고 경로를 예상할 필요가 없다.
        return ResponseEntity
                .created(URI.create("/api/articles/" + saved.getId()))
                .body(ApiResponseDto.of("Article successfully created", saved));
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    @Transactional
    public ResponseEntity<ApiResponseDto<Article>> update(
            @PathVariable Long id,
            @RequestBody ArticleRequestDto dto
    ) {

        Article target = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Article", id)); // 커스텀 예외 적용
        log.info("UPDATE 대상 데이터 : {}", target);

        target.patch(dto);
        log.info("UPDATE 결과 데이터 : {}", target);

        return ResponseEntity.ok(
                ApiResponseDto.of("Article successfully updated", target)
        );
    }

    // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable Long id) {

        Article target = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Article", id));
        log.info("DELETE 대상 데이터 : {}", target);

        articleRepository.delete(target);

        return ResponseEntity.ok()
                .body(ApiResponseDto.of("Article successfully deleted", target));
    }
}