package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 회원가입 요청 데이터를 전달받는 dto 입니다.
 */
@AllArgsConstructor
@ToString
@Getter
public class MemberRequestDto {

    private Long id;
    private String email;
    private String password;

    // 엔티티 변환 함수
    public Member toEntity() {
        return new Member(id, email, password);
    }
}