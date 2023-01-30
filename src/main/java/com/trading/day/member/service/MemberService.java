package com.trading.day.member.service;

import com.trading.day.member.domain.*;
import com.trading.day.member.repository.MemberJpaRepository;
import com.trading.day.member.repository.RoleJpaRepository;
import com.trading.day.member.repository.UserRoleJpaRepository;
import com.trading.day.qna.answer.repository.AnswerRepository;
import com.trading.day.qna.answer.service.AnswerService;
import com.trading.day.qna.repository.QnaRepository;
import com.trading.day.qna.service.QnaService;
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

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService implements UserDetailsService{

    private final MemberJpaRepository memberRepository; // MEMBER
    private final UserRoleJpaRepository urRepository; // User_Role
    private final ModelMapper modelMapper; // DTO <-> Entity 변환 라이브러리

    /**
     * methodName : save
     * author : TAEIL KIM
     * description : 일반회원가입
     *
     * @return Long
     */
    public Long save(MemberDTO inDto) {

        //Entity 변환 작업
        Member member = modelMapper.map(inDto, Member.class);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String pwd = bCryptPasswordEncoder.encode(member.getPwd());
        member.setPwd(pwd);

        Member save = memberRepository.save(member);
        addAuthority(save.getMemberNo(), "ROLE_USER");
        MemberDTO out = modelMapper.map(save, MemberDTO.class);
        return out.getMemberNo();
    }

    /**
     * methodName : addAuthority
     * author : TAEIL KIM
     * description : 유저 권한 부여
     *
     * @return void
     */
    public void addAuthority(Long userId, String authority) {
        memberRepository.findById(userId).ifPresent(user -> {
            UserRole newRole = new UserRole(user.getMemberNo(), authority);
            newRole.setCreatedDate(LocalDateTime.now());
            newRole.setModifiedDate(LocalDateTime.now());
            urRepository.save(newRole);
        });
    }

    /**
     * methodName : manageSave
     * author : TAEIL KIM
     * description : 매지저 권한 부여
     *
     * @return Long
     */
    public Long manageSave(MemberDTO memberDTO) { // -> admin 가입시키면 --> 매니저
        Member member = modelMapper.map(memberDTO, Member.class);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String pwd = bCryptPasswordEncoder.encode(member.getPwd());
        member.setPwd(pwd);
        Member save = memberRepository.save(member);

        addAuthority(save.getMemberNo(), "ROLE_MANAGER");
        MemberDTO result = modelMapper.map(save, MemberDTO.class);
        return result.getMemberNo();

    }

    /**
     * methodName : memberModiPwdChk
     * author : TAEIL KIM
     * description : 개인정보 수정 페이지 진입시 사용자 비밀번호 확인
     *
     * @return boolean
     */
    @Transactional(readOnly = true)
    public boolean memberModiPwdChk(MemberDTO memberDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Member searchRslt = memberRepository.findByMemberId(memberDTO.getMemberId());
        String dbPwd = searchRslt.getPwd();
        String dtoPwd = memberDTO.getPwd();

        return bCryptPasswordEncoder.matches(dtoPwd, dbPwd);
    }

    // testOnly !
    public Member save2(Member member) {
        return memberRepository.save(member);
    }

    /**
     * methodName : findAll
     * author : TAEIL KIM
     * description : 조건에 상관없이 모든 고객을 가져옴
     *
     * @return List<Member>
     */
    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll(Sort.by(Sort.Direction.DESC, "memberNo"));
    }

    /**
     * methodName : deleteMember
     * author : TAEIL KIM
     * description :
     *
     * @return int
     */
    @Transactional
    public int deleteMember(MemberDTO memberDTO) {
        int result = 1;
        // 가지고온 아이디로 맴버 테이블 검색
        Member dbMember = memberRepository.findByMemberId(memberDTO.getMemberId());

        if(ObjectUtils.isEmpty(dbMember)) {
            // 맴버 테이블에 조회된 결과가 없는 경우
            result = 0;
            return result;
        }
        
        // 유저권한 테이블 삭제
        urRepository.deleteByMemberNo(dbMember.getMemberNo());
        // 회원 데이터 삭제
        memberRepository.delete(dbMember);

        return result;
    }

    /**
     * methodName : findById
     * author : TAEIL KIM
     * description :
     *
     * @return MemberDTO
     */
    @Transactional(readOnly = true)
    public MemberDTO findById(MemberDTO inDto) {
        Optional<Member> result = memberRepository.findById(inDto.getMemberNo());
        // to DTO
        MemberDTO out = modelMapper.map(result.get(), MemberDTO.class);
        return out;
    }

    /**
     * methodName : findByMemberId
     * author : TAEIL KIM
     * description :
     *
     * @return MemberDTO
     */
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

    /**
     * methodName : chkDupliEmail
     * author : TAEIL KIM
     * description :
     *
     * @return 이메일 중복확인
     */
    @Transactional(readOnly = true)
    public int chkDupliEmail(String email) {
        int result = 1;
        Member resultEntity = memberRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(resultEntity)) {
            result = 0;
        }
        return result;
    }

    /**
     * methodName : updateMember
     * author : TAEIL KIM
     * description :
     *
     * @return memberDTO
     */
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

    /**
     * methodName : findByName
     * author : TAEIL KIM
     * description :
     *
     * @return memberDTO
     */
    public MemberDTO findByName(MemberDTO inDto) {

        // Entity
        Member member = modelMapper.map(inDto, Member.class);
        Member result = memberRepository.findByName(member.getName());

        // DTO
        MemberDTO out = modelMapper.map(result, MemberDTO.class);
        return out;
    }


    /**
     * methodName : searchActivated
     * author : TAEIL KIM
     * description : 계정 활성화 여부를 조회함
     * date : 2023/1/30
     * @return boolean
     */
    public boolean searchActivated(String memberId) {
        boolean activated = true;
        Member dbMember = memberRepository.findByMemberId(memberId);

        if(!ObjectUtils.isEmpty(dbMember)) {
            activated = dbMember.isActivated();
        }

        return activated;
    }

    /**
     * methodName : saveLastLoginTime
     * author : TAEIL KIM
     * description : 로그인 시간을 저장함
     * date : 2023/1/21
     * @return void
     */
    public void saveLastLoginTime(String memberId) {
        memberRepository.saveLoginTime(memberId);
        log.debug("--------------- 로그인 시간 업데이트 -----------");
    }
    //-------------------------------------UserDetails-------------------------------------
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String memberId) { // --> 맴버에서 가지고 오는건데
        return memberRepository.findOneByMemberId(memberId)
                .map(user -> createUserDetail(memberId, user))
                .orElseThrow(() -> new UsernameNotFoundException(memberId + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    /**
     * methodName : createUserDetail
     * author : TAEIL KIM
     * description : 계정 활성 체크와, 유저 디테일 리턴하는 메서드
     *
     * @return User
     */
    private User createUserDetail(String username, Member user) {
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
    /**
     * methodName : socialFindMember
     * author : TAEIL KIM
     * description :
     *
     * @return Member
     */
    public Member socialFindMember(String email) {
        Member findDbData = memberRepository.findByEmail(email);
            return findDbData;
    }

    /**
     * methodName : socialSave
     * author : TAEIL KIM
     * description :
     *
     * @return Long
     */
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
