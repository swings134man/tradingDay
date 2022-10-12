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
        inDto.setName("봄");

        Long in = 8L;
        Long out = service.save(inDto);


        System.out.println("in : " + in);
        System.out.println("out : " + out);

        Assertions.assertThat(in).isEqualTo(out);


    }
}