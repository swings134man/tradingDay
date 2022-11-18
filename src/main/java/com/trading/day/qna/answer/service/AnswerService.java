package com.trading.day.qna.answer.service;

import com.trading.day.item.reply.domain.ItemBoardReply;
import com.trading.day.qna.answer.domain.Answer;
import com.trading.day.qna.answer.domain.AnswerDTO;
import com.trading.day.qna.answer.repository.AnswerRepository;
import com.trading.day.qna.domain.Qna;
import com.trading.day.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * packageName : com.trading.day.qna.answer.service
 * fileName : AnswerService
 * author : taeil
 * date : 2022/11/18
 * description :
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 * 2022/11/18        taeil                   최초생성
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AnswerService {

    private final ModelMapper modelMapper;
    private final AnswerRepository answerRepository;
    private final QnaRepository qnaRepository;

    public AnswerDTO answerSave(AnswerDTO inDTO) {

        // param을 엔티티로
        Answer answerEntity = modelMapper.map(inDTO, Answer.class);

        Optional<Qna> qnaResult = qnaRepository.findById(inDTO.getQnaId());
        Qna qnaEntity = qnaResult.get();

        qnaEntity.addAnswer(answerEntity);
        answerRepository.save(answerEntity);

        return modelMapper.map(answerEntity, AnswerDTO.class);
    }



    public AnswerDTO answerUpdate(AnswerDTO inDTO) {
        Optional<Answer> findResult = Optional.ofNullable(answerRepository.findById(inDTO.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 답변이 존재하지 않습니다." + inDTO.getId())
        ));

        Answer answerEntity = findResult.get();

        answerEntity.setContent(inDTO.getContent());
        answerEntity.setCustomerMemo(inDTO.getCustomerMemo());

        return modelMapper.map(answerEntity, AnswerDTO.class);
    }

    public int answerDelete(@RequestParam Long id) {
        Optional<Answer> result = Optional.ofNullable(answerRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 문의가 존재하지 않습니다" + id)
        ));
        answerRepository.delete(result.get());
        return 1;
    }

}