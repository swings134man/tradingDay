package com.trading.day.item.apply.domain;


import lombok.*;

/************
 * @info : 프로젝트 지원서 DTO
 * @name : applyDTO
 * @date : 2022/11/30 2:18 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyDTO {

    private Long applyId;            /* 지원서ID */
    private String title;            /* 지원서 제목 */
    private String content;          /* 내용 */
    private String type;             /* 지원분야 */
    private String level;            /* 스킬 레벨 */
    private String writer;           /* 지원자 Member_ID */
    private String writerEmail;      /* 지원자 Email */

    // Board
    private Long itemBoard;          /* 게시물 ID */

    // Member
    private Long member;             /* 회원 ID */

}
