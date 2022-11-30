package com.trading.day.item.apply.repository;

import com.trading.day.item.apply.domain.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyJpaRepository extends JpaRepository<Apply, Long> {
}
