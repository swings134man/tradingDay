package com.trading.day.common.file;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.trading.day.item.domain.ItemBoard;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageFileDTO {

    private Long id;

    private String orgNm;   // 원본 파일명
    private String saveNm;  // 저장 파일명 - uuid
    private String savePath;// 저장 경로
    private Long boardId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ImageFileReqeust {
        private List<Long> idList;
    }// Request
}//class
