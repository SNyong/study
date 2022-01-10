package test.test.repository;


import test.test.domain.Member;

import java.util.*;

//인터페이스에서 만든 기능들을 구현화 하기


public class MemoryMemberRepository implements MemberRepository {

    private static Map<Integer, Member> store = new HashMap<>();

    //sequence -> 0,1,2 이런식으로 계속생성해주게 하는것
    private static int sequence = 0;


    //저장부분
    @Override
    public Member save(Member member) {
        //sequence를 하나씩 추가해서 num를 1씩 올리고
        member.setNum(++sequence);

        //member(num,id,pw)를 store에 저장함(Map에 저장)
        store.put(member.getNum(), member);
        return member;
    }

    @Override
    public Optional<Member> findByNum(int num) {
        //store에서 num을 가져와서 검색함
        return Optional.ofNullable(store.get(num));
    }

    @Override
    public Optional<Member> findById(String id) {
        //아이디를 검색해서 같은지 확인
        return store.values().stream()
                .filter(member -> member.getId().equals(id))
                .findAny();

    }

    @Override
    public List<Member> findAll() {
//        store에 들어있는값들을 ArrayList로 번환함
        return new ArrayList<>(store.values());
    }


    //store를 클리어해주는 메서드를 만듦
    public void clearStore() {
        store.clear();
    }

}
