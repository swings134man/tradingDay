//package com.trading.day.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//
///**
// * packageName : com.trading.day.config
// * fileName : SecurityConfig
// * author : taeil
// * date : 2022/12/02
// * description :
// * =======================================================
// * DATE          AUTHOR                      NOTE
// * -------------------------------------------------------
// * 2022/12/02        taeil                   최초생성
// */
//
//@EnableWebSecurity(debug = true)
////@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//@CrossOrigin(origins = "http://localhost:3000")
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/api/v1/auth/**",
//                        "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/index.html", "/swagger-ui.html","/webjars/**", "/swagger/**",
//                        "/h2-console/**",
//                        "/favicon.ico"
//                ).permitAll();
//
////                .and()
////
////                .formLogin();
////                            .loginPage("/member/v1/signin");
//
////        http
////                .csrf().disable()
////                .cors();
//    }
//
//    // cors 설정
////    @Bean
////    public CorsConfigurationSource corsConfigurationSource() {
////        CorsConfiguration configuration = new CorsConfiguration();
////
//////        configuration.addAllowedOrigin("*");
////        configuration.addAllowedOriginPattern("*");
////        configuration.addAllowedHeader("*");
////        configuration.addAllowedMethod("*");
////        configuration.setAllowCredentials(true);
////
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", configuration);
////        return source;
////    }
//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    UserDetailsService userService(){
//        final PasswordEncoder pw = passwordEncoder();
//        UserDetails user2 = User.builder()
//                .username("user2")
//                .password(pw.encode("1234"))
//                .roles("USER").build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(pw.encode("1234"))
//                .roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(user2, admin);
//    }
//
//
//}
