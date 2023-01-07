package com.trading.day.config.jwtConfig;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.Instant;
import java.util.UUID;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;

/**
 * packageName : com.trading.day.config.jwtConfig
 * fileName : JWTUtil
 * author : taeil
 * date : 2022/12/14
 * description :
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 * 2022/12/14        taeil                   최초생성
 */
@Slf4j
public class JWTUtil {

    // secretKey는 랜덤한 값으로 알수없게 지정
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(String.valueOf(UUID.randomUUID()));
    //인증토큰의 유효시간은 30분
    private static final long AUTH_TIME = 30 * 60;
    //testOnly == 인증토큰의 시간을 2초로
//    private static final long AUTH_TIME = 2;

    //리프레시 토큰은 일주일
    private static final long REFRESH_TIME = 60 * 60 * 24 * 7;
//    private static final long REFRESH_TIME = 4;
    public static String makeAuthToknen(UserDetails details) {
        return JWT.create().withSubject(details.getUsername())
                .withClaim("exp", Instant.now().getEpochSecond() + AUTH_TIME)
                .withClaim("memberId", details.getUsername())
                .sign(ALGORITHM);
    }

    public static String makeRefreshToken(UserDetails details) {
        return JWT.create().withSubject(details.getUsername())
                .withClaim("exp", Instant.now().getEpochSecond() + REFRESH_TIME)
                .withClaim("memberId", details.getUsername())
                .sign(ALGORITHM);
    }

    public static String makeSocialAuthToken(String memberId) {
        return JWT.create().withSubject(memberId)
                .withClaim("exp", Instant.now().getEpochSecond() + REFRESH_TIME)
                .withClaim("memberId", memberId)
                .withClaim("userRole", "ROLE_USER")
                .sign(ALGORITHM);
    }

    public static String makeSocialRefreshToken(String memberId) {
        return JWT.create().withSubject(memberId)
                .withClaim("exp", Instant.now().getEpochSecond() + REFRESH_TIME)
                .withClaim("memberId", memberId)
                .withClaim("userRole", "ROLE_USER")
                .sign(ALGORITHM);
    }




    // 토큰의 유효성을 검증함
    public static VerifyResult verify(String token) {
        try {
            DecodedJWT verify =  JWT.require(ALGORITHM).build().verify(token);
            return VerifyResult.builder()
                    .success(true)
                    .memberId(verify.getSubject())
                    .build();

        } catch (Exception e ) {
            DecodedJWT decoded = JWT.decode(token);
            return VerifyResult.builder()
                    .success(false)
                    .memberId(decoded.getSubject())
                    .build();
        }

    }


}