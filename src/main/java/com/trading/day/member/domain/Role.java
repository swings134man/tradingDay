package com.trading.day.member.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "ROLE")
public class Role {

    @Id @GeneratedValue
    @Column(name = "role_id")
    private int roleId; // 권한번호 0,1,2

    private String roleName; // admin, user


}
