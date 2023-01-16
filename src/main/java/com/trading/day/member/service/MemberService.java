package com.trading.day.member.service;

import com.trading.day.member.domain.*;
import com.trading.day.member.repository.MemberJpaRepository;
import com.trading.day.member.repository.RoleJpaRepository;
import com.trading.day.member.repository.UserRoleJpaRepository;
import com.trading.day.qna.answer.domain.Answer;
import com.trading.day.qna.answer.repository.AnswerRepository;
import com.trading.day.qna.answer.service.AnswerService;
import com.trading.day.qna.domain.Qna;
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
    private final RoleJpaRepository roleRepository; // Role
    private final AnswerRepository answerRepository; // 문의답변 repo
    private final QnaRepository qnaRepository; // 문의 repo
    private final ModelMapper modelMapper; // DTO <-> Entity 변환 라이브러리
    private final QnaService qnaService;
    private final AnswerService answerService;

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
            result = 0;
            return result;
        }
        // 작성자명으로 qna 테이블을 조회
        List<Qna> qnaList = qnaService.findByWriter(dbMember.getMemberId());

        //answer, qna테이블 삭제
        if(!ObjectUtils.isEmpty(qnaList)) {
            for(int i = 0; i < qnaList.size(); i++) {
                Long qnaId = qnaList.get(i).getQnaId();
                Long answerId = answerService.findByQnaId(qnaId);

                answerRepository.deleteByAnswerId(answerId);
                qnaRepository.deleteByWriter(qnaList.get(i).getWriter());
            }
        }
        // 권한, 유저권한 테이블 삭제
        urRepository.deleteByMemberNo(dbMember.getMemberNo());
        memberRepository.deleteByMemberNo(dbMember.getMemberNo());

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
