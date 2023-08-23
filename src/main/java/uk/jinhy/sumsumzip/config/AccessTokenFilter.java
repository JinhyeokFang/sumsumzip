package uk.jinhy.sumsumzip.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import uk.jinhy.sumsumzip.util.JwtProvider;
import uk.jinhy.sumsumzip.util.JwtType;

import java.io.IOException;

@RequiredArgsConstructor
public class AccessTokenFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        var bearer = request.getHeader("Authorization");
        if (bearer != null) {
            parseToken(bearer);
        }
        filterChain.doFilter(request, response);
    }

    private void parseToken(String bearer) {
        var token = bearer.substring(7);
        try {
            var claims = jwtProvider.parseJwtToken(token);
            if (claims.getSubject().equals(
                    JwtType.REFRESH_TOKEN.toString()
            )) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "잘못된 토큰입니다."
                );
            }

            var email = claims.get("email");
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(email, ""));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "잘못된 토큰입니다."
            );
        }
    }
}