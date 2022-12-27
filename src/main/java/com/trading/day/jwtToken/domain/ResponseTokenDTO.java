package com.trading.day.jwtToken.domain;

import lombok.*;

/**
 * packageName : com.trading.day.jwtToken.domain
 * fileName : ResponseTokenDTO
 * author : taeil
 * date : 2022/12/20
 * description :
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 * 2022/12/20        taeil                   최초생성
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTokenDTO {

    private String auth_token;
    private String refresh_token;

}