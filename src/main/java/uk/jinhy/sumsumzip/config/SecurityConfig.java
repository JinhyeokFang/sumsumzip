package uk.jinhy.sumsumzip.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import uk.jinhy.sumsumzip.service.OAuthService;
import uk.jinhy.sumsumzip.controller.OAuthSuccessHandler;
import uk.jinhy.sumsumzip.util.JwtProvider;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final OAuthService oAuthService;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(CorsConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .headers(httpSecurityHeadersConfigurer ->
                        httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .logout(logoutConfigurer -> logoutConfigurer.logoutSuccessUrl("/"))
                .addFilterBefore(new AccessTokenFilter(jwtProvider), BasicAuthenticationFilter.class)
                .oauth2Login(loginConfigurer ->
                        loginConfigurer
                                .userInfoEndpoint(
                                        userInfoEndpointConfig -> userInfoEndpointConfig.userService(oAuthService)
                                )
                                .successHandler(oAuthSuccessHandler)
                );

        return httpSecurity.build();
    }
}
