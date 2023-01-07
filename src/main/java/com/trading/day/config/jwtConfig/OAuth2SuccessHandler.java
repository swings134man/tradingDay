package com.trading.day.config.jwtConfig;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.day.jwtToken.domain.TokenDTO;
import com.trading.day.jwtToken.service.TokenService;
import com.trading.day.member.domain.Member;
import com.trading.day.member.domain.SocialMember;
import com.trading.day.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

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
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private TokenService tokenService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        System.out.println("response" + response);
        String email = principal.getAttribute("email");

        System.out.println("@@@" + principal.getAuthorities());

//        stringCollection
//                .stream()
//                .filter((s) -> s.startsWith("김"))
//                .forEach(System.out::println);

        //String test = principal.getAuthorities().toString();

        Member findMember = memberService.socialFindMember(email);
        if (ObjectUtils.isEmpty(findMember)) {

            // 기존 memberTable을 조회 -> 결과가 없다면 소셜 회원가입으로 리턴
//            response.setHeader("socialLogin", email);
//            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//            response.getOutputStream().write(objectMapper.writeValueAsBytes(email));

            // redirect:#?id="+userId 와 이게되네 미친 .....................................
            response.sendRedirect("http://localhost:3000/member/socialsignup?email="+email);

        } else {
             //조회된 결과가 있을경우, 가입된 정보를 기반으로 로그인시키자.
            //response.setHeader("auth_token", JWTUtil.makeAuthToknen());

            System.out.println("memberInfo : " + findMember.getMemberId());
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

            // 리다이렉트
            response.sendRedirect("http://localhost:3000/?jwt_token="+auth_token);
            //request.getRequestDispatcher("http://localhost:3000/").forward(request, response);
        }



//        if(principal instanceof OidcUser) {
//        //google
//        SocialMember socialMember = SocialMember.Provider.google.convert((OidcUser) principal);
//
//
////            Member member = memberService.socialLoginSave(socialMember);
////            SecurityContextHolder.getContext().setAuthentication(
////                    new UsernamePasswordAuthenticationToken(member, "", member.getAuthorities())
////            );
//    } else if(principal instanceof OAuth2User) {
//        //naver
//
//    }

    }
}