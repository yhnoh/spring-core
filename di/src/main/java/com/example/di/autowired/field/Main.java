package com.example.di.autowired.field;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//실행 클래스
public class Main {

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        MemberJoin memberJoin = ac.getBean("memberJoin", MemberJoin.class);
        memberJoin.join("username", "email");
        memberJoin.join("username", "email");
    }
}
