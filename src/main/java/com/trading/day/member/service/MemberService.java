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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
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

        // 실제 사용 코드 Role - > USER
//        Optional<Role> role = roleFind(1L); //1L 값 = USER


        // 실제 사용 코드 user Role Table
        UserRole userRole = new UserRole();
        userRole.setMemberId(member); // 저장할 MEMBER Entity 객체 -- PARAM : MEMBER ENTITY - TYPE ENTITY
        userRole.setRoleId(role);   // Role 권한 정보
//        userRole.setRoleId(role.get()); // 실사용 코드
        userRole.setCreatedDate(LocalDateTime.now());
        userRole.setModifiedDate(LocalDateTime.now());
        UserRole saveUserRole = urRepository.save(userRole); // UserRole 저장.

        //실제 사용코드  멤버 save - UserRole에 대한 Target 테이블은 나중에 insert
        Member save = repository.save(member); //
        save.addUserRoles(saveUserRole); // Member Entity에 UserRoles에 대한 정보 add

        // Entity 결과 to DTO
        MemberDTO out = modelMapper.map(save, MemberDTO.class);

        return out.getId();
    }


    public MemberDTO findByName(MemberDTO inDto) {

        // Entity
        Member member = modelMapper.map(inDto, Member.class);
        Member result = repository.findByName(member);

        // DTO
        MemberDTO out = modelMapper.map(result, MemberDTO.class);
        return out;
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
