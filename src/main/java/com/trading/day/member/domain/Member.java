package com.trading.day.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trading.day.item.domain.ItemBoard;
import com.trading.day.qna.domain.Qna;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Slf4j
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_no")
    private Long memberNo;                 /* 고객번호 */
    private String memberId;               /* 고객 ID */
    private String name;                   /* 이름 */
    private String email;                  /* 이메일*/
    private String telNo;                  /* 전화번호 */
    //private String address;                /* 주소 */

    @CreatedDate
    private LocalDateTime createDate;      /* 가입 날짜 */
    @CreatedDate
    private LocalDateTime modifiedDate;     /* 수정 날짜 */

    // 권한 테이블 mapping
    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<UserRole> userRoles = new ArrayList<>();

    // Item 게시판 Mapping
    @OneToMany(mappedBy = "member")
    private List<ItemBoard> itemBoards = new ArrayList<>();

    // Qna mapping
    @JsonIgnore // --> Json으로 변환 과정중에 무한으로 참조가 순환문제를 해결 --> 무한 순환을 끊어줌
    @OneToMany(mappedBy = "member")
    private List<Qna> qnas = new ArrayList<>();


    public void addQnas (Qna qna) {
        qnas.add(qna);
        qna.setMember(this);
    }

    // 편의 메서드
    public void addUserRoles (UserRole userRole) {
        userRoles.add(userRole);
        userRole.setMember(this);
    }
    // Item Board add
    public void addItemBoards(ItemBoard itemBoard) {
        itemBoards.add(itemBoard);
        itemBoard.setMember(this);
    }

}