package com.trading.day.member.service;

import com.trading.day.member.domain.Member;
import com.trading.day.member.domain.MemberDTO;
import com.trading.day.member.domain.Role;
import com.trading.day.member.domain.UserRole;
import com.trading.day.member.repository.MemberJpaRepository;
import com.trading.day.member.repository.RoleJpaRepository;
import com.trading.day.member.repository.UserRoleJpaRepository;
import com.trading.day.qna.domain.Qna;
import com.trading.day.qna.domain.QnaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    private final MemberJpaRepository memberRepository; // MEMBER
    private final UserRoleJpaRepository urRepository; // User_Role
    private final RoleJpaRepository roleRepository; // Role
    private final ModelMapper modelMapper; // DTO <-> Entity 변환 라이브러리

    public Long save(MemberDTO inDto) {

        //Entity 변환 작업
        Member member = modelMapper.map(inDto, Member.class);
        member.setCreateDate(LocalDateTime.now());
        member.setModifiedDate(LocalDateTime.now());


        //TEST ONLY role  권한 info Set
//        Role role = new Role();
//        role.setRoleNumber(1);
//        role.setRoleName("USER");
//
//        Role role2 = new Role();
//        role2.setRoleNumber(2);
//        role2.setRoleName("MANAGER");
//
//        Role role3 = new Role();
//        role3.setRoleNumber(3);
//        role3.setRoleName("ADMIN");
//
//        roleRepository.save(role);
//        roleRepository.save(role2);
//        roleRepository.save(role3);

        // 실제 사용 코드 Role - > USER
        // TODO : 권한부여 방법 생각중. 권한부여는 제일 상위 ADMIN 계정이 가입을 시킴 --> 매니저 권한부여
        // TODO : 가입시키는게 ADMIN이 아니라면 USER권한으로 할까 생각중 ~
        Optional<Role> role = roleFind(1L); //1L 값 = USER
        log.debug("@@@나는 권한 : " + role.get().getRoleId());

        // 실제 사용 코드 user Role Table
        UserRole userRole = new UserRole();
        userRole.setMember(member); // 저장할 MEMBER Entity 객체 -- PARAM : MEMBER ENTITY - TYPE ENTITY
        log.debug("@@@나는 맴버 아이디 : " + member.getMemberId());
//        userRole.setRoleId(role);   // Role 권한 정보 TEST
        userRole.setRoleId(role.get()); // 실사용 코드
        userRole.setCreatedDate(LocalDateTime.now());
        userRole.setModifiedDate(LocalDateTime.now());

        log.debug("@@@나는 RoleId : " + userRole.getRoleId());
        log.debug("@@@나는 Id: " + userRole.getId());
        UserRole saveUserRole = urRepository.save(userRole); // UserRole 저장.

        //실제 사용코드  멤버 save - UserRole에 대한 Target 테이블은 나중에 insert
        Member save = memberRepository.save(member); //
        save.addUserRoles(saveUserRole); // Member Entity에 UserRoles에 대한 정보 add

        // Entity 결과 to DTO
        MemberDTO out = modelMapper.map(save, MemberDTO.class);

        return out.getMemberNo();
    }


    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll(Sort.by(Sort.Direction.DESC, "memberNo"));
    }


    public int deleteMember(MemberDTO inDto) {
        Optional<Member> findResult = memberRepository.findById(inDto.getMemberNo());
        if(findResult.isPresent()) {
            memberRepository.delete(findResult.get());
            return 1;
        }
        return 0;
    }

    // ID(PK)- Member_No 로 회원 정보 검색
    @Transactional(readOnly = true)
    public MemberDTO findById(MemberDTO inDto) {
        Optional<Member> result = memberRepository.findById(inDto.getMemberNo());
        // to DTO
        MemberDTO out = modelMapper.map(result.get(), MemberDTO.class);
        return out;
    }

    // ID - memberId 로 회원 객체 검색
    @Transactional(readOnly = true)
    public MemberDTO findByMemberId(MemberDTO inDTO) {
        Member resultEntity = memberRepository.findByMemberId(inDTO.getMemberId());

        // to DTO
        MemberDTO outDTO = modelMapper.map(resultEntity, MemberDTO.class);
        return outDTO;
    }

    /**
     * methodName : chkdupliId
     * author : TAEIL KIM
     * description : 회원가입시 사용되는 아이디 중복확인 api
     *
     * @return int
     */
    public int chkDupliId(@RequestParam String memberId) {
        int result = 1;

        Member resultEntity = memberRepository.findByMemberId(memberId);
        if(ObjectUtils.isEmpty(resultEntity)) {
            result = 0;
        }
        return result;
    }


    public MemberDTO updateMember(MemberDTO memberDTO) {
        //pk로 업데이트
        Optional<Member> searchResult = Optional.ofNullable(memberRepository.findById(memberDTO.getMemberNo()).orElseThrow(
                () -> new IllegalArgumentException("게시물 업데이트에 실패했습니다."))); // NoSuchElementException::new));

        Member updateEntity = searchResult.get();
        updateEntity.setMemberNo(searchResult.get().getMemberNo());
        updateEntity.setName(memberDTO.getName());
        updateEntity.setEmail(memberDTO.getEmail());
        updateEntity.setTelNo(memberDTO.getTelNo());

        return modelMapper.map(updateEntity, MemberDTO.class);
    }







    // findByName -- > member name 으로 검색
    public MemberDTO findByName(MemberDTO inDto) {

        // Entity
        Member member = modelMapper.map(inDto, Member.class);
        Member result = memberRepository.findByName(member.getName());

        // DTO
        MemberDTO out = modelMapper.map(result, MemberDTO.class);
        return out;
    }

    // Update
    public MemberDTO updateName(MemberDTO inDto) {

        Long pk = Long.valueOf(inDto.getMemberId());

        Optional<Member> result = memberRepository.findById(pk);
        if(result.isPresent()){
            result.get().setName(inDto.getName()); // 이름 변경
            // to DTO
            MemberDTO out = modelMapper.map(result.get(), MemberDTO.class); // Entity 가 Optional 로 Wrap 되어 있으므로 .get()으로 값 꺼내줘야함.
            return out;
        }
        return null;
    }

    /**
    * @info    : ROLE 권한 가져오는 Method - 실사용 코드
    * @name    : roleFind
    * @date    : 2022/10/13 4:10 AM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   : Long id
    * @return  : Optional<Role>
    */
    public Optional<Role> roleFind(Long id) {
        Optional<Role> result = roleRepository.findById(id);
        return result;
    }

}//class
