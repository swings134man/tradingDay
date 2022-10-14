package com.trading.day.qna.service;

import com.trading.day.member.repository.MemberJpaRepository;
import com.trading.day.qna.domain.Qna;
import com.trading.day.qna.domain.QnaDTO;
import com.trading.day.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class QnaService {

    private final QnaRepository qnaRepository;
    private final MemberJpaRepository memberJpaRepository;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<Qna> findAll() {
        return qnaRepository.findAll(Sort.by(Sort.Direction.DESC, "qnaId"));
    }

    public QnaDTO saveQna(QnaDTO inQnaDTO) {
        Qna qnaEntity =  modelMapper.map(inQnaDTO, Qna.class);



        Qna resultEntity = qnaRepository.save(qnaEntity);

        QnaDTO outDTO = modelMapper.map(resultEntity, QnaDTO.class);



        return outDTO;
    }

}
