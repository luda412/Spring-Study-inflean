package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class MemberService {

    //private final MemberRepository memberRepository = new MemoryMemberRepository(); 서비스가 있기 위해서는 저장소가 일단 필요하기 때문에 repository를 생성
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    } //외부에서 넣어줄 수 있도록 바꾼다.

    /**
     * 회원 가입 | 같은 이름이 있는 중복 회원은 안됨.
     * @param member
     */
    public Long join(Member member){

        vaildateDuplicateMember(member); //중복회원 검증
        memberRepository.save(member); //파라미터로 전달 받은 member를 레포지토리에 저장
        return member.getId(); // member의 id 반환
    }

    private void vaildateDuplicateMember(Member member){
        memberRepository.findByName(member.getName())
                .ifPresent(m ->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });//
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll(); //반환하는 값이 List이기 때문에 findAll 수행 후 그냥 반환해주면 됨.
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
