package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence); // 회원의 id를 저장할 떄 sequence 값을 하나 올려줌
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); //null이어도 감쌀 수 있도록 Optional이용
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))//파라미터로 넘어온 name이 Name과 같은지 확인
                .findAny(); // 찾으면 그냥 반환
    } //루프를 다 돌면서 Map에서 찾으면 바로 반환하고 끝까지 루프를 다 돌았는데 없으면 Optional에 null이 포함되서 반환됨.

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); //store에 있는 values가 멤버들이 쫙 반환됨.
    }

    public void clearStore(){
        store.clear();

    }
}
