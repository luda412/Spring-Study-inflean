package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); //회원을 저장
    Optional<Member> findById(Long id); //id가 없을 경우 null을 반환하게 되는데 null을 그대로 반화하지 않고 Optional로 감싸서 반환하게 해줌.
    Optional<Member> findByName(String name);
    List<Member> findAll(); //지금까지 저당된 모든 회원 리스트를 다 반환
}
