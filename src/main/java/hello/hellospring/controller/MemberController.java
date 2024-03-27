package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private  final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }
    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member(); //member 생성
        member.setName(form.getName());

        memberService.join(member); //join은 name이 아닌 member를 받음
        return "redirect:/"; // 회원가입이 끝나면 홈화면으로 보낸다.
    }
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers(); //모든 멤버 가져옴
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
