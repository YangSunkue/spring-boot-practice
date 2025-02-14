package com.example.firstproject.entity;

import com.example.firstproject.dto.CoffeeRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String price;

    public Coffee(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public void patch(CoffeeRequestDto dto) {
        if (dto.getName() != null) {
            this.name = dto.getName();
        }
        if (dto.getPrice() != null) {
            this.price = dto.getPrice();
        }
    }
}
