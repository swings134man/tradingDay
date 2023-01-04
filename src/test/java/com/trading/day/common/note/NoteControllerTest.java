//package com.trading.day.common.note;
//
//import com.trading.day.jwtTest.TokenBox;
//import com.trading.day.jwtTest.WebIntegrationTest;
//import com.trading.day.member.domain.Member;
//import com.trading.day.member.domain.MemberDTO;
//import lombok.extern.slf4j.Slf4j;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.*;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.web.client.RestTemplate;
//
//
//import java.net.URISyntaxException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///************
// * @info : 쪽지 기능 Controller Test
// * @name : NoteControllerTest
// * @date : 2023/01/04 5:44 PM
// * @author : SeokJun Kang(swings134@gmail.com)
// * @version : 1.0.0
// * @Description :
// ************/
//@TestPropertySource(properties = "spring.jpa.properties.hibernate.default_batch_fetch_size=1000")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Slf4j
//class NoteControllerTest extends WebIntegrationTest {
//
////    private static int port = 8080;
//
//
//    @DisplayName("Token 가져오기")
//    private TokenBox getToken() throws URISyntaxException {
//        RestTemplate rt = new RestTemplate();
//
//        //headers
//        HttpHeaders header = new HttpHeaders();
//        header.setContentType(MediaType.APPLICATION_JSON);
//        header.add("Accept", MediaType.APPLICATION_JSON_VALUE);
//        header.add("User-Agent", "");
//
//        // body
//        HttpEntity<MemberDTO> body = new HttpEntity<>(
//                MemberDTO.builder().memberId("admin").pwd("djemals1!").build(),
//                header
//        );
////        String url = "http://127.0.0.1:" + port + "/member/v1/login";
//        // connection
//        ResponseEntity<Member> res = rt.exchange(uri("/member/v1/login"), HttpMethod.POST, body, Member.class);
//
//        return TokenBox.builder().authToken(res.getHeaders().get("auth_token").get(0))
//                .refreshToken(res.getHeaders().get("refresh_token").get(0))
//                .build();
//    }// 로그인 토큰 get
//
//    @DisplayName("1. 쪽지 발송")
//    @Test
//    void sendNote() throws URISyntaxException {
//        // 1. 로그인 (토큰)
//        TokenBox tokenBox = getToken();
//        log.debug("Token 값 : " + tokenBox.getAuthToken());
//        String tokenHeader = "Bearer " + tokenBox.getAuthToken();
//
//        // 2. Rest, Header, Body 셋팅
////        String url = "http://127.0.0.1:" + port + "/note/v1/auth/sendnote";
//
//        RestTemplate rt = new RestTemplate();
//        // header
//        HttpHeaders header = new HttpHeaders();
//        header.add(HttpHeaders.AUTHORIZATION, tokenHeader);
//
//        //Body
//        HttpEntity<NoteDTO> body = new HttpEntity<>(
//                NoteDTO.builder().title("Test").content("Test Con").receiveMemberId("manager").senderMemberId("admin").build()
//                , header
//        );
//
//        // Connect
//        ResponseEntity<NoteDTO> res = rt.exchange(uri("/note/v1/auth/sendnote"), HttpMethod.POST, body, NoteDTO.class);
//
//        Assertions.assertThat("admin").isEqualTo(res.getBody().getSenderMemberId());
//    }
//
//
//
//
//}