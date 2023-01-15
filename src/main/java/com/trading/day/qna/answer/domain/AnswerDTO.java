package com.trading.day.qna.answer.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * packageName : com.trading.day.qna.answer.domain
 * fileName : AnswerDTO
 * author : taeil
 * date : 2022/11/18
 * description :
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 * 2022/11/18        taeil                   최초생성
 */


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {

    private Long id;               /* 답변 Id */
    private String content;        /* 답변내용 */
    private final String writer = "tradingManager";  /* 작성자 : 고정으로 사용할것.*/
    private Long qnaId;             /* 문의번호 */

    private String createdDate; // 생성 시간
    private String modifiedDate;// 수정 시간


}

