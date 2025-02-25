package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.exception.NotFoundException;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    /**
     * 특정 게시글의 모든 댓글을 조회합니다.
     */
    public List<CommentDto> findCommentsByArticleId(Long id) {

        List<Comment> comments = commentRepository.findByArticleId(id);

        return comments.stream()
                .map(CommentDto::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 게시글의 댓글을 생성합니다.
     */
    @Transactional
    public CommentDto saveArticleComment(Long articleId, CommentDto dto) {

        // 부모 게시글 찾기
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NotFoundException("Article", articleId));

        // dto를 엔티티로 변환
        Comment commentEntity = dto.toEntity(article, dto);

        // db에 저장
        Comment saved = commentRepository.save(commentEntity);

        // 저장한 거 dto로 다시 변환
        CommentDto savedDto = CommentDto.toDto(saved);

        // 리턴
        return savedDto;
    }

    /**
     * 댓글을 수정합니다.
     */
    @Transactional
    public CommentDto updateComment(Long id, CommentDto dto) {

        // 수정할 댓글 찾기
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment", id));

        // 댓글 수정
        target.patch(dto);

        // dto 형태로 변환
        CommentDto targetDto = CommentDto.toDto(target);

        // 리턴
        return targetDto;
    }

    @Transactional
    public CommentDto deleteComment(Long id) {

        // 수정할 댓글 찾기
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment", id));

        // 댓글 삭제
        commentRepository.delete(target);

        // dto로 변환
        CommentDto targetDto = CommentDto.toDto(target);

        // 리턴
        return targetDto;
    }
}














