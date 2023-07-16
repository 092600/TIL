package com.chap_6.domain.product;


import com.chap_6.domain.product.currency.Currency;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 45, nullable = false)
    private String price;

    @Enumerated(EnumType.STRING)
    private Currency currency;
}
