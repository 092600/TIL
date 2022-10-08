package com.example.security_project.domain.product;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    private String name;
    private double price;

    @Enumerated(EnumType.STRING)
    public Currency currency;
}
