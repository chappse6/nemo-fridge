package parse.squarerefri.domain.exception.cotroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class ErrorPageController {

    public static final String ERROR_MESSAGE = "javax.servlet.error.message";

    @RequestMapping("/error/403")
    public String errorPage403(HttpServletRequest request, HttpServletResponse
            response) {
        log.info("errorPage 403");
        return "error/403";
    }
    @RequestMapping("/error/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse
            response) {
        log.info("errorPage 404");
        return "error/404";
    }
    @RequestMapping("/error/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse
            response, Model model) {
        log.info("errorPage 500");
        model.addAttribute("errorMessage",request.getAttribute(ERROR_MESSAGE));
        return "error/500";
    }

}
