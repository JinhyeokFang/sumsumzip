package uk.jinhy.sumsumzip.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import uk.jinhy.sumsumzip.service.UserAuthService;

@AllArgsConstructor
@Configuration
public class SecurityConfig {
    private final UserAuthService userAuthService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(CorsConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .headers(httpSecurityHeadersConfigurer ->
                        httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/")).permitAll()
                                .anyRequest().permitAll()
                )
                .logout(logoutConfigurer -> logoutConfigurer.logoutSuccessUrl("/"))
                .oauth2Login(loginConfigurer -> loginConfigurer.userInfoEndpoint(
                        userInfoEndpointConfig -> userInfoEndpointConfig.userService(userAuthService)
                ));

        return httpSecurity.build();
    }
}
