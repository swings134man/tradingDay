package com.trading.day.qna.answer.repository;

import com.trading.day.qna.answer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

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
}