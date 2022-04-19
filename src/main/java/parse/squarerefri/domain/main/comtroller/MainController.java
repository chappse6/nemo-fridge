package parse.squarerefri.domain.main.comtroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/read-more")
    public String readMore() {
        return "read_more";
    }

    @GetMapping("/error/denied")
    public String errorDenied() {
        return "error/denied";
    }
}
