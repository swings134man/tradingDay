package com.trading.day.member.repository;

import com.trading.day.member.domain.Member;
import com.trading.day.member.domain.MemberDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Member findByName(String name);
    Member findByMemberId(String memberId);

    Optional<Member> findOneWithAuthoritiesByMemberId(String memberId);

    Member findByEmail(String email);

}
