package com.trading.day.member.controller;

import com.trading.day.member.domain.MemberDTO;
import com.trading.day.member.domain.Role;
import com.trading.day.member.repository.MemberJpaRepository;
import com.trading.day.member.repository.RoleJpaRepository;
import com.trading.day.member.repository.UserRoleJpaRepository;
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

    @Autowired
    RoleJpaRepository roleRepository;

    @Autowired
    UserRoleJpaRepository userRepository;


//    @Test
//    void save() {
//        MemberDTO inDto = new MemberDTO();
//        inDto.setMemberId("iu");
//        inDto.setName("아이유");
//        inDto.setMemberId("iu1234");
//
//        Long in = 1L;
//        Long out = service.save(inDto);
//
//
//        System.out.println("in : " + in);
//        System.out.println("out : " + out);
//
//        Assertions.assertThat(in).isEqualTo(out);
//
//    }
//
//    @Test
//    void updateName() {
//        MemberDTO inDto = new MemberDTO();
//        inDto.setName("아이유");
//
//        MemberDTO outDto = service.updateMember(inDto);
//        String outName = outDto.getName();
//
//        System.out.println("in : " + inDto.getName());
//        System.out.println("out : " + outName);
//
//        Assertions.assertThat(inDto.getName()).isEqualTo(outName);
//    }

    /************
    * @info : Create Table 이후 초기 1회만 실행 -> Member, User, UserRole Data insert
    * @name : MemberMgmtControllerTest
    * @date : 2022/11/03 5:44 PM
    * @author : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
     * 추후 추가 User data는 void save 메서드 사용할것.
    ************/
    @Test
    void setData() {
        //TEST ONLY role  권한 info Set - ROle
        Role role = new Role();
        role.setRoleNumber(1);
        role.setRoleName("USER");

        Role role2 = new Role();
        role2.setRoleNumber(2);
        role2.setRoleName("MANAGER");

        Role role3 = new Role();
        role3.setRoleNumber(3);
        role3.setRoleName("ADMIN");

        roleRepository.save(role);
        roleRepository.save(role2);
        roleRepository.save(role3);

        // Member
//        MemberDTO inDto = MemberDTO.builder()
//                .name("아이유")
//                .memberId("iu")
//                .email("iu@naver.com")
//                .address("나는주소")
//                .build();
//        Long memberNo = service.save(inDto);

        //Assertions.assertThat(1L).isEqualTo(memberNo);
    }


}//class