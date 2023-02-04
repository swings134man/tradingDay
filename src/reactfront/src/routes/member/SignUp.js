import style from "../../css/signUpStyle.css";
import {useState} from "react";
import Post from "components/Post";
import {useRef} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";


function SignUp() {
    const [dupli, setDupli] = useState(0); // -> id 중복체크 스테이트
    const [disabled, setDisabled] = useState(false);
    const [emailDisabled, setEmailDisabled] = useState(false);
    const navigate = useNavigate();

    const nameRef =useRef(null);
    const idRef = useRef(null);
    const pwdRef = useRef(null);
    const pwdConfirmRef = useRef(null);
    const phoneNumRef = useRef(null);
    const phoneSelect = useRef(null);
    const emailRef = useRef(null);
    const searchAddr = useRef(null);
    const addrDetailRef = useRef(null);

    const emailDupliChk = async () => {
        const emailValiForm = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
        const emailVal = emailRef.current.value;

        await axios.get('/member/v1/chkdupliemail', {
            params: {
                email: emailRef.current.value
            }
        }).then(function (res) {
            if (!emailRef.current.value) {
                alert("이메일을 입력하세요")
            } else if (!emailValiForm.test(emailVal)) {
                alert("이메일 형식을 확인 후 다시 입력해주세요");
                emailRef.current.focus();
            } else if(res.data === 1) {
                alert("이미 가입되어 있는 이메일입니다.")
                emailRef.current.focus();
            } else if(res.data === 0) {
                const useCfn = window.confirm("사용 가능한 이메일입니다. 사용 하시겠어요?")
                if(useCfn === true) {
                    setEmailDisabled(true);
                    console.log(emailDisabled);
                }
            }
        }).catch(function (err) {
           console.log(err);
        })
    }

    const dupliIdChk = async (e) => {

        // id 중복체크 온클릭 이벤트
        const idChkVal = idRef.current.value;

        if(idChkVal.search(/\W|\s/g) > -1 ) {
            idRef.current.focus();
            alert("아이디에는 공백이나 특수문자가 들어갈수없어요.");
            return;
        } else if (idChkVal == "") {
            alert("아이디를 입력하세요.");
            idRef.current.focus();
            return;
        } else if (idChkVal.length < 4 || idChkVal.length > 12) {
            alert('아이디는 4글자이상 12자 이하로 입력해주세요');
            return;
        }

        const uri = `/member/v1/chkdupliId?memberId=${idChkVal}`;
        const encoded = encodeURI(uri);
        const json = await (
            await fetch(encoded)
        ).json();

        //최종 저장시 분기처리 값
        setDupli(json);

        if (json == 1) {
            alert("이미 사용중인 아이디입니다.")
            idRef.current.focus();
            return;
        } else if (json == 0) {
            const useCfn = window.confirm("사용 가능한 아이디입니다 사용 하시겠어요?")
            if(useCfn === true) {
                setDisabled(true);
                console.log(disabled);
            }
        } else {
            alert("중복확인 중 오류가 발생했어요. 잠시 후 다시 시도해주세요.")
        }
    }



    // 최종 회원가입 버튼에 대한 이벤트
    function onSubmit(e) {
        const nameValiForm = /^[가-힣]+$/;
        // --> 비밀번호는 특수문자 하나, 8글자 이상 16자 이하로 설정함.
        const pwdValiForm = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
        const phoneValiForm = /^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$/;

        e.preventDefault();

        const idVal = idRef.current.value;
        const nameVal = nameRef.current.value;
        const pwdVal = pwdRef.current.value;
        const pwdConfirmVal = pwdConfirmRef.current.value;
        const phoneVal = phoneNumRef.current.value;
        const phoneSelectVal = phoneSelect.current.value;
        const emailVal = emailRef.current.value;
        const searchAddrVal = searchAddr.current.value;
        const addrDetail = addrDetailRef.current.value;

        if (nameVal == "") {
            alert("이름을 입력하세요");
            nameRef.current.focus();
                return;
        } else if (!nameValiForm.test(nameVal)) {
            alert("이름은 한글만 입력할수있어요");
            nameRef.current.focus();
                return;
        }  else if (pwdVal !== pwdConfirmVal) {
            alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            pwdRef.current.focus();
            return;
        } else if (!pwdValiForm.test(pwdVal)) {
            alert("비밀번호는 특수문자 포함 영문 숫자 조합으로,\n8글자 이상, 16자 이하로 설정해야합니다");
            pwdRef.current.focus();
            return;
        } else if(phoneSelectVal === "선택") {
            alert("통신사를 선택하세요");
            return;
        } else if (!phoneValiForm.test(phoneVal)) {
            alert("전화번호 형식을 확인 후 다시 입력해주세요.");
            return;
        } else if (emailDisabled === false) {
            alert("이메일 중복체크 해야합니다.");
            return;
        } else if (searchAddrVal  === "") {
            alert("주소를 입력하세요.");
            return;
        } else if (addrDetail === "") {
            alert("상세 주소를 입력하세요");
            return;
        } else if (disabled === false) {
            alert("아이디 중복체크 해야합니다.");
            return;
        }
        axios.post("/member/v1/save", {
            memberId: idVal,
            name: nameVal,
            pwd: pwdVal,
            email: emailVal,
            telNo: phoneVal,
            address: searchAddrVal,
            detailAddr: addrDetail
        })
            .then(function (response) {
                console.log('res : ' + response.status);
                window.alert("welcome trade!!\n가입이 완료 되었어요!");
                navigate(`/`)
            }).catch(function (error) {
                // 오류발생시 실행
                console.log('error message : '+ error);
                window.alert("회원가입에 실패했어요 잠시 후 다시 시도해주세요.");
        }); // axios
    } // onSubmit end

    //--------------------------------주소검색 api----------------------
    const [enroll_company, setEnroll_company] = useState({address:'', });
    const [popup, setPopup] = useState(false);
    const handleInput = (e) => {
        setEnroll_company({
            ...enroll_company,
            [e.target.name]:e.target.value,
        })
    }
    const handleComplete = (data) => {
        setPopup(!popup);
    }
    //--------------------------------주소검색 api----------------------

    return (
        <div>
            <div id="con">
                <div id="login">
                    <div id="login_form" style={{paddingBottom:50}}>
                            <h3 className="login" style={{letterSpacing: -1}}>Welcome Trade :)</h3>
                        <br/>
                        <div className="d-flex justify-content-start">
                            <label >
                                <div style={{display: "flex", padding: 5}}>
                                    <p style={{textAlign: "left", fontSize: 12, color:"#666", paddingRight: 5}}>user id</p>
                                </div>
                            </label>
                                <div>
                                    <button className="btn"
                                            style={{fontSize: 12,backgroundColor: "#217Af0", width: 50, height:10, paddingBottom: 21, color: "white"}}
                                            onClick={dupliIdChk}> 중복 </button>
                                </div>
                        </div>
                        <input type="text"
                               placeholder="아이디"
                               className="size"
                               style={{marginBottom: 15}}
                               ref={idRef}
                               disabled={disabled === true} />
                        <div>
                            <label>
                                <p style={{textAlign: "left", fontSize: 12, color:"#666"}}>user name</p>
                                <input type="text" placeholder="이름" className="size" style={{marginBottom: 15}} ref={nameRef}/>
                            </label>
                            <br />

                            <label>
                                <p style={{textAlign: "left", fontSize: 12, color:"#666"}}>Password </p>
                                <input type="password" placeholder="특수문자 포함, 8글자 이상 16자 이하" className="size" ref={pwdRef}/>
                        </label>
                            <br />
                            <label>
                                <p style={{textAlign: "left", fontSize: 12, color:"#666"}}></p>
                                <input type="password" placeholder="비밀번호 확인" className="size" ref={pwdConfirmRef}/>
                            </label>
                            <br />
                            <label >
                                <p style={{textAlign: "left", fontSize: 12, color:"#666", paddingTop: 15}}>Mobile Phone</p>
                                <select className="size num1" ref={phoneSelect} >
                                    <option defaultValue="1" >선택</option>
                                    <option defaultValue="2" >SKT</option>
                                    <option defaultValue="3" >KT</option>
                                    <option defaultValue="4" >LG</option>
                                </select>
                                <input placeholder="00*-000*-0000" className="size num2" ref={phoneNumRef} maxLength={13}/>
                            </label>

                            <br/>
                            <div>
                                <div className="d-flex justify-content-start" style={{paddingTop: 30}}>
                                    <label >
                                            <p style={{textAlign: "left", fontSize: 12, color:"#666", paddingTop: -20, paddingRight:5}}>E-mail</p>
                                    </label>
                                        <div>
                                            <button
                                                type="button"
                                                className="btn"
                                                onClick={emailDupliChk}
                                                style={{fontSize: 12,
                                                        backgroundColor: "#217Af0",
                                                        width: 50,
                                                        height:10,
                                                        paddingBottom: 21,
                                                        color: "white",
                                                        }}> 중복 </button>
                                        </div>
                                    </div>
                                <div >
                                    <input type="text"
                                           placeholder="이메일"
                                           className="size"
                                           ref={emailRef}
                                           disabled={emailDisabled === true}
                                        />
                                </div>
                            </div>
                            <br />

                            <div className="d-flex justify-content-start" style={{height:60}}>
                                <label>
                                    {popup && <Post company={enroll_company} setcompany={setEnroll_company}></Post>}
                                    <br />
                                        <div >
                                            <p style={{ fontSize: 12, color:"#666", paddingRight: 8, paddingTop:5}} align="center">주소</p>
                                        </div>
                                </label>
                                <div style={{paddingTop: 20}}>
                                    <button type="button"
                                            className="btn"
                                            style={{fontSize:11,fontWeight: "bold", color: "white", backgroundColor: "#217Af0", width: 70, height: 30}}
                                            onClick={() => {
                                                handleComplete()}}
                                            onChange={handleInput}> 주소검색 </button>
                                </div>
                            </div>
                            <div>
                                <input type="text"
                                       placeholder="주소를 검색해주세요"
                                       className="size"
                                       defaultValue={enroll_company.address}
                                       ref={searchAddr}/>
                            </div>
                            <br />
                                <div style={{marginTop: -8, paddingBottom: -100}} >
                                    <input type="text" placeholder="상세주소" className="size" ref={addrDetailRef}/>
                                </div>
                            <br />
                            <br />
                        </div>
                        <form onSubmit={onSubmit}>
                            <p style={{marginTop: -15}}>
                                <input type="submit" value="가입!" className="btn" style={{backgroundColor: "#217Af0", color: "white"}}/>
                            </p>
                        </form>

                        <hr/>
                        <p className="find">
                            <span><a href="/member/signin">로그인 페이지로 이동</a></span>
                        </p>
                    </div>
                </div>
            </div>

        </div>
                )}
        export default SignUp;