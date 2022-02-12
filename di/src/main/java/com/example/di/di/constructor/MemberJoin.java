package com.example.di.di.constructor;

import com.example.di.di.Member;
import com.example.di.di.MemberDuplication;

public class MemberJoin {


    MemberDuplication memberDuplication;
    //생성자 주입
    public MemberJoin(MemberDuplication memberDuplication) {
        this.memberDuplication = memberDuplication;
    }

    //회원 가입
    public Member join(String username, String email){
        Member member = new Member(username, email);
        Member duplicate = memberDuplication.duplicate(member);
        return member;
    }
}
