package com.example.firstproject.api;

import com.example.firstproject.dto.ApiResponseDto;
import com.example.firstproject.dto.CoffeeRequestDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.exception.NotFoundException;
import com.example.firstproject.repository.CoffeeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Slf4j
public class CoffeeApiController {

    @Autowired
    private CoffeeRepository coffeeRepository;

    /**
     * 모든 커피 목록을 조회합니다.
     */
    @GetMapping("/api/coffees")
    public ResponseEntity<ApiResponseDto<Iterable<Coffee>>> index() {

        Iterable<Coffee> coffeeList = coffeeRepository.findAll();

        return ResponseEntity.ok(
                ApiResponseDto.of("Coffees successfully retrieved", coffeeList)
        );
    }

    /**
     * 단일 커피를 조회합니다.
     */
    @GetMapping("/api/coffees/{id}")
    public ResponseEntity<ApiResponseDto<Coffee>> show(@PathVariable Long id) {

        Coffee coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Coffee", id));

        return ResponseEntity.ok(
                ApiResponseDto.of("Coffee successfully retrieved", coffee)
        );
    }

    /**
     * 새로운 커피를 생성합니다.
     */
    @PostMapping("/api/coffees")
    public ResponseEntity<ApiResponseDto<Coffee>> create(@RequestBody CoffeeRequestDto dto) {

        Coffee coffee = dto.toEntity();
        Coffee saved = coffeeRepository.save(coffee);

        return ResponseEntity
                .created(URI.create("/api/coffees/" + saved.getId()))
                .body(ApiResponseDto.of("Coffee successfully created", saved));
    }

    /**
     * 커피 정보를 수정합니다.
     */
    @PatchMapping("/api/coffees/{id}")
    @Transactional
    public ResponseEntity<ApiResponseDto<Coffee>> update(
            @PathVariable Long id,
            @RequestBody CoffeeRequestDto dto
    ) {

        Coffee target = coffeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Coffee", id));
        log.info("수정 대상 데이터 : {}", target);

        target.patch(dto);
        log.info("수정 후 데이터 : {}", target);

        return ResponseEntity.ok(
                ApiResponseDto.of("Coffee successfully updated", target)
        );
    }

    /**
     * 커피 정보를 삭제합니다.
     */
    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<ApiResponseDto<Coffee>> delete(@PathVariable Long id) {

        Coffee target = coffeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Coffee", id));
        log.info("삭제 대상 데이터 : {}", target);

        coffeeRepository.delete(target);

        return ResponseEntity.ok(
                ApiResponseDto.of("Coffee successfully deleted", target)
        );
    }
}