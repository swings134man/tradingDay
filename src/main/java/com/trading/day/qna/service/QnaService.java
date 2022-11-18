package com.trading.day.qna.service;

import com.trading.day.item.domain.ItemBoard;
import com.trading.day.member.domain.Member;
import com.trading.day.member.repository.MemberJpaRepository;
import com.trading.day.qna.answer.domain.Answer;
import com.trading.day.qna.domain.Qna;
import com.trading.day.qna.domain.QnaDTO;
import com.trading.day.qna.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * packageName :
 * fileName : QnaRepository
 * author : taeil
 * date :
 * description : 1:1 문의에 대한 Service class
 * =======================================================
 * DATE          AUTHOR                      NOTE
 * -------------------------------------------------------
 * 김태일                       최초생성
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class QnaService {

    private final QnaRepository qnaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final ModelMapper modelMapper;

    /**
     * methodName : findAll
     * author : TAEIL KIM
     * description :
     *
     * @return list
     */
    @Transactional(readOnly = true)
    public List<Qna> findAll() {
        return qnaRepository.findAll(Sort.by(Sort.Direction.DESC, "qnaId"));
    }

    /**
     * methodName : findByQnaId
     * author : TAEIL KIM
     * description : 상세페이지를 위한 문의글 상세 조회 api
     *
     * @return list
     */
    @Transactional(readOnly = true)
    public QnaDTO findByQnaId(Long qnaId) {
//        Qna searchResult = qnaRepository.findByQnaId(qnaId);

        Optional<Qna> result = Optional.ofNullable(qnaRepository.findById(qnaId).orElseThrow(
                () -> new IllegalArgumentException("문의가 존재하지 않습니다. 확인 후 다시 시도하세요." + qnaId)));

        //pk로 가져와서
        Qna entity = result.get();
        //fk로 묶인 pk로 조회하고
        List<Answer> answers = entity.getAnswers();

        QnaDTO outQnaDTO = modelMapper.map(entity, QnaDTO.class);
        //디티오에 합쳐주고 리턴
        outQnaDTO.setAnswers(answers);

        return outQnaDTO;
    }

    /**
     * methodName : updateQna
     * author : TAEIL KIM
     * description :
     *
     * @param qnaInDTO
     * @return qna dto
     */
    public QnaDTO updateQna(QnaDTO qnaInDTO) {
        //pk로 업데이트
        Optional<Qna> searchResult = Optional.ofNullable(qnaRepository.findById(qnaInDTO.getQnaId()).orElseThrow(
                () -> new IllegalArgumentException("게시물 업데이트에 실패했습니다."))); // NoSuchElementException::new));

            Qna updateEntity = searchResult.get();
            updateEntity.setQnaId(searchResult.get().getQnaId());
            updateEntity.setWriter(qnaInDTO.getWriter());
            updateEntity.setTitle(qnaInDTO.getTitle());
            updateEntity.setContent(qnaInDTO.getContent());

        return modelMapper.map(updateEntity, QnaDTO.class);
    }

    /**
     * methodName : deleteQna
     * author : TAEIL KIM
     * description :
     *
     * @param qnaInDTO
     * @return int
     */
    public int deleteQna(QnaDTO qnaInDTO) {
        //TODO 다건으로할까 단건으로할까..
        // pk를 담을 리스트
        List<Long> qnaIdList = new ArrayList<Long>();
        int result = 0;

        try {
//            for(int i = 0; i < qnaInDTO.getQnaList().size(); i++) {
//                qnaIdList.set(i, qnaInDTO.getQnaList().get(i).getQnaId());
//                System.out.println("qnaInDTO : "  + qnaIdList.get(i));
                qnaRepository.deleteById(qnaInDTO.getQnaId());
//            }
            result = 1;
        } catch(Exception e) {
            throw new IllegalArgumentException("삭제에 실패했습니다. 다시 확인하고 삭제해주세요.");
        }
        return result;
    }

    /**
     * methodName : findByWriter
     * author : TAEIL KIM
     * description :
     *
     * @param writer
     * @param pageable
     * @return page
     */
    @Transactional(readOnly = true)
    public Page<QnaDTO> findByWriter(String writer, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.by("qnaId").descending());
        Page<Qna> findQnaEntity = qnaRepository.findByWriter(writer, pageable);

        return new QnaDTO().toPageDTO(findQnaEntity);
    }

    @Transactional(readOnly = true)
    public Page<QnaDTO> findAllPaging(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, Sort.by("qnaId").descending());

        Page<Qna> findQnaEntity = qnaRepository.findAll(pageable);
        return new QnaDTO().toPageDTO(findQnaEntity);
    }


    /**
     * methodName : saveQna
     * author : TAEIL KIM
     * description :
     *
     * @param inQnaDTO
     * @return qna dto
     */
    public QnaDTO saveQna(QnaDTO inQnaDTO) {
        //memberId를 기준으로 member 테이블 조회 --> writer 작성자
        Member selectMemberNo = memberJpaRepository.findByMemberId(inQnaDTO.getWriter());

        if(!ObjectUtils.isEmpty(selectMemberNo)) {
            System.out.println("selectMemberNo : " + selectMemberNo.getMemberNo());
        } else {
            // TODO : 공용익셉션을 만들어볼까? 생각해보자(BizException)
            // 결과가 없으면 exception으로 잡아서 ui(view)로 잡아서 던지면 좋겠당..
            throw new IllegalArgumentException("조회된 아이디가 없어요.");
        }

        Qna qnaEntity =  modelMapper.map(inQnaDTO, Qna.class);
        qnaEntity.setMember(selectMemberNo);

        Qna resultEntity = qnaRepository.save(qnaEntity);
        // 서로간에 매핑을 시켜줬어야함 Member에 addQnas..
        selectMemberNo.addQnas(qnaEntity);

        QnaDTO outDTO = modelMapper.map(resultEntity, QnaDTO.class);
        return outDTO;
    }
} // service end
