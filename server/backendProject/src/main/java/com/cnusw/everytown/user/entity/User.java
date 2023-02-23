package com.cnusw.everytown.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// name, id, pw, nickname
@Getter
@Entity
@NoArgsConstructor
@Builder
public class User {
    @Id
    private int id;

    @Column
    private String name;

    @Column
    private String password;

    @Column(unique = true)
    private String nickname;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void setPassword(String password) {
        this.password = password;
    }

    public User(int id, String name, String password, String nickname, String email, Authority authority) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.authority = authority;
    }
}
