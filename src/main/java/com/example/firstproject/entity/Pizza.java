package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private BigDecimal price;
}

/**
 * GET
 * /api/pizzas
 * /api/pizzas/{id}
 *
 * POST
 * /api/pizzas
 *
 * PATCH
 * /api/pizzas/{id}
 *
 * DELETE
 * /api/pizzas/{id}
 */