package com.example.jpa11.domain.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Setter
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;

}
