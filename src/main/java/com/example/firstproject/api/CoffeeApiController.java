package com.example.firstproject.api;

import com.example.firstproject.dto.ApiResponseDto;
import com.example.firstproject.dto.CoffeeRequestDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.exception.NotFoundException;
import com.example.firstproject.repository.CoffeeRepository;
import com.example.firstproject.service.CoffeeService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@ResponseStatus(HttpStatus.OK)
@RestController
public class CoffeeApiController {

    @Autowired
    private CoffeeService coffeeService;

    /**
     * 모든 커피 목록을 조회합니다.
     */
    @GetMapping("/api/coffees")
    public ApiResponseDto<List<Coffee>> index() {
        return coffeeService.index();
    }

    /**
     * 단일 커피를 조회합니다.
     */
    @GetMapping("/api/coffees/{id}")
    public ApiResponseDto<Coffee> show(@PathVariable Long id) {
        return coffeeService.show(id);
    }

    /**
     * 새로운 커피를 생성합니다.
     */
    @PostMapping("/api/coffees")
    public ResponseEntity<ApiResponseDto<Coffee>> create(@RequestBody CoffeeRequestDto dto) {

        ApiResponseDto<Coffee> response = coffeeService.create(dto);

        return ResponseEntity
                .created(URI.create("/api/coffees" + response.getData().getId()))
                .body(response);
    }

    /**
     * 커피 정보를 수정합니다.
     */
    @PatchMapping("/api/coffees/{id}")
    public ApiResponseDto<Coffee> update(
            @PathVariable Long id,
            @RequestBody CoffeeRequestDto dto
    ) {
        return coffeeService.update(id, dto);
    }

    /**
     * 커피 정보를 삭제합니다.
     */
    @DeleteMapping("/api/coffees/{id}")
    public ApiResponseDto<Coffee> delete(@PathVariable Long id) {
        return coffeeService.delete(id);
    }
}