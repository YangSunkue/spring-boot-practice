package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberRequestDto;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
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
 * 회원가입 관련 컨트롤러 입니다.
 */
@Controller
@Slf4j
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
    public String newMember(MemberRequestDto form) {
        // 0. 데이터를 DTO로 잘 받았는지 확인
        log.info(form.toString());

        // 1. DTO를 엔티티로 변환
        Member member = form.toEntity();
        log.info(member.toString());

        // 2. 리파지터리를 이용해 엔티티를 DB에 저장
        Member saved = memberRepository.save(member);
        log.info(saved.toString());

        return "redirect:/members/" + saved.getId();
    }

    /**
     * 회원 상세 조회 페이지 입니다.
     */
    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model) {

        // 조회된 데이터가 없을 경우 예외 처리, 500 반환
        Member memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + id));
        model.addAttribute("member", memberEntity);
        return "members/show";
    }

    /**
     * 회원 정보 수정 페이지 입니다.
     */
    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {

        Member memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + id));
        model.addAttribute("member", memberEntity);

        return "members/edit";
    }

    /**
     * 회원 정보 수정 api 입니다.
     */
    @PostMapping("/members/update")
    @Transactional
    public String update(MemberRequestDto form) {

        log.info("수정 요청 데이터 : " + form.toString());

        Member target = memberRepository.findById(form.getId())
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + form.getId()));
        log.info("기존 데이터 : " + target.toString());

        target.patch(form);
        log.info("수정된 데이터 : " + target.toString());

        return "redirect:/members/" + target.getId();
    }

    /**
     * 회원 정보 삭제 api 입니다.
     */
    @GetMapping("/members/{id}/delete")
    @Transactional
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {

        log.info(String.format("%d번 회원 삭제 요청이 들어왔습니다!", id));

        Member target = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("삭제할 %d번 회원이 존재하지 않습니다.", id)
                ));
        log.info("삭제 대상 회원 정보 : " + target.toString());

        memberRepository.delete(target);
        rttr.addFlashAttribute("msg", String.format("%d번 회원이 성공적으로 삭제되었습니다!", id));

        return "redirect:/members";
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
