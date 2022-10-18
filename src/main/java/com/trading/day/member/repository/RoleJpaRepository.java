package com.trading.day.member.repository;

import com.trading.day.member.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleJpaRepository extends JpaRepository<Role, Long> {


}
