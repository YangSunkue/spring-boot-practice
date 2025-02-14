package com.example.firstproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponseDto<T> { // 제네릭 변수 T를 사용하겠다

    private String message;
    private T data;

    // 데이터 없이 메시지만 보낼 경우 사용
    public static <T> ApiResponseDto<T> of(String message) { // 자유로운 타입의 T 제네릭을 사용할 것이고 그 타입은 T(자유로운)이다
        return new ApiResponseDto<>(message, null); // AllArgsConstructor에 의해 생성된 생성자 호출
    }

    // 데이터와 메시지 모두 보낼 경우 사용
    public static <T> ApiResponseDto<T> of(String message, T data) {
        return new ApiResponseDto<>(message, data);
    }
}
