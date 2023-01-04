package com.trading.day.common.note;

import com.trading.day.config.BaseTimeEntity;
import com.trading.day.config.BooleanToConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/************
 * @info : 쪽지 Entity
 * @name : Note
 * @date : 2023/01/04 1:40 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "NOTE")
public class Note extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "note_no")
    private Long noteNo;

    //TODO : FK를 해야할지 고민?
    private String receiveMemberId; /*받는 사람 ID*/
    private String senderMemberId;  /*보내는 사람 ID*/

    private String title;
    @Column(length = 700)
    private String content;

    @Convert(converter = BooleanToConverter.class)
    private boolean status;          /*열람 여부 - Y/N*/
}
