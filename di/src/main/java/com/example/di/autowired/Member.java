package com.example.di.autowired;

import lombok.Getter;

@Getter
public class Member {
    private String username;
    private String email;

    public Member(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
