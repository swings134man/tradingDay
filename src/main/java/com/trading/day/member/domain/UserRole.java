package com.trading.day.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "USER_ROLE")
@IdClass(UserRole.class)
public class UserRole implements GrantedAuthority {
    @Id
    @Column(name="member_no")
    private Long memberNo;

    @Id
    private String authority;

    @CreatedDate
    private LocalDateTime createdDate;
    @CreatedDate
    private LocalDateTime modifiedDate;

    public UserRole(Long memberNo, String authority) {
        this.memberNo = memberNo;
        this.authority = authority;
    }
}


