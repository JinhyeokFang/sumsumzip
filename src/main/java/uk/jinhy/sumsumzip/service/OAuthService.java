package uk.jinhy.sumsumzip.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import uk.jinhy.sumsumzip.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
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

        var user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.updateUser(attributes.getName()))
                .orElse(attributes.toEntity());
        userRepository.save(user);

        return oAuth2User;
    }
}
