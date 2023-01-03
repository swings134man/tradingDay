package com.trading.day.item.apply.service;

import com.trading.day.common.email.EmailDTO;
import com.trading.day.common.email.EmailService;
import com.trading.day.item.apply.domain.Apply;
import com.trading.day.item.apply.domain.ApplyDTO;
import com.trading.day.item.apply.repository.ApplyJpaRepository;
import com.trading.day.item.domain.ItemBoard;
import com.trading.day.item.repository.ItemBoardJpaRepository;
import com.trading.day.member.domain.Member;
import com.trading.day.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/************
 * @info : 프로젝트 지원서 서비스
 * @name : applyService
 * @date : 2022/11/30 2:20 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ApplyService {

    private final ApplyJpaRepository repository; //repo
    private final MemberJpaRepository memberRepository; //member
    private final ItemBoardJpaRepository boardReposiory; // Board
    private final EmailService emailService;            // email
    private final ModelMapper modelMapper;      //model mapper

    /**
     * @info    : 지원서 작성 - 저장
     * @name    : savePost
     * @date    : 2022/12/01 1:24 AM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description : 성공시 1, 실패시 0 return.
     */
    @Transactional
    public int savePost(ApplyDTO.ApplyRequest inDTO) {
        int success = 0;
        // Member
        Member memberInfo = memberRepository.findByMemberId(inDTO.getMemberId());
        if(memberInfo == null) {
            throw new IllegalArgumentException("해당 회원이 존재하지 않습니다." + inDTO.getMemberId());
        }

        // Board
        Optional<ItemBoard> boardEntity = Optional.ofNullable(boardReposiory.findById(inDTO.getItemBoard())).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다." + inDTO.getItemBoard()));
        ItemBoard boardInfo = boardEntity.get();

        // apply
        Apply applyEntity = modelMapper.map(inDTO, Apply.class);
        memberInfo.addApplys(applyEntity);
        boardInfo.addApplys(applyEntity);
        repository.save(applyEntity);

        success = 1;

        // 로깅
//        makeLog(memberInfo.getMemberNo(), inDTO.getType(), inDTO.getLevel());

        return success;
//        return modelMapper.map(applyEntity, ApplyDTO.class);
    }//save

    /**
     * @info    : 지원서 디테일페이지 조회 - 지원서 ID
     * @name    : findByApplyId
     * @date    : 2022/12/01 1:07 AM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   : 지원서 ID - Long
     * @return  :
     * @Description :
     */
    @Transactional
    public ApplyDTO findByApplyId(Long applyId) {
        Optional<Apply> applyEntity = Optional.ofNullable(repository.findById(applyId)).orElseThrow(
                () -> new IllegalArgumentException("해당 지원서가 존재하지 않습니다." + applyId));
        Apply result = applyEntity.get();

//        ApplyDTO outDTO = modelMapper.map(result, ApplyDTO.class);
        ApplyDTO outDTO = new ApplyDTO().toDetail(result);
        return outDTO;
    }//findByApplyId


    /**
     * @info    : 지원서 페이징 조회 - String 회원 아이디
     * @name    : findByWriter
     * @date    : 2022/12/01 10:37 PM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description : String Writer 로 검색후 Paging 처리
     * 프로세스 : String 회원아이디 조회 -> Apply 에서 회원의 Long Id로 where 조회 후 DTO converting, return.
     */
    @Transactional
    public Page<ApplyDTO> findByWriter(String memberId, Pageable pageable) {
        Member memberEntity = memberRepository.findByMemberId(memberId);
        if(memberEntity == null) {
            throw new IllegalArgumentException("해당 회원이 존재하지 않습니다." + memberId);
        }

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 10, Sort.by("applyId").descending());

        Page<Apply> result = repository.findByMember_MemberNo(memberEntity.getMemberNo(), pageable);
        return new ApplyDTO().toPageDTO(result);
    } // findbyWriter


    /**
     * @info    : 지원서 거절. - 거절 이메일 발송.
     * @name    : applyReplyReject
     * @date    : 2022/12/04 1:17 AM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description :
     */
    @Transactional
    public boolean applyReplyReject(ApplyDTO.ApplyRequest inDTO) {

        boolean res = false;
        String msg = "메일 발송 실패";
        EmailDTO emailDTO = EmailDTO.builder()
                .targetMail(inDTO.getWriterEmail())
                .boardId(inDTO.getItemBoard())
                .build();

        if(emailDTO.getTargetMail().equals("null")) {
            throw new IllegalArgumentException("이메일이 입력되지 않았습니다.");
        }

        try {
            boolean result = emailService.sendMailReject(emailDTO);
            if (result){
                msg = "메일 발송 성공.";
                res = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if(res) {
            Optional<Apply> findId = repository.findById(inDTO.getApplyId());
            Apply apply = findId.get();
            apply.setApplyStatus("denied"); // 상태 : 거절
        }

        //TODO : batch 테이블 or history 테이블로 쌓아야함.
        return res;
    }

    /**
     * @info    : 지원서 수락 - 화면에서의 form 존재.
     * @name    : applyReplyPermit
     * @date    : 2022/12/04 1:17 AM
     * @author  : SeokJun Kang(swings134@gmail.com)
     * @version : 1.0.0
     * @param   :
     * @return  :
     * @Description :
     */
    @Transactional
    public boolean applyReplyPermit(ApplyDTO.ApplyRequest inDTO) {

        String msg = "메일 발송 실패.";
        boolean res = false;
        EmailDTO emailDTO = EmailDTO.builder()
                .title(inDTO.getTitle())
                .content(inDTO.getContent())
                .targetMail(inDTO.getWriterEmail())
                .boardIdNo(inDTO.getBoardId())
                .build();

        try {
            boolean result = emailService.sendMailMime(emailDTO);
            if (result){
                msg = "메일 발송 성공.";
                res = true;
            }else {
//                throw new RuntimeException("이메일 발송 중 문제가 발생했습니다!");
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if(res) {
            Optional<Apply> findId = repository.findById(inDTO.getApplyId());
            Apply apply = findId.get();
            apply.setApplyStatus("accept"); // 상태 : 수락
        }

        //TODO : batch 테이블 or history 테이블로 쌓아야함.
        return res;
    }

    // 추가할 메서드 : 해당 지원서는 history 테이블로 전송.


}//class
