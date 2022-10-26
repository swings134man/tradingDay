package com.trading.day.qna.domain;


import com.trading.day.config.BaseTimeEntity;
import com.trading.day.item.domain.ItemBoard;
import com.trading.day.item.domain.ItemBoardDTO;
import lombok.*;
import org.springframework.data.domain.Page;

import java.io.Writer;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
/**
 * packageName :
 * fileName : QnaDTO
 * author : taeil
 * date :
 * description : 1:1 문의에 대한 DTO class
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 *               김태일                       최초생성
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class QnaDTO {
    private Long qnaId;
    private String title;
    private String writer;
    private String content;
    private String createdDate;
    private String modifiedDate;


    public Page<QnaDTO> toPageDTO(Page<Qna> entity) {
        Page<QnaDTO> qnaListPage = entity.map(m ->
                QnaDTO.builder()
                        .qnaId(m.getQnaId())
                        .title(m.getTitle())
                        .content(m.getContent())
                        .writer(m.getWriter())
                        .createdDate(m.getCreatedDate())
                        .modifiedDate(m.getModifiedDate())
                        .title(m.getTitle())
                        .build()
        );
        return qnaListPage;
    }

}
