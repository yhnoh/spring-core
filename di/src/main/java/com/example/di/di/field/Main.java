package com.example.di.di.field;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        MemberJoin memberJoin = ac.getBean("memberJoin", MemberJoin.class);
        memberJoin.join("username", "email");
        memberJoin.join("username", "email");
    }
}
