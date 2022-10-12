package com.trading.day.member.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    private Long id;
    private String name;

    
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
}
