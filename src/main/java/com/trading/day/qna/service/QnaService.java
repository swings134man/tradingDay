package com.trading.day.qna.service;

import com.trading.day.member.domain.Member;
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
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        return qnaRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
    }

//    @Transactional(readOnly = true)
//    public List<Qna> findById(@RequestParam String memberNo) {
//        List<Qna> resultSearch = qnaRepository.findByMemberNo(memberNo);
//        System.out.println("resultSearch --> : " + resultSearch);
//        return resultSearch;
//    }

    public QnaDTO saveQna(QnaDTO inQnaDTO) {
        //memberId를 기준으로 member 테이블 조회 --> writer 작성자
        Member selectMemberNo = memberJpaRepository.findByMemberId(inQnaDTO.getWriter());

        if(!ObjectUtils.isEmpty(selectMemberNo)) {
            System.out.println("selectMemberNo : " + selectMemberNo.getMemberNo());
        } else {
            // TODO : 공용익셉션을 만들어볼까? 생각해보자(BizException)
            // 결과가 없으면 exception으로 잡아서 ui(view)로 잡아서 던지던지
            // jpa가 알아서 맴버에 참조할 pk가 없다고 막아줘야되는데 그냥 null이 저장되어버림
            System.out.println("조회 결과가 없는데?");
        }

        Qna qnaEntity =  modelMapper.map(inQnaDTO, Qna.class);
        qnaEntity.setMember(selectMemberNo);

        Qna resultEntity = qnaRepository.save(qnaEntity);
        QnaDTO outDTO = modelMapper.map(resultEntity, QnaDTO.class);

        return outDTO;
    }





}
