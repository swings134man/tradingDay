package com.trading.day.member.service;

import com.trading.day.member.domain.Member;
import com.trading.day.member.domain.MemberDTO;
import com.trading.day.member.domain.Role;
import com.trading.day.member.domain.UserRole;
import com.trading.day.member.repository.MemberJpaRepository;
import com.trading.day.member.repository.RoleJpaRepository;
import com.trading.day.member.repository.UserRoleJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    private final MemberJpaRepository repository; // MEMBER
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
        Optional<Role> role = roleFind(1L); //1L 값 = USER


        // 실제 사용 코드 user Role Table
        UserRole userRole = new UserRole();
        userRole.setMember(member); // 저장할 MEMBER Entity 객체 -- PARAM : MEMBER ENTITY - TYPE ENTITY
//        userRole.setRoleId(role);   // Role 권한 정보
        userRole.setRoleId(role.get()); // 실사용 코드
        userRole.setCreatedDate(LocalDateTime.now());
        userRole.setModifiedDate(LocalDateTime.now());
        UserRole saveUserRole = urRepository.save(userRole); // UserRole 저장.

        //실제 사용코드  멤버 save - UserRole에 대한 Target 테이블은 나중에 insert
        Member save = repository.save(member); //
        save.addUserRoles(saveUserRole); // Member Entity에 UserRoles에 대한 정보 add

        // Entity 결과 to DTO
        MemberDTO out = modelMapper.map(save, MemberDTO.class);

        return out.getMemberNo();
    }

    // ID(PK)- Member_No 로 회원 정보 검색
    public MemberDTO findById(MemberDTO inDto) {

        Optional<Member> result = repository.findById(inDto.getMemberNo());

        // to DTO
        MemberDTO out = modelMapper.map(result.get(), MemberDTO.class);
        return out;
    }

    // ID - memberId 로 회원 객체 검색
    public MemberDTO findByMemberId(MemberDTO inDTO) {
        Member resultEntity = repository.findByMemberId(inDTO.getMemberId());

        // to DTO
        MemberDTO outDTO = modelMapper.map(resultEntity, MemberDTO.class);
        return outDTO;
    }


    // findByName -- > member name 으로 검색
    public MemberDTO findByName(MemberDTO inDto) {

        // Entity
        Member member = modelMapper.map(inDto, Member.class);
        Member result = repository.findByName(member.getName());

        // DTO
        MemberDTO out = modelMapper.map(result, MemberDTO.class);
        return out;
    }

    // Update
    public MemberDTO updateMember(MemberDTO inDto) {

        // Test 를 위한 PK 하드코딩 -> 실제로는 Session의 회원 ID나 다른값으로 대체 -> 아래의 DB값 조회도 상황에 따라 find Method 만들어야함.
        Long pk = 1L;

        // DB 값 조회
        Optional<Member> result = repository.findById(pk);
        if(result.isPresent()){
            result.get().setName(inDto.getName()); // 이름 변경

            // to DTO
            MemberDTO out = modelMapper.map(result.get(), MemberDTO.class); // Entity 가 Optional 로 Wrap 되어 있으므로 .get()으로 값 꺼내줘야함.
            return out;
        }

        return null;
    }


    public int deleteMember(MemberDTO inDto) {
        // Test 를 위한  PK 하드코딩
        inDto.setMemberNo(3L);

        Optional<Member> findResult = repository.findById(inDto.getMemberNo());
        if(findResult.isPresent()) {
            repository.delete(findResult.get());
            return 1;
        }

        return 0;
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
