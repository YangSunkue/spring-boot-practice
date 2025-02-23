package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.support.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.firstproject.entity.QComment.comment;

/**
 * 댓글 커스텀 리포지토리 구현체입니다.
 */
@Repository
public class CommentRepositoryCustomImpl extends Querydsl4RepositorySupport implements CommentRepositoryCustom{

    /**
     * QuerydslConfig에서 JPAQueryFactory를 스프링 컨테이너에 빈으로써 등록해 두었다.
     * 스프링은 컨테이너에서 객체를 찾아, 생성자의 인자로 자동으로 전달한다.
     *
     * 이 클래스는 @Repository 어노테이션이 달려 있기 때문에 자동으로 빈으로 등록되고 인식되는 것이다.
     *
     */

    /**
     * 실제 리파지터리(CommentRepository)에서 Impl을 상속받고 사용할 때,
     * 이 생성자가 자동으로 호출되며 JPAQueryFactory에 대한 의존성 주입이 일어난다.
     */
    public CommentRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        super(queryFactory, Comment.class);
    }

    /**
     * nickname, body를 포함하는 레코드를 모두 찾습니다. ( LIKE )
     */
    @Override
    public List<Comment> search(String nickname, String body) {
        return getQueryFactory()
                .selectFrom(comment)
                .where(
                        nicknameContains(nickname),
                        bodyContains(body)
                )
                .fetch();
    }

    /**
     * body가 일치하는 레코드를 모두 찾습니다.
     */
    @Override
    public List<Comment> findByBody(String body) {
        return getQueryFactory()
                .selectFrom(comment)
                .where(
                        comment.body.eq(body)
                )
                .fetch();
    }

    /**
     * QueryDSL은 where절에 null이 들어올 경우 조건을 무시합니다.
     *
     * nickname 또는 body 값이 null일 경우,
     * 해당 조건을 제외하는 동적 쿼리 작성을 가능하게 합니다.
     */
    private BooleanExpression nicknameContains(String nickname) {
        return StringUtils.hasText(nickname) ? comment.nickname.contains(nickname) : null;
    }

    private BooleanExpression bodyContains(String body) {
        return StringUtils.hasText(body) ? comment.body.contains(body) : null;
    }

}