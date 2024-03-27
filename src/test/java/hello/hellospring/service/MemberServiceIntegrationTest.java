package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    //MemberService memberService = new MemberService();
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository(); new 연산자를 사용했기 떄문에 아예 다른 객체가 생성된 것 그렇기 때문에 구현한 class와는 다른 레포지토리와 비교를 하고 있다.
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given
        Member member = new Member(); // 멤버 생성
        member.setName("luda"); //멤버의 Name 주입

        //when
        Long saveId = memberService.join(member); //join 메서드를 실행하면 결과같으로 id를 반환함. id는 sequence에 의해 증가된 회원을 식별하기 위한 시스템의 아이디

        //then
        Member findMember = memberService.findOne(saveId).get(); // 위에서 저장한 Id를 통해 member를 찾고 findMember에 담는다.
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName()); //찾은 findMember의 name과 위에서 setName한 name을 get하여 같은지 비교한다.
    }
    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("fnek");

        Member member2 = new Member();
        member2.setName("fnek");
        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));//예외가 터져야함.
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //then
    }

}