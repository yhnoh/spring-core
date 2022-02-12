package com.example.di.di;

import java.util.HashMap;
import java.util.Map;

//Username 중복 체크
public class MemberUsernameDuplication implements MemberDuplication{
    private static Map<String, Member> members = new HashMap<>();
    @Override
    public Member duplicate(Member member) {
        if(members.containsKey(member.getUsername())){
            throw new IllegalArgumentException("사용자 이름은 이미 존재합니다.");
        }
        members.put(member.getUsername(), member);
        return member;
    }
}
