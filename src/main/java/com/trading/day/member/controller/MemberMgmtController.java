package com.trading.day.member.controller;

import com.trading.day.member.domain.Member;
import com.trading.day.member.domain.MemberDTO;
import com.trading.day.member.service.MemberService;
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

/************
* @info : 고객관리 컨트롤러
* @name : MemberMgmtController
* @date : 2022/10/11 9:14 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
@RestController
@RequiredArgsConstructor
public class MemberMgmtController {

    private final MemberService service;

    // 회원 가입
    @PostMapping("/member/mgmt/v1/save")
    public Long save(@RequestParam String name, @RequestParam String memberId) {

        // parameter to DTO
        MemberDTO inDto = MemberDTO.builder()
                .memberId(memberId)
                .name(name)
                .build();


        Long result = service.save(inDto);


        return result;
    }
    //ID로 회원정보 검색 (PK)
    public MemberDTO findById (@RequestParam Long id) {
        MemberDTO inDto = MemberDTO.builder()
                .memberNo(id)
                .build();
        MemberDTO result = service.findById(inDto);
        return result;
    }

    // 이름으로 회원정보 검색
    @GetMapping("/member/v1/findbyname")
    public MemberDTO findByName(@RequestParam String name) {
        MemberDTO inDto = MemberDTO.builder()
                .name(name)
                .build();

        MemberDTO result = service.findByName(inDto);

        return result;
    }

    // 회원 아이디로 회원 객체 검색
    @GetMapping("/member/v1/findByMemberId")
    public MemberDTO findByMemberId(@RequestParam String memberId) {
        MemberDTO inDTO = MemberDTO.builder()
                .memberId(memberId)
                .build();
        MemberDTO result = service.findByMemberId(inDTO);
        return result;
    }

    // 회원 이름 update
    @PutMapping("/member/v1/updatename")
    public MemberDTO updateName(@RequestParam String name) {
        MemberDTO inDto = MemberDTO.builder()
                .name(name)
                .build();

        MemberDTO out = service.updateMember(inDto);
        return out;
    }

    // 회원 삭제
    @DeleteMapping("/member/v1/deletemember")
    public int deleteMember(@RequestParam Long id) {
        MemberDTO inDto = MemberDTO.builder()
                .memberNo(id)
                .build();

        int result = service.deleteMember(inDto);
        return result;
    }





}//class
