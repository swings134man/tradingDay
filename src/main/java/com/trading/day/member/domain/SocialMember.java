package com.trading.day.member.domain;

import com.trading.day.config.BaseTimeEntity;
import lombok.*;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static java.lang.String.format;

/**
 * packageName : com.trading.day.config.jwtConfig
 * fileName : OAuthInfoDTO
 * author : taeil
 * date : 2023/01/03
 * description :
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 * 2023/01/03        taeil                   최초생성
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "SOCIAL_MEMBER")
public class SocialMember extends BaseTimeEntity {

    @Id
    private Long memberNo;
    private String email;




//    private Provider provider;              /*데이터 받아올때 사용할 provider 컬럼*/
//    private String provider;                /*소셜*/

    // provider들이 주는 정보 안에 oidc혹은 oauth 정보가 포함되어있음.
    // -> 가져온 정보를 클래스의 정보로 컨버팅해주는 메서드
//    public static enum Provider {
//        google {
//            // google에서 제공하는 정보
//            public SocialMember convert(OAuth2User user) {
//                return SocialMember.builder()
//                        .oauth2UserId(format("%s_%s", name(), user.getAttribute("sub"))) // -> id
//                        .provider(google)
//                        .email(user.getAttribute("email"))
//                        .name(user.getAttribute("name"))
//                        .build();
//            }
//        };
//        public abstract SocialMember convert(OAuth2User userInfo);
//    }




}