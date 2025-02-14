package com.example.firstproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 커스텀 예외의 기본 틀이 되는 클래스
 *
 * abstract : 추상 클래스 -> 직접 객체를 생성할 수 없고 상속받기 위한 기본 틀 역할을 한다
 * RuntimeException : Java의 기본 예외 클래스
 */
@Getter
public abstract class BaseException extends RuntimeException {

    private final HttpStatus status;

    protected BaseException(String message, HttpStatus status) {
        super(message); // 부모 클래스 생성자 호출, 예외 메시지 전달
        this.status = status;
    }
}