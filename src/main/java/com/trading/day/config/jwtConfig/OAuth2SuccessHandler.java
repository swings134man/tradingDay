package com.trading.day.config.jwtConfig;

import com.trading.day.jwtToken.domain.TokenDTO;
import com.trading.day.jwtToken.service.TokenService;
import com.trading.day.member.domain.Member;
import com.trading.day.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * packageName : com.trading.day.config.jwtConfig
 * fileName : OAuth2SuccessHandler
 * author : taeil
 * date : 2023/01/03
 * description :
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 * 2023/01/03        taeil                   최초생성
 */
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User principal = (OAuth2User) authentication.getPrincipal();

        String email = principal.getAttribute("email");
        Member findMember = memberService.socialFindMember(email);

        if (ObjectUtils.isEmpty(findMember)) {
            response.sendRedirect("http://localhost:3000/member/socialsignup?email="+email);
        } else {
             //조회된 결과가 있을경우, 가입된 정보를 기반으로 로그인시키자.

            String auth_token = JWTUtil.makeSocialAuthToken(findMember.getMemberId());
            String refresh_token = JWTUtil.makeSocialRefreshToken(findMember.getMemberId());

            Cookie cookie = new Cookie("refresh_token", refresh_token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            response.addCookie(cookie);

            TokenDTO tokenDTO = TokenDTO.builder()
                    .userName(findMember.getMemberId())
                    .refresh_token(refresh_token.substring("Bearer ".length()))
                    .build();
            tokenService.saveRefreshToken(tokenDTO);

            response.sendRedirect("http://localhost:3000/?jwt_token="+auth_token);
        }
    }
}