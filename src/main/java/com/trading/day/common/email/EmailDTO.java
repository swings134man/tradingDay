package com.trading.day.common.email;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {

    private String title;   // 제목
    private String content; // 내용
    private String targetMail; // 수신자 이메일.
    private Long boardId; // 게시판 ID
    private String boardIdNo; // 게시판 ID

}//class
