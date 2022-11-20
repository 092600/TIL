package com.example.jpa7.domain.item;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Book")
public class Book extends Item{
    private String author;
    private String lsbn;
}
