package com.trading.day.qna.domain;


import com.trading.day.config.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Slf4j
@Table(name = "Qna")
public class Qna extends BaseTimeEntity {

    // --> pk지정
    // id 값을 null로 하면 DB가 AUTO_INCREMENT
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "qna_id")
    private Long qnaId;

    private String memberId;
    private String title;
    private String writer;
    private String content;
    private String createdDate;
    private String modifiedDate;


    // fetchType.LAZY --> jpa 사용 전략 --> 즉시 로딩(Eager Loading)과 지연 로딩(Lazy Loading)
    // 특정 엔티티를 조회할 때 연관된 모든 엔티티를 같이 로딩하는 것을 즉시 로딩(Eager Loading)이라고 합니다.
    // 즉시 로딩은 연관된 엔티티를 모두 가져온다는 장점이 있지만, 실무에서 엔티티간의 관계가 복잡해질수록 조인으로 인한 성능 저하를 피할 수 없음.
    // JPA에서 연관관계의 데이터를 어떻게 가져올 것인가를 fetch(패치)라고 하는데, 연관관계의 어노테이션 속성으로 'fetch'모드를 지정
    // 지연 로딩(Lazy Loading)이란, 가능한 객체의 초기화를 지연시키는데 사용
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//     private Member MemberId;

}
