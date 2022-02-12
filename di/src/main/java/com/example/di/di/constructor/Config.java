package com.example.di.di.constructor;

import com.example.di.di.MemberEmailDuplication;
import com.example.di.di.MemberUsernameDuplication;
import org.springframework.context.annotation.Bean;

/**
 * 생성자 주입 방식
 * 1.객체 조립을 객체를 생성할때 어떤것을 주입해할지 명확하다.
 * 2.가장 좋은 방식
 */
public class Config {

    //사용자 이름 중복 체크
    @Bean
    public MemberJoin memberJoin(){
        MemberJoin memberJoin = new MemberJoin(new MemberUsernameDuplication());
        return memberJoin;
    }


    //이메일 중복 체크
/*
    @Bean
    public MemberJoin memberJoin(){
        MemberJoin memberJoin = new MemberJoin(new MemberEmailDuplication());
        return memberJoin;
    }
*/

}
