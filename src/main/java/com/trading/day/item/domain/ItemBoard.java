package com.trading.day.item.domain;

import com.trading.day.config.BaseTimeEntity;
import com.trading.day.member.domain.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Slf4j
@Table(name = "ITEM_BOARD_INFO")
public class ItemBoard extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;            // 게시판 ID
    private String title;       // 제목
    private String writer;      // 작성자

    @Column(columnDefinition = "TEXT")
    private String content;     // 내용
    private String type;        // 물건 타입 -> 신품/중고
    private Long view;          // 조회수

    @ManyToOne(fetch = FetchType.LAZY)  // 조회만 할때는 member에 대한 정보는 필요없음. 필요하다면 FK에 저장된 member.id로 접근. -> member값 실제 사용시에 로딩.
    @JoinColumn(name = "member_no")     // PK를 Mapping 한다면 생략 가능.
    private Member member; //member_no (Member 의 PK값) 프론트에서 입력받은 writer를 그대로 writer 컬럼에 set, ItemBoard에서는 해당 writer의 PK 값 저장 -> 이게 좋음
    // fetch join 이득또한 있고, writer 컬럼이 존재하는데 또 member_id 매핑할 이유가 없음.
}
