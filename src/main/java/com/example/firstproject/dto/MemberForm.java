package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;

/**
 * 회원가입 요청 데이터를 전달받는 dto 입니다.
 */
public class MemberForm {

    private String email;
    private String password;

    public MemberForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "MemberForm{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    // 엔티티 변환 함수
    public Member toEntity() {
        return new Member(null, email, password);
    }
}