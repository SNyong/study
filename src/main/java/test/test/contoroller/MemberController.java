package test.test.contoroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import test.test.domain.Member;
import test.test.service.MemberService;

import java.util.List;
import java.util.Optional;

@Controller
public class MemberController {


    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //GetMapping은 해당 경로 파일로 이동할때 사용
    //조회할때 주로 사용
    @GetMapping("/members/join")
    public String joinForm() {
        return "members/joinMemberForm";
    }

    //PotsMapping은 값을 받는곳
    //입력할때 주로사용
    @PostMapping("/member/join")
    public String join(MemberForm form) {
        Member member = new Member();
        member.setId(form.getId());
        member.setPw(form.getPw());

        //입력된 아이디값과 비밀번호값을 join함
        memberService.join(member);

        System.out.println("회원가입 num = " + member.getNum() + ", id = " + member.getId() + ", pw = " + member.getPw());

        //리턴은 위에 저장 및 실행후 갈 페이지 지정
        // redirect:/ -> 홈 화면으로 보내기
        return "redirect:/";
    }

    @GetMapping("/members")
        public String list(Model model) {

        List<Member> members = memberService.findMembers();
        
        //AttributeName 이 외부 html에서 불러오는 그룹
        model.addAttribute("members", members);
        for(int i = 0; i < members.size(); i++) {
            System.out.println(members.get(i));
        }

        return "members/memberList";
    }


}
