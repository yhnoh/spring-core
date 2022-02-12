package com.example.di.autowired;

import java.util.HashMap;
import java.util.Map;

//Email 중복체크
public class MemberEmailDuplication implements MemberDuplication {
    public static Map<String, Member> members = new HashMap<>();

    @Override
    public Member duplicate(Member member) {
        if(members.containsKey(member.getEmail())){
            throw new IllegalArgumentException("사용자 이메일은 이미 존재합니다.");
        }
        members.put(member.getEmail(), member);
        return member;

    }
}
