package com.trading.day.config.jwtConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * packageName : com.trading.day.config.jwtConfig
 * fileName : FailureHandler
 * author : taeil
 * date : 2023/01/27
 * description :
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 * 2023/01/27        taeil                   최초생성
 */
@Component
@Slf4j
public class AuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException
    {
      log.debug("----------------------------- 나는 failHandler -----------------------------");
        response.sendRedirect("http://localhost:3000/member/signin?exception="+exception.getMessage());
    }
}