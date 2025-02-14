package com.example.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestApi 설계를 따르는 첫번째 컨트롤러 입니다.
 * @RestController : @Controller + @ResponseBody -> @ResponseBody는 뷰 템플릿이 아닌 데이터 자체를 반환하고 싶을 때 사용한다.
 */
@RestController
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello() {
        return "hello world!";
    }
}
