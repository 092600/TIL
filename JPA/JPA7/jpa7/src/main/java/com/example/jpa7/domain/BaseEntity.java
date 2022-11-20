package com.example.jpa7.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {

    private Date createdDate;
    private Date lastModifiedDate;

}
