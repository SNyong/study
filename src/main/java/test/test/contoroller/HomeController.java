package test.test.contoroller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //http://localhost:8080/ 치면 처음 들어와지는곳
    @GetMapping("/")
    public String home(){
    //템플릿에 home.html로 이동함
        return "home";
    }
}
