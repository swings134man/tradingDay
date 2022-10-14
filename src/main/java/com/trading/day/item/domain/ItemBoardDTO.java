package com.trading.day.item.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trading.day.config.BaseTimeEntity;
import lombok.*;
import org.springframework.data.domain.Page;

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

        // paging 변환
        public Page<ItemBoardDTO> toPageDTO(Page<ItemBoard> entity) {
            Page<ItemBoardDTO> boardListPage = entity.map(m ->
                    ItemBoardDTO.builder()
                            .id(m.getId())
                            .title(m.getTitle())
                            .content(m.getContent())
                            .writer(m.getWriter())
                            .type(m.getType())
                            .view(m.getView())
                            .createdDate(m.getCreatedDate())
                            .modifiedDate(m.getModifiedDate())
                            .build()
                    );
            return boardListPage;
        }
}//class
