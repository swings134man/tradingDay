package com.trading.day.qna.answer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trading.day.config.BaseTimeEntity;
import com.trading.day.qna.domain.Qna;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * packageName : com.trading.day.qna.answer.domain
 * fileName : Answer
 * author : taeil
 * date : 2022/11/18
 * description :
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 * 2022/11/18        taeil                   최초생성
 */


@Entity
@Getter
@Setter
@Table(name = "ANSWER")
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    private String content;
    private String writer;
//    private String customerMemo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id_qna_id")
    private Qna qnaId;




}