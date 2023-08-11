package uk.jinhy.sumsumzip.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import uk.jinhy.sumsumzip.util.JwtProvider;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final HttpSession httpSession;
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        var attributes = oAuth2User.getAttributes();
        var token = jwtProvider.createToken((String) attributes.get("email"));
        httpSession.setAttribute("token", token);
        response.sendRedirect("/");
    }
}
