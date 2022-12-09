//package com.trading.day.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * packageName : com.trading.day.common.file
// * fileName : WebConfig
// * author : taeil
// * date : 2022/12/09
// * description :
// * =======================================================
// * DATE          AUTHOR                      NOTE
// * -------------------------------------------------------
// * 2022/12/09        taeil                   최초생성
// */
//@Configuration
//@EnableWebMvc
//public class WebConfig  implements WebMvcConfigurer{
//        @Override
//        public void addCorsMappings(CorsRegistry registry) {
//            registry.addMapping("/**")
//                    .allowedOrigins("http://localhost:3000")
//                    .allowedMethods("*")
//                    .allowCredentials(false)
//                    .maxAge(3000);
//        }
//
//
//
//}