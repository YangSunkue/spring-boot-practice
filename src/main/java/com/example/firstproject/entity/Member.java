package com.example.firstproject.entity;

import com.example.firstproject.dto.MemberRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 엔티티 클래스입니다.
 * Member 테이블과 연결됩니다.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String email;
    @Column
    private String password;

    public void patch(MemberRequestDto form) {
        if (form.getEmail() != null) {
            this.email = form.getEmail();
        }
        if (form.getPassword() != null) {
            this.password = form.getPassword();
        }
    }
}
