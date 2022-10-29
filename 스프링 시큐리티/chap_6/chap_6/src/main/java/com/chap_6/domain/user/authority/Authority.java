package com.chap_6.domain.user.authority;

import com.chap_6.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 45, nullable = false)
    private String name;

    @JoinColumn(name = "user")
    @ManyToOne
    private User user;
}
