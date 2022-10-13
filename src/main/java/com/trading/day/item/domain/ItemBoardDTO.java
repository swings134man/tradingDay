package com.trading.day.item.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trading.day.config.BaseTimeEntity;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemBoardDTO  {

    private Long id;            // 게시판 ID
    private String title;       // 제목
    private String writer;      // 작성자
    private String content;     // 내용
    private String type;        // 물건 타입 -> 신품/중고
    private Long view;          // 조회수

    private String createdDate;
    private String modifiedDate;
}
