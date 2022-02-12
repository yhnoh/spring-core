package com.example.di.autowired.field;

import com.example.di.autowired.MemberDuplication;
import com.example.di.autowired.MemberEmailDuplication;
import org.springframework.context.annotation.Bean;

//설정 클래스
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
