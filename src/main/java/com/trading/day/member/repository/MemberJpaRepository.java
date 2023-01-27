package com.trading.day.member.repository;

import com.trading.day.member.domain.Member;
import com.trading.day.member.domain.MemberDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Member findByName(String name);
    Member findByMemberId(String memberId);

    Optional<Member> findOneWithAuthoritiesByMemberId(String memberId);

    Member findByEmail(String email);

    // 로그인 시간 업데이트
    @Modifying
    @Query(value = "update Member set lastLoginTime = current_timestamp where memberId = :memberId")
    void saveLoginTime(@Param("memberId") String memberId);

    // Batch - 일정기간 + 계정 활성화 여부 검색.
    List<Member> findByLastLoginTimeBeforeAndActivatedEquals(LocalDateTime date, boolean activated);
}
