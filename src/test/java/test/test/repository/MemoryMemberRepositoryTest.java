package test.test.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import test.test.domain.Member;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//컨트롤 + 쉬프트 + t 로 자동으로 테스트 만들기
//@Transactional
class MemoryMemberRepositoryTest {

   MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    //각 메서드가 끝나고 실행되게
    public void afterEach(){
        //Store를 정리해주는 메서드인 clearStore()를 실행
        repository.clearStore();

    }

    @Test
    void 회원저장() {

        //given  이런 상황이 주어져서

        Member member = new Member();
        //새로운 아이디 cafe 만들기
        member.setId("cafe");
        //cafe를 member에 저장
        repository.save(member);


        //when 이걸 실행했을때

        //member에 저장된 id의 해당 num을 가져와서 저장이 됐는지 확인
        Member result = repository.findByNum(member.getNum()).get();


        // then 이런 결과가 나와야한다.

        System.out.println("result = " + (result == member));
        //두 값이 같은지 비교 (기대값) (결과값) 다르다면 빨간불
//        Assertions 알트+엔터 하면 static import로 저장 가능
        // 저장하면 앞에 생략하고 assertThat() 부터 쓰면 됨
        Assertions.assertThat(member).isEqualTo(result);





    }

    @Test
    void findByNum() {
        //given
        Member member1 = new Member();
        //        새로운 아이디 cafe1 을 만들고
        member1.setId("cafe1");

//        만들어진 member1을 repository에 저장
        repository.save(member1);

         //when
            Member result = repository.findByNum(1).get();
         // then
        Assertions.assertThat(result).isEqualTo(member1);
    }

    @Test
    void findById() {
        Member member1 = new Member();
//        새로운 아이디 cafe1 을 만들고
        member1.setId("cafe1");
//        만들어진 member1을 repository에 저장
        repository.save(member1);

//        복사 붙여넣기후 중복 이름 쉬프트+f6 하면 한번에 변경 가능
        Member member2 = new Member();
        member2.setId("cafe1");
        repository.save(member2);

        //기대값을 cafe2로 했을때 결과값을 확인함
        Member result = repository.findById("cafe1").get();
        
        //기대값은 result , 결과값이 member1
        Assertions.assertThat(result).isEqualTo(member1);

    }

    @Test
    void findAll() {
        Member member1 = new Member();
        member1.setId("cafe1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setId("cafe2");
        repository.save(member2);

//        저장된 후 repository에 저장된 모든 값을 List형태로 가져옴
        List<Member> result = repository.findAll();

//        result에 저장된 갯수와 내가 확인하려는 갯수가 같은지 확인
        Assertions.assertThat(result.size()).isEqualTo(2);

    }
}