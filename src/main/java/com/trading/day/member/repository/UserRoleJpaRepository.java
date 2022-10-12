package com.trading.day.member.repository;

import com.trading.day.member.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleJpaRepository extends JpaRepository<UserRole, Long> {
}
