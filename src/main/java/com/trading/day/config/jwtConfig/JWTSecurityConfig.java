package com.trading.day.config.jwtConfig;

import com.trading.day.jwtToken.repository.TokenManageJpaRepository;
import com.trading.day.jwtToken.service.TokenService;
import com.trading.day.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName : com.trading.day.config.jwtConfig
 * fileName : JWTSecurityConfig
 * author : taeil
 * date : 2022/12/15
 * description :
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 * 2022/12/15        taeil                   ????????????
 */
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class JWTSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberService memberService;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;
    @Autowired
    private TokenManageJpaRepository tokenManageJpaRepository;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    RoleHierarchy roleHierarchy () {
        // ?????? ?????? ??????
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE_USER");
        return roleHierarchy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTLoginFilter loginFilter = new JWTLoginFilter(authenticationManager(), memberService, tokenService);
        JWTCheckFilter checkFilter = new JWTCheckFilter(authenticationManager(), memberService, tokenManageJpaRepository);

        // ????????? ???????????????,
        //csrf??? ???????????????,csrf????????? ???????????? ????????? ???????????? ???????????? ????????? ??????????????? ????????? ???
        http
                .authorizeRequests()
                .antMatchers("/member/v1/**", "/logout").permitAll()
                .antMatchers("/item/v1/**", "/apply/v1/**", "/login/oauth2/**").permitAll()
                .antMatchers("/qna/v1/**", "/answer/v1/**").hasAnyAuthority("ROLE_USER")
                .anyRequest().authenticated()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(checkFilter, BasicAuthenticationFilter.class)
                .logout(logout ->
                        logout.deleteCookies("refresh_token"));

        // ----------------- ?????? ????????? config -----------------------------------
        http
                .oauth2Login(oauth2 ->
                        oauth2.successHandler(oAuth2SuccessHandler) );


    }
    //cors ??????
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                // -- Static resources
                "/css/**", "/images/**", "/js/**"
                // -- Swagger UI v2
                , "/v2/api-docs", "/swagger-resources/**"
                , "/swagger-ui.html", "/webjars/**", "/swagger/**"
        );
    }



}