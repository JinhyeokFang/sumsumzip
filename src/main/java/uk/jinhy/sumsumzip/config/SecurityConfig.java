package uk.jinhy.sumsumzip.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import uk.jinhy.sumsumzip.service.OAuthService;
import uk.jinhy.sumsumzip.controller.OAuthSuccessHandler;
import uk.jinhy.sumsumzip.util.JwtProvider;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    @Value("${spring.security.cors.client-origin}")
    private String clientOrigin;

    private final OAuthService oAuthService;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(
                        cors -> cors.configurationSource(
                            corsConfigurationSource()
                        )
                )
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
                                .defaultSuccessUrl(clientOrigin)
                );

        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(clientOrigin));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
