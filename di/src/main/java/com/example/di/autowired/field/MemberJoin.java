package com.example.di.autowired.field;

import com.example.di.autowired.Member;
import com.example.di.autowired.MemberDuplication;
import com.example.di.autowired.MemberUsernameDuplication;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberJoin {

    /**
     * 필드 자동 주입
     * 개발자가 new 키워드를 사용하여 인스턴스를 생성한 적 없음
     * 즉, 스프링에게 의존 주입을 위임한 경우
     * 다형성을 활용했기 때문에 Config.java에서 다른 기능으로 변경이 가능
     */
    @Autowired
    MemberDuplication memberDuplication;

    //회원 가입
    public Member join(String username, String email){
        Member member = new Member(username, email);
        Member duplicate = memberDuplication.duplicate(member);
        return member;
    }
}
