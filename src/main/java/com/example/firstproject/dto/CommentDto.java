package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 응답용 dto 입니다.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private Long articleId;
    private String nickname;
    private String body;

    /**
     * Comment 객체를 dto 형태로 변환합니다.
     */
    public static CommentDto from(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getArticle().getId(),
                comment.getNickname(),
                comment.getBody()
        );
    }

    /**
     * dto를 Comment 객체로 변환합니다.
     */
    public static Comment toEntity(Article article, CommentDto dto) {
        return new Comment(
                null, // id는 엔티티에서 자동 부여된다.
                article,
                dto.getNickname(),
                dto.getBody()
        );
    }
}
