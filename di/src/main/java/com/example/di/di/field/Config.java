package com.example.di.di.field;

import org.springframework.context.annotation.Bean;

/**
 * 필드 주입 방식
 * 1.객체 조립이 불가능 하다.
 * 2.의존성 주입을 받는 객체의 상태 변경이 한번에 불가능 하다. (사용자 중복 -> 이메일 중복)
 * 3.수백개의 클래스가 의존성을 주입받는 경우 실수가 분명히 일어난다.
 * 4.이 실수는 런타임 에러 시점에 확인 가능하다. (치명적)
 */
public class Config {
    @Bean
    public MemberJoin memberJoin(){
        return new MemberJoin();
    }
}
