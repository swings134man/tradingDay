package com.trading.day.member.controller;

import com.trading.day.member.domain.MemberDTO;
import com.trading.day.member.repository.MemberJpaRepository;
import com.trading.day.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMgmtControllerTest {

    @Autowired
    MemberService service;

    @Autowired
    MemberJpaRepository repository;

    @Test
    void save() {
        MemberDTO inDto = new MemberDTO();
        inDto.setMemberId("iu");
        inDto.setName("아이유");

        Long in = 1L;
        Long out = service.save(inDto);


        System.out.println("in : " + in);
        System.out.println("out : " + out);

        Assertions.assertThat(in).isEqualTo(out);

    }

    @Test
    void updateName() {
        MemberDTO inDto = new MemberDTO();
        inDto.setName("아이유");

        MemberDTO outDto = service.updateMember(inDto);
        String outName = outDto.getName();

        System.out.println("in : " + inDto.getName());
        System.out.println("out : " + outName);

        Assertions.assertThat(inDto.getName()).isEqualTo(outName);
    }

}