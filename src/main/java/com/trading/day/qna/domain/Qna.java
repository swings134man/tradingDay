package com.trading.day.qna.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trading.day.config.BaseTimeEntity;
import com.trading.day.member.domain.Member;
import com.trading.day.qna.answer.domain.Answer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName :
 * fileName : Qna
 * author : taeil
 * date :
 * description : 1:1 문의에 대한 Entity class
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 *               김태일                       최초생성
 */
@Entity
@Getter
@Setter
@Slf4j
@Table(name = "Qna")
public class Qna extends BaseTimeEntity {

    // --> pk지정
    // id 값을 null로 하면 DB가 AUTO_INCREMENT
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private Long qnaId;

    // fetchType.LAZY --> jpa 사용 전략 --> 즉시 로딩(Eager Loading)과 지연 로딩(Lazy Loading)
    // 특정 엔티티를 조회할 때 연관된 모든 엔티티를 같이 로딩하는 것을 즉시 로딩(Eager Loading)이라고 합니다.
    // 즉시 로딩은 연관된 엔티티를 모두 가져온다는 장점이 있지만, 실무에서 엔티티간의 관계가 복잡해질수록 조인으로 인한 성능 저하를 피할 수 없음.
    // JPA에서 연관관계의 데이터를 어떻게 가져올 것인가를 fetch(패치)라고 하는데, 연관관계의 어노테이션 속성으로 'fetch'모드를 지정
    // 지연 로딩(Lazy Loading)이란, 가능한 객체의 초기화를 지연시키는데 사용
    @JsonIgnore // --> Json으로 변환 과정중에 무한으로 참조가 순환문제를 해결 --> 무한 순환을 끊어줌
    @ManyToOne(fetch = FetchType.LAZY)  // 조회만 할때는 member에 대한 정보는 필요없음. 필요하다면 FK에 저장된 member.id로 접근. -> member값 실제 사용시에 로딩.
    @JoinColumn(name = "member_no")     // PK를 Mapping 한다면 생략 가능.
    private Member member;

    private String title;
    private String writer;
    // mysql에 컬럼 만들때는 사이즈를 500~1000으로 설정해야함
    // @Column(length = 1000)
    private String content;

    private String pwd;

    @JsonIgnore
    @OneToMany(mappedBy = "qnaId", cascade = CascadeType.REMOVE)
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answer.setQnaId(this);
        answers.add(answer);
    }
}