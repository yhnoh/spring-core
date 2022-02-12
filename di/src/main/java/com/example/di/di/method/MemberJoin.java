package com.example.di.di.method;

import com.example.di.di.Member;
import com.example.di.di.MemberDuplication;
import com.example.di.di.MemberUsernameDuplication;

public class MemberJoin {


    MemberDuplication memberDuplication;
    //메소드 주입
    public void setMemberDuplication(MemberDuplication memberDuplication) {
        this.memberDuplication = memberDuplication;
    }

    //회원 가입
    public Member join(String username, String email){
        Member member = new Member(username, email);
        Member duplicate = memberDuplication.duplicate(member);
        return member;
    }
}
