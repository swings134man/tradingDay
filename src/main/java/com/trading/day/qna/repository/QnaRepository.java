package com.trading.day.qna.repository;


import com.trading.day.item.domain.ItemBoard;
import com.trading.day.qna.domain.Qna;
import com.trading.day.qna.domain.QnaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
/**
 * packageName :
 * fileName : QnaRepository
 * author : taeil
 * date :
 * description : 1:1 문의에 대한 Repository class
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 *               김태일                       최초생성
 */
public interface QnaRepository extends JpaRepository<Qna,Long> {
    // 특정 고객이 남긴 문의글만 조회
    //List<Qna> findByWriter(String writer);

    Qna findByQnaId(Long QnaId);
    // id로 작성자 검색
    Page<Qna> findByWriter(String Writer, Pageable pageable);

}
