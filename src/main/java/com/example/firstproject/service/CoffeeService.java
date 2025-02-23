package com.example.firstproject.service;

import com.example.firstproject.dto.ApiResponseDto;
import com.example.firstproject.dto.CoffeeRequestDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.exception.NotFoundException;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 커피 관련 서비스 클래스입니다.
 */
@Transactional(readOnly = true)
@Slf4j
@Service
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    /**
     * 모든 커피 목록을 조회합니다.
     */
    public ApiResponseDto<List<Coffee>> index() {

        List<Coffee> coffeeList = coffeeRepository.findAll();

        return ApiResponseDto.of("Coffees successfully retrieved", coffeeList);
    }

    /**
     * 단일 커피를 조회합니다.
     */
    public ApiResponseDto<Coffee> show(Long id) {

        Coffee coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Coffee", id));

        return ApiResponseDto.of("Coffee successfully retrieved", coffee);
    }

    /**
     * 새로운 커피를 생성합니다.
     */
    @Transactional
    public ApiResponseDto<Coffee> create(CoffeeRequestDto dto) {

        Coffee coffee = dto.toEntity();
        Coffee savedCoffee = coffeeRepository.save(coffee);

        return ApiResponseDto.of("Coffee successfully created", savedCoffee);
    }

    /**
     * 커피 정보를 수정합니다.
     */
    @Transactional
    public ApiResponseDto<Coffee> update(
            Long id,
            CoffeeRequestDto dto
    ) {

        Coffee target = coffeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Coffee", id));
        log.info("수정 대상 데이터 : {}", target);

        target.patch(dto);
        log.info("수정 후 데이터 : {}", target);

        return ApiResponseDto.of("Coffee successfully updated", target);
    }

    /**
     * 커피 정보를 삭제합니다.
     */
    @Transactional
    public ApiResponseDto<Coffee> delete(Long id) {

        Coffee target = coffeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Coffee", id));
        log.info("삭제 대상 데이터 : {}", target);

        coffeeRepository.delete(target);

        return ApiResponseDto.of("Coffee successfully deleted", target);
    }
}






