package com.trading.day.member.domain;

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

    @OneToMany(mappedBy = "member")
    private List<UserRole> userRoles = new ArrayList<>();

//    @OneToMany(mappedBy = "MemberId") // 1:N 관계에서 어떤것이랑 연결이 되어있는지 -> UserRole 객체 변수명인 "MemberId"
//    private List<UserRole> userRoles = new ArrayList<>();


    @CreatedDate
    private LocalDateTime createDate;
    @CreatedDate
    private LocalDateTime modifiedDate;

    // 편의 메서드
    public void addUserRoles (UserRole userRole) {
        userRoles.add(userRole);
        userRole.setMember(this);
    }
}
