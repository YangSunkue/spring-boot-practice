package com.example.firstproject.dto;

import com.example.firstproject.entity.Coffee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CoffeeRequestDto {

    private Long id;
    private String name;
    private String price;

    public Coffee toEntity() {
        return new Coffee(name, price);
    }
}
