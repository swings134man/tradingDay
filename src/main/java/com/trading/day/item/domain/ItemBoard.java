package com.trading.day.item.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.trading.day.common.file.ImageFile;
import com.trading.day.config.BaseTimeEntity;
import com.trading.day.item.reply.domain.ItemBoardReply;
import com.trading.day.member.domain.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    // 댓글 Entity
    /*
        순환참조 문제
        1. post 직렬화
        2. post 내부의 replys 직렬화
        3. 각 replys 내부에 있는 posts 를 직렬화
        - 무한반복 문제
        -@JsonManagedReference
            - 연관관계 주인 반대편에 선언 - 정상적으로 직렬화 수행
        -@JsonBackReference
            - 직렬화가 되지 않도록 수행
    행*/
    @JsonManagedReference
    @OneToMany(mappedBy = "boardId", cascade = CascadeType.REMOVE)
    private List<ItemBoardReply> replys = new ArrayList<>();

    public void addReplys(ItemBoardReply reply) {
        reply.setBoardId(this);
        replys.add(reply);
    }

    // Image
    @JsonManagedReference
    @OneToMany(mappedBy = "boardId", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<ImageFile> images = new ArrayList<>();

    public void addImages(ImageFile file) {
        file.setBoardId(this);
        images.add(file);
    }

    // 조회수 증가
    public void increaseView() {
        this.view ++;
    }
}
