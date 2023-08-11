package uk.jinhy.sumsumzip.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import uk.jinhy.sumsumzip.util.JwtProvider;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        var bearer = request.getHeader("Authorization");
        if (bearer != null) {
            var token = bearer.substring(7);
            var email = jwtProvider.parseJwtToken(token).getSubject();
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(email, ""));
        }
        filterChain.doFilter(request, response);
    }
}