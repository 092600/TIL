package com.example.jpa7.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Album")
public class Album extends Item{
    private String artist;
    private String etc;

}
