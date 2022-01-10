package test.test.repository;

import test.test.domain.Member;

import java.util.List;
import java.util.Optional;

// 멤버 관련 구현할 기능들

public interface MemberRepository {

    Member save(Member member); //회원가입하기
    Optional<Member> findByNum(int num); //등록번호 중복방지
    Optional<Member> findById(String id); //아이디 중복방지
    List<Member> findAll(); // 전체출력
}
