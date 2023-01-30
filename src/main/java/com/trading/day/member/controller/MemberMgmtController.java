package com.trading.day.member.controller;

import com.trading.day.member.domain.Member;
import com.trading.day.member.domain.MemberDTO;
import com.trading.day.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/************
* @info : 고객관리 컨트롤러
* @name : MemberMgmtController
* @date : 2022/10/11 9:14 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/member/v1")
public class MemberMgmtController {

    private final MemberService memberService;

    /**
    * methodName : memberModiPwdChk
    * author : TAEIL KIM
    * description :
    *
    * @return boolean
    */
    @PostMapping("/pwdchk")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ApiOperation(value ="고객 정보 수정시 비밀번호 확인 API", notes ="고객 정보 수정시 비밀번호 확인함")
    public boolean memberModiPwdChk(@RequestBody MemberDTO memberDTO) {
        return memberService.memberModiPwdChk(memberDTO);
    }

    /**
    * methodName : findAll
    * author : TAEIL KIM
    * description :
    *
    * @return list
    */
    @ApiOperation(value ="고객 전체 조회", notes ="조건에 상관 없이 가입되어있는 모두를 조회함")
    @GetMapping("/memberlist")
    public List<Member> findAll() {
        return memberService.findAll();
    }

    /**
    * methodName : save
    * author : TAEIL KIM
    * description :
    *
    * @return Long
    */
    @PostMapping("/save")
    @PreAuthorize("isAnonymous()")
    @ApiOperation(value =  "회원 가입 api", notes = "회원 가입 처리함")
    public Long save(@RequestBody MemberDTO memberDTO) {
        Long result = memberService.save(memberDTO);
        return result;
    }

    /**
    * methodName : saveManage
    * author : TAEIL KIM
    * description :
    *
    * @return Long
    */
    @PostMapping("/savemanage")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "admin권한을 가진 계정으로 매니저권한을 가진 계정을 생성 api", notes = "admin권한을 가진 계정으로 매니저권한을 가진 계정을 생성함")
    public Long manageSave(@RequestBody MemberDTO memberDTO) {
        Long result = memberService.manageSave(memberDTO);
        return result;
    }

    /**
    * methodName : deleteMember
    * author : TAEIL KIM
    * description :
    *
    * @return int
    */
    @DeleteMapping("/memberdelete")
    @ApiOperation(value =  "회원 탈퇴 api", notes = "회원 탈퇴 처리함")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public int deleteMember(@RequestBody MemberDTO memberDTO) {
        int result = memberService.deleteMember(memberDTO);
        return result;
    }

    /**
     * methodName : searchActivated
     * author : TAEIL KIM
     * description : 계정 활성화 여부를 조회함
     * date : 2023/1/30
     * @return boolean
     */
    @GetMapping("/searchactivated")
    @ApiOperation(value = "계정 활성화 여부 조회 api", notes = "계정 활성화 여부를 조회함")
    @PreAuthorize("isAnonymous()")
    public boolean searchActivated(@RequestParam String memberId) {
       return memberService.searchActivated(memberId);
    }

    /**
    * methodName : findById
    * author : TAEIL KIM
    * description :
    *
    * @return MemberDTO
    */
    @GetMapping("/findbyid")
    @ApiOperation(value =  "pk로 해당 고객 정보조회 api", notes = "단건 회원 정보 조회")
    //ID로 회원정보 검색 (PK)
    public MemberDTO findById (@RequestParam Long id) {
        MemberDTO inDto = MemberDTO.builder()
                .memberNo(id)
                .build();
        MemberDTO result = memberService.findById(inDto);
        return result;
    }

    /**
    * methodName : updateMember
    * author : TAEIL KIM
    * description :
    *
    * @param memberDTO
    */
    @ApiOperation(value = "회원정보 수정 api", notes = "회원 정보를 수정함")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/updatemember")
    public MemberDTO updateMember(@RequestBody MemberDTO memberDTO) {
        return memberService.updateMember(memberDTO);
    }

    /**
    * methodName : findByName
    * author : TAEIL KIM
    * description :
    *
    * @param memberDTO
    */
    @GetMapping("/member/v1/findbyname")
    public MemberDTO findByName(@RequestParam String name) {
        MemberDTO inDto = MemberDTO.builder()
                .name(name)
                .build();

        MemberDTO result = memberService.findByName(inDto);

        return result;
    }
    /**
    * methodName : findByMemberId
    * author : TAEIL KIM
    * description :
    *
    * @param memberDTO
    */
    @GetMapping("/findbymemberid")
    @PreAuthorize("hasRole('ROLE_USER')")
    public MemberDTO findByMemberId(@RequestParam String memberId) {
        MemberDTO inDTO = MemberDTO.builder()
                .memberId(memberId)
                .build();
        MemberDTO result = memberService.findByMemberId(inDTO);
        return result;
    }


    /**
    * methodName : chkdupliId
    * author : TAEIL KIM
    * description : 회원가입시 사용되는 아이디 중복확인 api
    *
    * @return int
    */
    @PreAuthorize("isAnonymous()")
    @GetMapping("/chkdupliId")
    @ApiOperation(value =  "회원가입시 사용되는 아이디 중복확인 api", notes = "아이디 중복을 체크함")
    public int chkDupliId(@RequestParam String memberId) {
        return memberService.chkDupliId(memberId);
    }


    /**
    * methodName : chkdupliEmail
    * author : TAEIL KIM
    * description :
    *
    * @return int
    */
    @PreAuthorize("isAnonymous()")
    @GetMapping("/chkdupliemail")
    @ApiOperation(value="회원가입시 사용되는 이메일 중복확인 api", notes = "이메일 중복 체크함")
    public int chkDupliEmail(@RequestParam String email) {
        return memberService.chkDupliEmail(email);
    }

// -----------------소셜-------------------------------
    /**
     * methodName : socialsave
     * author : TAEIL KIM
     * description :
     *
     * @return Long
     */
    @PostMapping("/socialsave")
    @PreAuthorize("isAnonymous()")
    @ApiOperation(value = "소셜로 접근한 회원가입 api", notes = "소셜로 접근한 회원가입 처리함")
    public Long socialSave(@RequestBody MemberDTO memberDTO) {
        Long result = memberService.socialSave(memberDTO);
        return result;
    }



}//class
