package uk.jinhy.sumsumzip.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import uk.jinhy.sumsumzip.entity.User;
import uk.jinhy.sumsumzip.repository.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var oAuth2User = defaultOAuth2UserService.loadUser(userRequest);
        var registration = userRequest.getClientRegistration();
        var userNameAttributeName = registration.getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        var attributes = OAuthAttributes.of(
                userNameAttributeName,
                oAuth2User.getAttributes()
        );

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));    //세션에 사용자 정보 저장

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("No Role")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.updateUser(attributes.getName()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
