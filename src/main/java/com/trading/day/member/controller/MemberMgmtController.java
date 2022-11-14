package com.trading.day.member.controller;

import com.trading.day.member.domain.Member;
import com.trading.day.member.domain.MemberDTO;
import com.trading.day.member.service.MemberService;
import com.trading.day.qna.domain.Qna;
import com.trading.day.qna.domain.QnaDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

/************
* @info : 고객관리 컨트롤러
* @name : MemberMgmtController
* @date : 2022/10/11 9:14 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
@RestController
@RequiredArgsConstructor
@RequestMapping("/member/v1")
public class MemberMgmtController {

    private final MemberService memberService;

    /**
     * methodName : findAll
     * author : TAEIL KIM
     * description :
     *
     * @return list
     */
    @ApiOperation(value ="고객 전체 조회", notes ="조건에 상관 없이 강비되어있는 모두를 조회함")
    @GetMapping("/memberlist")
    public List<Member> findAll() {
        return memberService.findAll();
    }

    // 회원 가입
    @PostMapping("/save")
    @ApiOperation(value =  "회원 가입 api", notes = "회원 가입 처리함")
    public Long save(@RequestBody MemberDTO memberDTO) {
        Long result = memberService.save(memberDTO);
        return result;
    }

    @DeleteMapping("/deletemember")
    @ApiOperation(value =  "회원 탈퇴 api", notes = "회원 탈퇴 처리함")
    public int deleteMember(MemberDTO inDto) {
        int result = memberService.deleteMember(inDto);
        return result;
    }

    @GetMapping("/findbyid")
    @ApiOperation(value =  "pk로 해당 고객 정보조회 api", notes = "단건 회원 정보 조회")
    //ID로 회원정보 검색 (PK)
    public MemberDTO findById (@RequestParam Long id) {
        MemberDTO inDto = MemberDTO.builder()
                .memberNo(id)
                .build();
        MemberDTO result = memberService.findById(inDto);
        return result;
    }

    /**
     * methodName : updateMember
     * author : TAEIL KIM
     * description :
     *
     * @param memberDTO
     * @return qna dto
     */
    @ApiOperation(value = "회원정보 수정 api", notes = "회원 정보를 수정함")
    @PostMapping("/updatemember")
    public MemberDTO updateMember(@RequestBody MemberDTO memberDTO) {
        return memberService.updateMember(memberDTO);
    }











     //이름으로 회원정보 검색
    @GetMapping("/member/v1/findbyname")
    public MemberDTO findByName(@RequestParam String name) {
        MemberDTO inDto = MemberDTO.builder()
                .name(name)
                .build();

        MemberDTO result = memberService.findByName(inDto);

        return result;
    }

    // 회원 아이디로 회원 객체 검색
    @GetMapping("/findbymemberid")
    public MemberDTO findByMemberId(@RequestParam String memberId) {
        MemberDTO inDTO = MemberDTO.builder()
                .memberId(memberId)
                .build();
        MemberDTO result = memberService.findByMemberId(inDTO);
        return result;
    }

    // 회원 이름 update
    @PutMapping("/updatename")
    public MemberDTO updateName(@RequestParam String name) {
        MemberDTO inDto = MemberDTO.builder()
                .name(name)
                .build();

        MemberDTO out = memberService.updateName(inDto);
        return out;
    }
}//class
