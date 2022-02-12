package com.example.di.bean;

import org.springframework.context.annotation.Bean;


public class Config {

    //메인에서 직접 생성하지 않음, 즉 스프링 컨테이너가 관리
    @Bean
    public Hello hello(){
        Hello hello = new Hello();
        //어플리케이션 실행시점에 실제로 빈이 등록 되는지 확인
        String format = hello.helloFormat("스프링1");
        System.out.println(format);
        return hello;
    }
}
