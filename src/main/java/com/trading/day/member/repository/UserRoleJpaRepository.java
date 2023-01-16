package com.trading.day.member.repository;

import com.trading.day.member.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRoleJpaRepository extends JpaRepository<UserRole, Long> {

    @Modifying
    @Query(value = "delete from UserRole where memberNo=:memberNo")
    void deleteByMemberNo(@Param("memberNo")Long memberNo);
}
