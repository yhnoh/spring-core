package com.example.di.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        //빈 객체 관리 확인
        Hello hello1 = ac.getBean("hello", Hello.class);
        String format1 = hello1.helloFormat("스프링2");
        System.out.println(format1);

        //객체를 한번만 생성하는지 확인
        Hello hello2 = ac.getBean("hello", Hello.class);
        if(hello1 == hello2){
            System.out.println("hello1 과 hello2는 같다");
        }

        //싱글톤이기 대문에 동시성 이슈 발생
        hello1.setHello("hello1");
        hello1.setHello("hello2");

        System.out.println("hello1 = " + hello1.getHello());
        System.out.println("hello2 = " + hello2.getHello());
    }
}
