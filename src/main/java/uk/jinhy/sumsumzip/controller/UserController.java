package uk.jinhy.sumsumzip.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.jinhy.sumsumzip.controller.user.TokenResponseDTO;
import uk.jinhy.sumsumzip.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final HttpSession httpSession;
    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/token")
    public TokenResponseDTO token() {
        var refreshToken = (String) httpSession.getAttribute("refresh-token");
        if (refreshToken == null) {
            return new TokenResponseDTO(
                    false,
                    null
            );
        }
        try {
            var accessToken = userService.createAccessToken(refreshToken);
            return new TokenResponseDTO(true, accessToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
