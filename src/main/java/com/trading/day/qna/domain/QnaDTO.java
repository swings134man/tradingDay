package com.trading.day.qna.domain;


import lombok.*;

import java.io.Writer;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class QnaDTO {
    private Long qnaId;
    private String title;
    private String Writer;
    private String contetnt;
    private String createdDate;
    private String modifiedDate;
    private String memberNo;
}
