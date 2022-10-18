package com.trading.day.qna.repository;


import com.trading.day.qna.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna,Long> {
    // 특정 고객이 남긴 문의글만 조회
    List<Qna> findByMemberNo(String memberId);
}
