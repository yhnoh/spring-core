package com.example.di.di.method;

import com.example.di.di.MemberEmailDuplication;
import com.example.di.di.MemberUsernameDuplication;
import org.springframework.context.annotation.Bean;

/**
 * 메소드 주입 방식
 * 1.객체의 조립이 가능하다.
 * 2.인스턴스를 생성하고 난 뒤에 객체의 사용자는 다시 setter메소드를 이용해서 의존성 주입을 해줘야 한다.
 * 3.런타임 에러가 발생할 가능성이 있다.
 */
public class Config {
    //사용자 이름 중복 체크
    @Bean
    public MemberJoin memberJoin(){
        MemberJoin memberJoin = new MemberJoin();
        memberJoin.setMemberDuplication(new MemberUsernameDuplication());
        return memberJoin;
    }

    //이메일 중복 체크
/*
    @Bean
    public MemberJoin memberJoin(){
        MemberJoin memberJoin = new MemberJoin();
        memberJoin.setMemberDuplication(new MemberEmailDuplication());
        return memberJoin;
    }
*/

}
