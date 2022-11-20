package com.example.jpa7.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Movie")
public class Movie extends Item{
    private String artist;
    private String etc;
}
