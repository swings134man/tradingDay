package com.trading.day.item.apply.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.trading.day.config.BaseTimeEntity;
import com.trading.day.item.domain.ItemBoard;
import com.trading.day.member.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "ITEM_APPLY")
public class Apply extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Long applyId;               /* 지원서ID */
    private String title;               /* 지원서 제목 */
    private String type;                /* 지원분야 */ // --> 지원분야가 딱히 없거나 애매하다면 ? : anonymous 사용.
    private String level;               /* 스킬 레벨 */ // --> beginner ,junior, senior, master

    // TODO : 게시글 작성자가 자신에게 온것을 리스트로 확인 할것인지? : 아니면 다른방법 사용할것인지 논의 필요.
    private String writer;              /* 작성자(PK가 아님. 지원자 ID) */
    private String writerEmail;         /* 답변을 받을 email */ // 답변을 받을 이메일 기재.

    @Column(columnDefinition = "TEXT")
    private String content;             /* 지원내용 */

    // Board
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonBackReference
    private ItemBoard itemBoard; // 지원한 게시물 ID

    // Member
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    @JsonBackReference
    private Member member; // 게시글 작성자 ID

}
