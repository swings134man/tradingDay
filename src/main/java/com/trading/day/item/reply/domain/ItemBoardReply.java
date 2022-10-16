package com.trading.day.item.reply.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.trading.day.config.BaseTimeEntity;
import com.trading.day.item.domain.ItemBoard;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "ITEM_BOARD_REPLY")
public class ItemBoardReply extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;        // 댓글 ID

    private String content; // 내용
    private String writer;  // 작성자

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "board_id_board_id")
    private ItemBoard boardId; // 게시물 ID -> 어디에 연관된 댓글인지 구분


}
