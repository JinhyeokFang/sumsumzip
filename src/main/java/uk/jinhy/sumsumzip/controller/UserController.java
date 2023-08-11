package uk.jinhy.sumsumzip.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.jinhy.sumsumzip.controller.user.TokenResponseDTO;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/token")
    public TokenResponseDTO token() {
        var token = (String) httpSession.getAttribute("token");
        return new TokenResponseDTO(token != null, token);
    }
}
