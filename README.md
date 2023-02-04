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
<br/>
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
<br/>
<h2 id="feature">Site Info - 기능</h2>

- Main 화면
<img src=“” width=“” height=“”/>
<img width="1778" alt="스크린샷 2023-02-05 오전 2 36 36" src="https://user-images.githubusercontent.com/86291550/216781650-2d23a619-8bbe-4e50-a8a6-bff9f58a1070.png">


- 회원가입, 로그인
<img width="502" alt="스크린샷 2023-02-05 오전 2 39 08" src="https://user-images.githubusercontent.com/86291550/216781655-8998dd3b-b943-4997-a537-c7ce57c67110.png">

<img width="892" alt="스크린샷 2023-02-05 오전 2 39 18" src="https://user-images.githubusercontent.com/86291550/216781660-cbf0207e-f8b6-4965-ab3d-62e188abc692.png">


<br/>
<br/>
<h2 id="test">통합테스트 목록</h2>

- 회원 접근 제한 (로그인 후 사용가능): QnA, 지원서 작성, 게시물 작성, 댓글 작성
-> 명시된 기능은 로그인 한(인증된) 회원만 이용가능, 권한 없을 시 -> 로그인 화면으로 이동. 

- Main 화면.
1. 각 버튼, 이미지 클릭 확인. 

- 회원가입 & 로그인 - Member
1. 회원가입 - 사이트 자체 기능.
2. 회원가입시 각 항목별 검증처리 - 아이디, 패스워드, 이메일, 전화번호, 주소
3. 로그인 3가지 - 사이트 로그인, 소셜 로그인(카카오 로그인, 구글 로그인).
4. 일정시간 이후 JWT TOKEN 재발급 여부.
5. MyPage 내부 쪽지함, 지원서 List 확인.
6. MyPage 내부 회원 정보 수정 및 회원 탈퇴 확인.
7. MyPage 내부 ADMIN Only 기능 확인 -> 매니저 계정 생성.
8. 비활성화 계정 로그인 시도. 


- TRADE 게시판 - Trade 
1. 비회원 게시글 조회 확인.
2. 비회원일시 게시글 작성, 댓글 작성 확인. 
3. 로그인 후 게시글 작성, 작성 게시물에 대해 수정, 삭제 확인 및 '모집상태' 변경 확인. (게시물 작성자, 관리자 Only)
4. 다른 사람 게시물에 지원하기 기능 이용. -> 지원 후 게시물 작성자 MyPage 이동 후 지원서 List 확인
5. 게시물 내부 댓글 등록, 수정, 삭제 테스트.(작성자, 관리자 Only)
6. TRADE 게시판 리스트 - 모집 상태 색깔별 확인.

- 지원서 - Apply 
1. TRADE 게시판에서 지원서 제출. 
2. 게시글 작성자 MyPage 내부 -> 지원서 리스트 확인. 
3. 지원서 상세 페이지 이동 -> 수락, 거절 기능별 이메일 발송 확인. 
4. 답변 후, 지원서 목록 답변 상태 확인. 

- 쪽지 - Note
1. TRADE 게시판 이동 -> 게시물 작성자 ID 클릭.
2. 쪽지 보내기 버튼 클릭 후 -> 내용 작성 및 발송. 
3. 수신자 MyPage 이동 후 쪽지 확인 및 답장. 
4. 발신자 MyPage 이동 후 쪽지 확인.
5. 쪽지 상세 페이지 내부 삭제 확인. 
6. 쪽지 List 선택 항목 삭제 확인. 

- Qna 
1. 로그인 회원 only 글작성, 수정, 삭제 확인.
2. Qna 글 클릭시 비밀번호 입력 후 페이지 진입 확인. 
3. Qna 리스트 - 로그인한 ID 가 작성한 글만 버튼 기능 확인. 
4. 관리자의 답글, 수정, 삭제 확인. 

- Batch 
1. 마지막 로그인 2주 이상된, 계정 TOKEN_MANAGE Table row 삭제. 매월, 매주 일요일 00:00 시작.
2. 1년 이상 비로그인 계정, 비활성화 - Member Table ACTIVE = FALSE -> 매월, 매주, 수~금요일 00:00 시작.

