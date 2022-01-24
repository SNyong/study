package test.test.contoroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import test.test.domain.Border;
import test.test.repository.BorderMapper;

import java.util.List;

@Controller
public class MyBorderController {

    @Autowired
    private BorderMapper borderMapper;

    public MyBorderController(BorderMapper borderMapper) {
        this.borderMapper = borderMapper;
    }

    @PostMapping("/border/write")
    public String save(Border border){
         borderMapper.Write(border);
        return "redirect:/";
    }

//    @GetMapping("/border")
    public String borderList(Model model){
        List<Border> border = borderMapper.borderList();
        model.addAttribute("border", border);
        return "border/borderList";
    }
}
