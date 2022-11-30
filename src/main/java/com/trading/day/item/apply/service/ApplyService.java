package com.trading.day.item.apply.service;

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
import org.springframework.stereotype.Service;

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
public class ApplyService {

    private final ApplyJpaRepository repository; //repo
    private final MemberJpaRepository memberRepository; //member
    private final ItemBoardJpaRepository boardReposiory; // Board
    private final ModelMapper modelMapper;

    public ApplyDTO savePost(ApplyDTO inDTO) {
        // Member
        Optional<Member> memberResult =
                Optional.ofNullable(memberRepository.findById(inDTO.getMember()).orElseThrow(
                        () -> new IllegalArgumentException("해당 회원이 존재하지 않습니다." + inDTO.getMember()))) ;
        Member memberInfo = memberResult.get();

        // Board
        Optional<ItemBoard> boardEntity = Optional.ofNullable(boardReposiory.findById(inDTO.getItemBoard())).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다." + inDTO.getItemBoard()));
        ItemBoard boardInfo = boardEntity.get();

        // apply
        Apply applyEntity = modelMapper.map(inDTO, Apply.class);
        memberInfo.addApplys(applyEntity);
        boardInfo.addApplys(applyEntity);
        repository.save(applyEntity);

        return modelMapper.map(applyEntity, ApplyDTO.class);
    }//save

    public ApplyDTO findByApplyId(Long applyId) {
        Optional<Apply> applyEntity = Optional.ofNullable(repository.findById(applyId)).orElseThrow(
                () -> new IllegalArgumentException("해당 지원서가 존재하지 않습니다." + applyId));
        Apply result = applyEntity.get();

        ApplyDTO outDTO = modelMapper.map(result, ApplyDTO.class);
        return outDTO;
    }


}//class
