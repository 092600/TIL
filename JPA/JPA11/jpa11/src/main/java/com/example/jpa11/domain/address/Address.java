package com.example.jpa11.domain.address;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Setter
@Getter
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

}
