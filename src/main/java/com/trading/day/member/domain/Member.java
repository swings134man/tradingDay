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
    private Long memberNo;

    private String memberId;
    private String name;
    private String email;
    @OneToMany(mappedBy = "member")
    private List<UserRole> userRoles = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createDate;
    @CreatedDate
    private LocalDateTime modifiedDate;

    // Item 게시판 Mapping
    @OneToMany(mappedBy = "member")
    private List<ItemBoard> itemBoards = new ArrayList<>();


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