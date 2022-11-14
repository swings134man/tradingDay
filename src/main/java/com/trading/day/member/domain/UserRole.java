package com.trading.day.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "USER_ROLE")
public class UserRole {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Long id;

    // 다 : 1 -> 다쪽에 ManyToOne
    // UserRole 은 하나의 멤버만 가질수 있음.
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "member_no")
    private Member member;

    // N : 1
    // 하나의 role 에 여러개 UserRole
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "role_id")
    private Role roleId;

    @CreatedDate
    private LocalDateTime createdDate;
    @CreatedDate
    private LocalDateTime modifiedDate;

}
