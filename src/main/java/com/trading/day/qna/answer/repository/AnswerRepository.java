package com.trading.day.qna.answer.repository;

import com.trading.day.qna.answer.domain.Answer;
import com.trading.day.qna.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * packageName : com.trading.day.qna.answer.repository
 * fileName : AnswerRepository
 * author : taeil
 * date : 2022/11/18
 * description :
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 * 2022/11/18        taeil                   최초생성
 */

public interface AnswerRepository extends JpaRepository<Answer,Long> {



    @Query(value = "select id from Answer where id=:qnaId")
    // select answer_id from answer where qna_id = 2;
    Long findByQnaId(@Param("qnaId") Long qnaId);

    @Modifying
    @Query(value = "delete from Answer where id=:answerId")
    void deleteByAnswerId(@Param("answerId") Long answerId);

}