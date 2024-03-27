package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    //MemberService memberService = new MemberService();
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository(); new 연산자를 사용했기 떄문에 아예 다른 객체가 생성된 것 그렇기 때문에 구현한 class와는 다른 레포지토리와 비교를 하고 있다.
    MemberService memberService;
    MemoryMemberRepository memberRepository;
    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }//테스트를 실행할 때 마다 각각 생성을 해준다, 테스트를 싱핼하기 전에 memberRepository를 만들고 멤버 서비스에 넣어준다.


    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }//각 메서드가 실행되고 나면 DB 값을 다 날려준다.

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

        /*
        try {
            memberService.join(member2); //여기서 member2에 대해 join을 진행할 때 같은 name인 "fnek"가 들어가기 때문에 Exception이 발생될 것으로 예상
            fail(); //예외가 발생하지 않고 넘어갈 경우도 존재하기 때문에 fail을 터트림.
        } catch (IllegalStateException e){ //예외 발생시 여기서 잡음
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
         */


        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}