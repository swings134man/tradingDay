package com.trading.day.member.controller;

import com.trading.day.member.domain.MemberDTO;
import com.trading.day.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/member/mgmt/v1/save")
    public Long save(@RequestParam String name) {

        // parameter to DTO
        MemberDTO inDto = MemberDTO.builder()
                .name(name)
                .build();


        Long result = service.save(inDto);


        return result;
    }

}//class
