package com.chap11_authserver.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class User {
    @Id
    private String username;
    private String password;
}
