package com.trading.day.config.jwtConfig;

import com.trading.day.member.repository.MemberJpaRepository;
import com.trading.day.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

/**
 * packageName : com.trading.day.config.jwtConfig
 * fileName : OAuth2DetailsService
 * author : taeil
 * date : 2023/01/04
 * description :
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 * 2023/01/04        taeil                   최초생성
 */
@Component
@Slf4j
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.debug("테스트@@@" + oAuth2User.getName());
        return oAuth2User;
    }
}