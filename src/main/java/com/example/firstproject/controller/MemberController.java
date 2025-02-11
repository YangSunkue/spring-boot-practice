package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 회원가입 관련 컨트롤러 입니다.
 */
@Slf4j
@Controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    /**
     * 회원가입 페이지 입니다.
     */
    @GetMapping("/signup")
    public String signUp() {
        return "members/new";
    }

    /**
     * 회원가입 :
     * 회원 정보를 받아 DB에 저장합니다.
     */
    @PostMapping("/join")
    public String newMember(MemberForm form) {
        // 0. 데이터를 DTO로 잘 받았는지 확인
        log.info(form.toString());

        // 1. DTO를 엔티티로 변환
        Member member = form.toEntity();
        log.info(member.toString());

        // 2. 리파지터리를 이용해 엔티티를 DB에 저장
        Member saved = memberRepository.save(member);
        log.info(saved.toString());

        return "members/new";
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model) {

        // 조회된 데이터가 없을 경우 예외 처리, 500 반환
        Member memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + id));
        model.addAttribute("member", memberEntity);
        return "members/show";
    }

    @GetMapping("/members")
    public String index(Model model) {

        // 데이터가 없을 경우 빈 컬렉션을 반환
        // 빈 컬렉션은 정상적인 응답응로 간주하므로 예외 처리 필요 없음
        // 만약 예외처리 한다면 뷰 단에서 하는 것이 바람직함
        Iterable<Member> memberEntityList = memberRepository.findAll();

        model.addAttribute("memberList", memberEntityList);
        return "members/index";
    }
}
