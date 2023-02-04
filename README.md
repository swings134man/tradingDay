# TradingDay

## 목차
<ul style="list-style-type: circle;" data-ke-list-type="circle">
<li><a href="#purose">프로젝트 소개</a></li>
<li><a href="#info">프로젝트 Info & Skills</a></li>
<li><a href="#feature">프로젝트 기능</a></li>
<li><a href="#test">통합 테스트 목록</a></li>
<li><a href="#5"></a></li>
</ul>


<h2 id="purose">Project Introduce - 프로젝트 소개</h2>
Trading Day for side Project <br/>

* 프로젝트 명  : Trading Day
* 프로젝트 소개 : 다양한 프로젝트 및 모임 참가자들을 찾고 매칭해주는 웹사이트.
* 프로젝트 인원  : 2명 (BE, FE, Infra) @swings134man, @caporatang
* 프로젝트 기간  : 2022.10 ~ 2023.01
<br/>

--------------------------------------------------------------------------------
<h2 id="info">Project Info & Skills</h2>

* Language : Java 11
* BE : Spring Boot <br/>
* FE : React <br/>
* DB : H2(Local), MySQL <br/>
* 형상관리 : GitHub, Git(Bash)

#### Library 
- Spring Data JPA 
- Spring Security 
- Spring Batch 
- Lombok
- Swagger (API 문서 관리)
- Model Mapper (Entity <-> DTO Mapping)
- BootStrap5
<br/>

#### Open API
- Kakao Login
- Google Login
- react-daum-postcode (kakao 주소검색)
<br/>

#### 배포 info
- AWS EC2
- AWS RDS
<br/>

--------------------------------------------------------------------------------
<h2 id="feature">Site Info - 기능</h2>

* Trade : 다양한 프로젝트 구인글 작성 및 지원자의 지원
1. Login(인증)된 회원에 한정하여 게시글 작성, 쪽지 보내기, 지원서 발송.
2. 지원서 작성 시 게시글 작성자에게 해당 정보 Email 전송 및 쪽지 전송.
<br/>

* QnA : 웹사이트 이용자들의 다양한 문의 사항(CS) 처리 게시판.
1. 게시글 비밀번호를 통하여 작성자본인, CS 담당자 열람 가능
<br/>

* MyPage
1. 쪽지함 기능을 이용하여 회원에게 온 쪽지 열람 및 답장 기능.
2. 회원정보 수정 및 탈퇴. 

* Admin
1. 어드민권한을 가졌을때, ADMIN페이지 노출
2. 매니저 계정 생성
3. 쪽지함 기능을 이용하여 회원에게 온 쪽지 열람 및 답장 기능.

* Sign Up
1. 이용자 사이트 회원가입

* Log In
1. Google 소셜 로그인 (기존 가입 내역 없는 경우 소셜 회원가입으로 이동)
2. 카카오 소셜 로그인 (기존 가입 내역 없는 경우 소셜 회원가입으로 이동)
3. 일반 이용자 로그인

<h2 id="test">통합테스트 목록</h2>
