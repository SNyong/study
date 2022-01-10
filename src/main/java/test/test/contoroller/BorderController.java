package test.test.contoroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import test.test.domain.Border;
import test.test.service.BorderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class BorderController {

    private BorderService borderService;

    @Autowired
    public BorderController(BorderService borderService) {
        this.borderService = borderService;
    }
    //전체 게시글
    @GetMapping("/border")
    public String borderList(Model model){

        List<Border> border = borderService.ListBorder();
        model.addAttribute("border", border);
        return "border/borderList";
    }

    //게시판 검색
    @RequestMapping("/border")
    public String SearchBorder(@RequestParam String title) {

        List<Border> border = borderService.ListBorder(title);

        Map<String,Object> map= new HashMap<>();

        ModelAndView mav = new ModelAndView();

        map.put("border", border);
        map.put("title", title);

        mav.addObject("border", border);
        mav.setViewName("border/borderList");
        return "mav";
    }


    //게시판 폼으로
    @GetMapping("border/write")
    public String writeForm(){
        return "border/borderForm";
    }
    
    //글쓰기
    @PostMapping("/border/write")
    public String save(BorderForm form){
        Border border = new Border();
        border.setNum(form.getNum());
        border.setTitle(form.getTitle());
        border.setContents(form.getContents());

        borderService.write(border);

        System.out.println("글쓰기 번호 = " + border.getNum() + ", 제목 = " + border.getTitle());

        return "redirect:/";
    }

    @PostMapping("/border/read")
    public String read(Border border){

        Optional<Border> read = borderService.read(border.getNum());

        border.setContents(border.getContents());

        Map<String,Object> map= new HashMap<>();

        ModelAndView mav = new ModelAndView();

        map.put("read", read);
        map.put("border", border);

        mav.addObject("border", border);
        mav.setViewName("border/borderList");


        return "border/read";
    }

}
