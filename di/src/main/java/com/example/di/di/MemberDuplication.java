package com.example.di.di;

/**
 * 중복 체크 인터페이스
 * 객체의 상태가 변경해도 사용자의 코드를 변경하지 않기 위해서 다형성이 꼭 필요
 */
public interface MemberDuplication {
    Member duplicate(Member member);
}
