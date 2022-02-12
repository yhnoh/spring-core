package com.example.di.autowired.method;

import com.example.di.autowired.MemberDuplication;
import com.example.di.autowired.MemberEmailDuplication;
import org.springframework.context.annotation.Bean;

/**
 * 메소드 주입 방식
 * 1.객체의 조립이 가능하다.
 * 2.인스턴스를 생성하고 난 뒤에 객체의 사용자는 다시 setter메소드를 이용해서 의존성 주입을 해줘야 한다.
 * 3.런타임 에러가 발생할 가능성이 있다.
 */
public class Config {

//    @Bean
//    public MemberDuplication memberDuplication(){
//        return new MemberUsernameDuplication();
//    }

    @Bean
    public MemberDuplication memberDuplication(){
        return new MemberEmailDuplication();
    }

    @Bean
    public MemberJoin memberJoin(){
        return new MemberJoin();
    }

}
