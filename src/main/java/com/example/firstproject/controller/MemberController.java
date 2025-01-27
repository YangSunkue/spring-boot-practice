package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 회원가입 관련 컨트롤러 입니다.
 */
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
        System.out.println(form.toString());

        // 1. DTO를 엔티티로 변환
        Member member = form.toEntity();
        System.out.println(member.toString());

        // 2. 리파지터리를 이용해 엔티티를 DB에 저장
        Member saved = memberRepository.save(member);
        System.out.println(saved.toString());

        return "members/new";
    }
}
