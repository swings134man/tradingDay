package com.trading.day.member.repository;

import com.trading.day.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Member findByName(String name);
}
