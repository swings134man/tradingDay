package com.trading.day.member.service;

import com.trading.day.member.domain.*;
import com.trading.day.member.repository.MemberJpaRepository;
import com.trading.day.member.repository.RoleJpaRepository;
import com.trading.day.member.repository.UserRoleJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import static com.trading.day.member.domain.SocialMember.Provider.google;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService implements UserDetailsService{

    private final MemberJpaRepository memberRepository; // MEMBER
    private final UserRoleJpaRepository urRepository; // User_Role
    private final RoleJpaRepository roleRepository; // Role
    private final ModelMapper modelMapper; // DTO <-> Entity 변환 라이브러리

    public Long save(MemberDTO inDto) {

        //Entity 변환 작업
        Member member = modelMapper.map(inDto, Member.class);
//        member.setCreateDate(LocalDateTime.now());
//        member.setModifiedDate(LocalDateTime.now());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String pwd = bCryptPasswordEncoder.encode(member.getPwd());
        member.setPwd(pwd);

        Member save = memberRepository.save(member);
        addAuthority(save.getMemberNo(), "ROLE_USER");
        MemberDTO out = modelMapper.map(save, MemberDTO.class);
        return out.getMemberNo();
    }

    public void addAuthority(Long userId, String authority) {
        memberRepository.findById(userId).ifPresent(user -> {
            UserRole newRole = new UserRole(user.getMemberNo(), authority);
            newRole.setCreatedDate(LocalDateTime.now());
            newRole.setModifiedDate(LocalDateTime.now());
            urRepository.save(newRole);
        });
    }

    public Long manageSave(MemberDTO memberDTO) { // -> admin 가입시키면 --> 매니저
        Member member = modelMapper.map(memberDTO, Member.class);
//        member.setCreateDate(LocalDateTime.now());
//        member.setModifiedDate(LocalDateTime.now());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String pwd = bCryptPasswordEncoder.encode(member.getPwd());
        member.setPwd(pwd);
        Member save = memberRepository.save(member);

        addAuthority(save.getMemberNo(), "ROLE_MANAGER");
        MemberDTO result = modelMapper.map(save, MemberDTO.class);
        return result.getMemberNo();

    }

    @Transactional(readOnly = true)
    public boolean memberModiPwdChk(MemberDTO memberDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Member searchRslt = memberRepository.findByMemberId(memberDTO.getMemberId());
        String dbPwd = searchRslt.getPwd();
        String dtoPwd = memberDTO.getPwd();

        return bCryptPasswordEncoder.matches(dtoPwd, dbPwd);
    }


    public Member save2(Member member) {
        return memberRepository.save(member);
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
    @Transactional(readOnly = true)
    public int chkDupliId(@RequestParam String memberId) {
        int result = 1;

        Member resultEntity = memberRepository.findByMemberId(memberId);
        if(ObjectUtils.isEmpty(resultEntity)) {
            result = 0;
        }
        return result;
    }

    @Transactional(readOnly = true)
    public int chkDupliEmail(String email) {
        int result = 1;
        Member resultEntity = memberRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(resultEntity)) {
            result = 0;
        }
        return result;
    }

    public MemberDTO updateMember(MemberDTO memberDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String pwd = "";
        Member searchMember = memberRepository.findByMemberId(memberDTO.getMemberId());
        if(memberDTO.getPwd().isBlank()) {
            memberDTO.setPwd(searchMember.getPwd());
            pwd = memberDTO.getPwd();
        } else {
            pwd = bCryptPasswordEncoder.encode(memberDTO.getPwd());
        }

        searchMember.setDetailAddr(memberDTO.getDetailAddr());
        searchMember.setAddress(memberDTO.getAddress());
        searchMember.setPwd(pwd);
        searchMember.setName(memberDTO.getName());
        searchMember.setEmail(memberDTO.getEmail());
        searchMember.setTelNo(memberDTO.getTelNo());

        return modelMapper.map(searchMember, MemberDTO.class);
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
    //-------------------------------------UserDetails-------------------------------------
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String memberId) { // --> 맴버에서 가지고 오는건데
        //TODO findOneWithAuthoritiesByMemberId --> 수정예정
        return memberRepository.findOneWithAuthoritiesByMemberId(memberId)
                .map(user -> createUser(memberId, user))
                .orElseThrow(() -> new UsernameNotFoundException(memberId + " -> 데이터베이스에서 찾을 수 없습니다."));
    }
    private User createUser(String username, Member user) {
        if (!user.isActivated()) {
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");

        }

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getMemberId(),
                user.getPwd(),
                grantedAuthorities);
    }
    // -------------------------------------소셜 로그인 사용 메서드----------------------------------------------------------

    public Member socialFindMember(String email) {
        Member findDbData = memberRepository.findByEmail(email);
            return findDbData;
    }

    public Long socialSave( MemberDTO memberDTO) {

        Member member = modelMapper.map(memberDTO, Member.class);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String pwd = bCryptPasswordEncoder.encode(member.getPwd());
        member.setPwd(pwd);

        Member save = memberRepository.save(member);
        addAuthority(save.getMemberNo(), "ROLE_USER");

        MemberDTO out = modelMapper.map(save, MemberDTO.class);
        return out.getMemberNo();
    }

}//class
