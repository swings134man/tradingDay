package com.trading.day.item.domain;

import com.trading.day.item.reply.domain.ItemBoardReply;
import com.trading.day.item.reply.domain.ItemBoardReplyDTO;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;


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

    private String createdDate; // 생성 시간
    private String modifiedDate;// 수정 시간

    private String keyWord;     // 검색어
    private String keyType;     // 검색 조건

    private List<ItemBoardReply> replys;
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

        // 검색 Reqeust
        public ItemBoardDTO findTitleOrWriter(String keyType, String keyWord) {
            ItemBoardDTO dto = ItemBoardDTO.builder()
                    .keyType(keyType)
                    .keyWord(keyWord)
                    .build();
            return  dto;
        }

    // Request 전용 inner class (save, update)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ItemRequest {
        private Long id;            // 게시판 ID
        private String title;       // 제목
        private String writer;      // 작성자
        private String content;     // 내용
        private String type;        // 물건 타입 -> 신품/중고
        private Long view;          // 조회수


        private String keyWord;     // 검색어
        private String keyType;     // 검색 조건
    }

}//class
