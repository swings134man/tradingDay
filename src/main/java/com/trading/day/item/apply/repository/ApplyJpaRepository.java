package com.trading.day.item.apply.repository;

import com.trading.day.item.apply.domain.Apply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyJpaRepository extends JpaRepository<Apply, Long> {

    Page<Apply> findByMember_MemberNo(Long memberNo, Pageable pageable);
}
