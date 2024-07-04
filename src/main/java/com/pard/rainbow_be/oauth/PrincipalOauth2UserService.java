package com.pard.rainbow_be.oauth;

import com.pard.rainbow_be.oauth.dto.OAuthAttributes;
import com.pard.rainbow_be.oauth.dto.SessionUser;
import com.pard.rainbow_be.user.entity.User;
import com.pard.rainbow_be.user.repo.UserRepo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepo userRepo;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest)
            throws OAuth2AuthenticationException {
        log.info("📍 google userRequest: " + oAuth2UserRequest);

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        log.info("📍 oauth: " + oAuth2User.getAttributes());

        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        OAuthAttributes attributes = OAuthAttributes.of(userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        log.info("📍 session user: " + new SessionUser(user));

        return oAuth2User;
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepo.findByEmail(attributes.getEmail())
                .map(entity -> {
                    entity.update(attributes.getName());
                    return entity;
                })
                .orElse(attributes.toEntity());

        return userRepo.save(user);
    }
}
