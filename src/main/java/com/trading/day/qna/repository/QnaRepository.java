package com.trading.day.qna.repository;


import com.trading.day.qna.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface QnaRepository extends JpaRepository<Qna,Long> {
    // 특정 고객이 남긴 문의글만 조회
    List<Qna> findByWriter(String writer);

}
