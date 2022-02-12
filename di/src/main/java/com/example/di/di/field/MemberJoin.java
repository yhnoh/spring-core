package com.example.di.di.field;

import com.example.di.di.Member;
import com.example.di.di.MemberDuplication;
import com.example.di.di.MemberUsernameDuplication;

public class MemberJoin {

    //필드 주입
    MemberDuplication memberDuplication = new MemberUsernameDuplication();

    //회원 가입
    public Member join(String username, String email){
        Member member = new Member(username, email);
        Member duplicate = memberDuplication.duplicate(member);
        return member;
    }
}
