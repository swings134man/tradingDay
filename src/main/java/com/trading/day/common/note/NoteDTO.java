package com.trading.day.common.note;

import com.trading.day.config.BooleanToConverter;
import lombok.*;

import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/************
 * @info : 쪽지 기능 DTO
 * @name : NoteDTO
 * @date : 2023/01/04 2:00 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDTO {

    private Long NoteNo;

    private String receiveMemberId; /*받는 사람 ID*/
    private String senderMemberId;  /*보내는 사람 ID*/

    private String title;
    private String content;

    private String status;          /*열람 여부 - Y/N*/

}
