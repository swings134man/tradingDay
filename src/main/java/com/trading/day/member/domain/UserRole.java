package com.trading.day.member.domain;

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

    @Id @GeneratedValue
    @Column(name = "user_role_id")
    private Long id;

    // 다 : 1 -> 다쪽에 ManyToOne
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member MemberId;
//
//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private Role roleId;

//    @CreatedDate
//    private LocalDateTime createdDate;
//
//    @CreatedDate
//    private LocalDateTime modifiedDate;

}
