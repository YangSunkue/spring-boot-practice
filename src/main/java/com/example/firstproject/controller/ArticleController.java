package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleRequestDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 게시글 관련 컨트롤러 입니다.
 */
@Controller
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * 게시글 조회 페이지입니다.
     */
    @GetMapping("articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        // 1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        // 3. 뷰 페이지 반환하기
        return "articles/show";
    }

    /**
     * 모든 게시글 조회 페이지입니다.
     */
    @GetMapping("/articles")
    public String index(Model model) {

        // Iterable을 그대로 사용해도 mustache는 아무런 문제 없이 반복 동작을 수행한다
        // 1. 모든 데이터 가져오기
        Iterable<Article> articleEntityList = articleRepository.findAll();

        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰 페이지 설정하기
        return "articles/index";
    }

    /**
     * 게시글 수정 페이지입니다.
     */
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {

        Article articleEntity = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + id));
        model.addAttribute("article", articleEntity);
        return "articles/edit";
    }

    /**
     * 게시글 수정 기능 api입니다.
     * @Transactional : 메서드 정상 종료 시 자동 커밋
     * patch 메서드를 이용한 엔티티 값 변경, 그리고 jpa의 변경 감지 기능을 통한 DB 데이터 UPDATE
     */
    @PostMapping("/articles/update")
    @Transactional
    public String update(ArticleRequestDto form) {
        log.info(form.toString());

        // 1. 기존 데이터 조회
        Article target = articleRepository.findById(form.getId())
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + form.getId()));

        // 2. 기존 엔티티 값 변경 ( update )
        target.patch(form); // patch 메서드로 필요한 필드만 업데이트

        // 3. 변경된 값 확인
        log.info("Update article: " + target.toString());

        // 4. 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + target.getId();
    }

    /**
     * 게시글 삭제 api 입니다.
     * HTML의 form은 delete 메소드를 제공하지 않으므로 GetMapping 사용
     */
    @GetMapping("/articles/{id}/delete")
    @Transactional
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {

        log.info("삭제 요청이 들어왔습니다!");

        // 1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("삭제할 %d번 데이터를 찾을 수 없습니다", id)
                ));
        log.info("삭제 대상 데이터 : " + target.toString());

        // 2. 대상 엔티티 삭제하기
        articleRepository.delete(target);

        // 리다이렉트 시점에 한 번만 사용할 데이터 등록
        // Model을 상속받는 객체라서 뷰 템플릿에서 사용 가능함
        rttr.addFlashAttribute("msg", String.format("%d번 글이 삭제되었습니다!", id));


        // 3. 결과 페이지로 리다이렉트
        return "redirect:/articles";
    }


    /**
     * 게시글 생성 페이지입니다.
     */
    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    /**
     * 게시글을 생성합니다.
     */
    @PostMapping("/articles/create")
    public String createArticle(ArticleRequestDto form) {
        log.info(form.toString());

        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. 리파지터리를 이용해 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }
}
