package com.trading.day.member.service;

import com.trading.day.member.domain.Member;
import com.trading.day.member.domain.MemberDTO;
import com.trading.day.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberJpaRepository repository;
    private final ModelMapper modelMapper;

    public MemberDTO save(MemberDTO inDto) {

        //Entity 변환 작업
        Member member = modelMapper.map(inDto, Member.class);
        System.out.println("to Entity " + member.getName());


        Member save = repository.save(member);


        // Entity 결과 to DTO
        MemberDTO out = modelMapper.map(save, MemberDTO.class);
        System.out.println("DTO : " + out.getName());

        return out;
    }




}//class
