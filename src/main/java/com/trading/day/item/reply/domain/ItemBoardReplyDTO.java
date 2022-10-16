package com.trading.day.item.reply.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ItemBoardReplyDTO {

    private Long id;                // 댓글 ID
    private String content;         // 내용
    private String writer;          // 작성자
    private String createdDate;     // 생성 시간
    private String modifiedDate;    // 수정 시간

    private Long boardId; // 프론트에서 받아올 게시판 ID

    public ItemBoardReplyDTO(ItemBoardReply reply) {
        id = reply.getId();
        content = reply.getContent();
        writer = reply.getWriter();
        createdDate = reply.getCreatedDate();
        modifiedDate = reply.getModifiedDate();
    }
}
