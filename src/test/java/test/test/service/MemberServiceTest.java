package test.test.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.test.domain.Member;
import test.test.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.xmlunit.util.Linqy.count;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    //각 테스트가 실행되기 전에 두 객체를 항상만들어냄
    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given  이런 상황이 주어져서
        Member member = new Member();
        member.setId("cafe");

        //when 이걸 실행했을때
        int saveNum = memberService.join(member);

        // then 이런 결과가 나와야한다.

        Member findMember = memberService.findOne(saveNum).get();
        assertThat(member.getId()).isEqualTo(findMember.getId());
//        assertThat(입력한 아이디()).isEqualTo(저장된 아이디) 같은지 확인
    }

    @Test
    void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setId("cafe");

        Member member2 = new Member();
        member2.setId("cafe");

        //when
        memberService.join(member1);
                                // 여기서 MemberService.join(mamber2) 가 정상작동이 안되면 통과
                                //IllegalStateException.class -> 제대로 안돌아갔을때를 참으로 함
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        //then
        //위에 로직이 정상작동 안됐을때 뜨는 문구가 아래와 같은지 비교
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디 입니다");
    }

    @Test
    void 회원전체찾기() {

        //given
            Member member1 = new Member();
            member1.setId("cafe1");
            member1.setNum(1);

            Member member2 = new Member();
            member2.setId("cafe2");
            member2.setNum(2);

            memberService.join(member1);
            memberService.join(member2);
        //when
            List<Member> result = memberService.findMembers();
        // then
        System.out.println(count(result));
//        System.out.println(result.stream());


        for(int i = 0; i < result.size(); i++){
            System.out.println(result.get(i).getId());
            System.out.println(Integer.toString(result.get(i).getNum()));

        }

    }

    @Test
    void 회원한명검색() {
        //given
        Member member1 = new Member();
        member1.setId("cafe1");
        member1.setNum(1);
        member1.setPw("pwpw");

        Member member2 = new Member();
        member2.setId("cafe2");
        member2.setNum(2);
        member2.setPw("pwpw2");

        memberService.join(member1);
        memberService.join(member2);
        //when
        Optional<Member> result = memberService.findOne("cafe1");

        // then

        System.out.println("ID = " + result.get().getId());
        System.out.println("회원번호 = " + result.get().getNum());
        System.out.println("비밀번호 = " + result.get().getPw());
    }
}