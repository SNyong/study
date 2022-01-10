package test.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.test.domain.Member;
import test.test.repository.MemberRepository;
import test.test.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;


public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public int join(Member member) {
        //같은 아이디 회원가입 불가능
        memberRepository.findById(member.getId())//입력된 아이디를 찾아보고
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 아이디 입니다");//null이 아니라면(입력한 아이디가 이미 있으면) (!= null)
                });

        memberRepository.save(member); // 위에 검증되면 저장장
        return member.getNum();
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();

    }

    /**
     * 번호, 아이디 중복방지
     */
    public Optional<Member> findOne(String memberId) {
        return memberRepository.findById(memberId);
    }

    public Optional<Member> findOne(int memberNum) {
        return memberRepository.findByNum(memberNum);
    }


}


