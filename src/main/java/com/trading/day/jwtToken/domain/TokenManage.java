package com.trading.day.jwtToken.domain;

import com.trading.day.config.BaseTimeEntity;
import com.trading.day.member.domain.Member;
import lombok.*;


import javax.persistence.*;

/**
 * packageName : com.trading.day.member.domain
 * fileName : TokenManage
 * author : taeil
 * date : 2022/12/18
 * description :
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 * 2022/12/18        taeil                   최초생성
 */
@Entity
@Getter
@Setter
@Table(name = "TOKEN_MANAGE")
public class TokenManage extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long tokenId;

    private String userName;
    private String refreshToken;
    private Long memberNo;

}