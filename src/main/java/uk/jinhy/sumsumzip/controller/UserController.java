package uk.jinhy.sumsumzip.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.jinhy.sumsumzip.service.SessionUser;
import uk.jinhy.sumsumzip.service.UserAttribute;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @UserAttribute SessionUser user) {
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }
}
