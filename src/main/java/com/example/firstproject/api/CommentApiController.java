package com.example.firstproject.api;

import com.example.firstproject.constant.CommentResponseMessage;
import com.example.firstproject.dto.ApiResponseDto;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@ResponseStatus(HttpStatus.OK)
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    /**
     * 특정 게시글의 모든 댓글을 조회합니다.
     */
    @GetMapping("/api/articles/{articleId}/comments")
    public ApiResponseDto<List<CommentDto>> getArticleComments(@PathVariable Long articleId) {

        List<CommentDto> comments = commentService.findCommentsByArticleId(articleId);

        return ApiResponseDto.of(CommentResponseMessage.COMMENTS_RETRIEVED, comments);
    }


    /**
     * 특정 게시글의 댓글을 생성합니다.
     */
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<ApiResponseDto<CommentDto>> createArticleComment(
            @PathVariable Long articleId,
            @RequestBody CommentDto dto
    ) {
        // 게시글 생성
        CommentDto created = commentService.saveArticleComment(articleId, dto);

        // 응답 형태로 변환 및 리턴
        ApiResponseDto<CommentDto> response = ApiResponseDto.of(
                CommentResponseMessage.COMMENT_CREATED,
                created
        );
        return ResponseEntity.created(URI.create("/api/articles/" + articleId + "/comments"))
                .body(response);
    }



    /**
     * 댓글을 수정합니다.
     */
    @PatchMapping("/api/comments/{id}")
    public ApiResponseDto<CommentDto> updateComment(
            @PathVariable Long id,
            @RequestBody CommentDto dto
    ) {
        CommentDto updated = commentService.updateComment(id, dto);

        return ApiResponseDto.of(CommentResponseMessage.COMMENT_UPDATED, updated);
    }


    /**
     * 댓글을 삭제합니다.
     */
    @DeleteMapping("/api/comments/{id}")
    public ApiResponseDto<CommentDto> deleteComment(@PathVariable Long id) {

        CommentDto deleted = commentService.deleteComment(id);

        return ApiResponseDto.of(CommentResponseMessage.COMMENT_DELETED, deleted);
    }


}




















