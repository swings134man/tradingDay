import { useNavigate } from "react-router-dom";
import {useEffect, useRef, useState} from "react";
import axios from "axios";
import Post from "../../components/Post";
import "../../css/post.css"


function PersonInfoModi( data ) {

    const [addr, setAddr] = useState();
    const navigate = useNavigate();
    let middleNum = "";
    let backNum = "";

    const nameRef = useRef(null);
    const pwdRef = useRef(null);
    const pwdConfirmRef = useRef(null);
    const firstPhoneNum = useRef(null);
    const middlePhoneNum = useRef(null);
    const backPhoneNum = useRef(null);
    const emailRef = useRef(null);
    const searchAddr = useRef(null);
    const addrDetailRef = useRef(null);

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

    useEffect(() => {
        //주소 api검색 변경
        if(enroll_company.address === null || enroll_company.address === '') {
            setAddr(data.data.address);
        } else {
            setAddr(enroll_company.address);
        }
        if(popup === true) {
            setPopup(false);
        }
    }, [enroll_company])

    // 번호 포맷 맞춰주는 함수
    if(data.data.telNo !== null) {

        let number = data.data.telNo;
        let subMiddleNum = number.substring(4, 7);
        let subBackNum = number.substring(9);

        if(number.length === 12) {
            // 010-123-1234
            subMiddleNum = number.substring(4, 7);
            subBackNum = number.substring(8);
        } else if(number.length === 13) {
            // 010-1234-1234
            subMiddleNum = number.substring(4, 8);
            subBackNum = number.substring(9);
        }
        middleNum = subMiddleNum;
        backNum = subBackNum;
    }

    function chkSubmit(e) {
        e.preventDefault();

        const nameVal = nameRef.current.value;
        const pwdVal = pwdRef.current.value;
        const pwdConfirmVal = pwdConfirmRef.current.value;
        const emailVal = emailRef.current.value;
        const searchAddrVal = searchAddr.current.value;
        const addrDetail = addrDetailRef.current.value;
        const firstPhoneNumVal = firstPhoneNum.current.value;
        const middlePhoneNumVal = middlePhoneNum.current.value;
        const backPhoneNumVal = backPhoneNum.current.value;

        const phoneNum = firstPhoneNumVal + '-' + middlePhoneNumVal + '-' + backPhoneNumVal;

        const nameValiForm = /^[가-힣]+$/;
        const emailValiForm = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
        // --> 비밀번호는 특수문자 하나, 8글자 이상 16자 이하로 설정함.
        const pwdValiForm = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
        const phoneValiForm  = /^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$/;

        if(nameVal == "") {
            alert("이름을 입력하세요");
                return;
        } else if(!nameValiForm.test(nameVal)) {
            alert("이름은 공백이나 영문은 입력 할 수 없습니다");
            nameRef.current.focus();
                return;
        } else if (!emailValiForm.test(emailVal)) {
            alert("이메일 형식을 확인 후 다시 입력해주세요");
            emailRef.current.focus();
                return;
        } else if(!phoneValiForm.test(phoneNum) ||!middlePhoneNumVal || !backPhoneNumVal ) {
            alert("전화번호 형식을 확인 후 다시 입력해주세요.");
            return;
        } else if(!searchAddrVal) {
            alert("주소를 입력하세요.");
            return;
        } else if(!addrDetail) {
            alert("상세주소를 입력하세요.");
            return;
        }
        if(!(!pwdVal === !pwdConfirmVal)) {
            if (!pwdValiForm.test(pwdVal)) {
                alert("비밀번호는 특수문자 포함 영문 숫자 조합으로,\n8글자 이상, 16자 이하로 설정해야합니다");
                pwdRef.current.focus();
                return;
            }
            if(pwdVal !== pwdConfirmVal) {
                alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
                pwdRef.current.focus();
                return;
            }
        }

        axios.post("/member/v1//updatemember", {
            memberId: localStorage.getItem("memberId"),
            pwd: pwdVal,
            address: searchAddrVal,
            detailAddr: addrDetail,
            email: emailVal,
            name: nameVal,
            telNo: phoneNum
        },
        {
            headers: {
                AUTHORIZATION: "Bearer " + localStorage.getItem("auth_token")
            },
        },
        ).then(function (res){
            if(res.status === 403) {
                navigate('/member/signin');
            }
            console.log(res);
            alert('회원정보 수정이 완료되었습니다')
            navigate('/')
        }).catch(function (err){
            console.log(err)
        })

    } // submit end

    return(
        <div>
            <div >
                {popup === true ?
                    <div>
                        <div className="d-flex justify-content-end">
                            <button className="d-flex justify-content-end btn "
                                    style={{backgroundColor: "#217Af0", width: 70, border: "solid"}}
                                    onClick={handleComplete}>창닫기</button>
                        </div>
                        <div  style={{position: "fixed", width: 590, height: 370}}>
                            {<Post company={enroll_company} setcompany={setEnroll_company}></Post>}
                        </div>
                    </div> : null}
            </div>
            <table className="table table-striped table-bordered " >
                <tbody style={{width: '100%'}} >
                <tr>
                    <th style={{paddingTop: 20, paddingBottom: 20}}>회원아이디</th>
                    <td colSpan={3} style={{paddingTop: 20, paddingBottom: 20}}>{data.data.memberId}</td>
                </tr>
                <tr>
                    <th style={{paddingTop: 20, paddingBottom: 20}}>비밀번호</th>
                    <td  style={{paddingTop: 20, paddingBottom: 20}}>
                        <input type="password" maxLength={16} placeholder="변경시에만 입력해주세요." align="center" ref={pwdRef}/>
                    </td>
                    <td style={{paddingTop: 20, paddingBottom: 20}}>
                        <b >비밀번호 확인</b>
                    </td>
                    <td style={{paddingTop: 20, paddingBottom: 20}}>
                        <input type="password" maxLength={16} placeholder="비밀번호 확인" ref={pwdConfirmRef}/>
                    </td>
                </tr>
                <tr>
                    <th style={{paddingTop: 20, paddingBottom: 20}}>이름</th>
                    <td colSpan={3} style={{paddingTop: 20, paddingBottom: 20}}>
                        <input type="text" defaultValue={data.data.name} style={{width:200}} maxLength={10} ref={nameRef} />
                    </td>
                </tr>
                <tr>
                    <th style={{paddingTop: 20, paddingBottom: 20}}>핸드폰번호</th>
                    <td colSpan="3" style={{paddingTop: 20, paddingBottom: 20}}>
                        <input type="text" disabled={true} defaultValue="010" style={{width:50}} ref={firstPhoneNum}/> -
                        <input type="text" maxLength={4} defaultValue={middleNum} style={{width:50}} ref={middlePhoneNum}/> -
                        <input type="text" maxLength={4} defaultValue={backNum} style={{width:50}} ref={backPhoneNum}/>
                    </td>
                </tr>
                <tr>
                    <th style={{paddingTop: 20, paddingBottom: 20}}>이메일</th>
                    <td colSpan={3} style={{paddingTop: 20, paddingBottom: 20}}>
                        <input type="text" defaultValue={data.data.email} style={{width: '100%'}} ref={emailRef} maxLength={20}/>
                    </td>
                </tr>

                <tr>
                    <th style={{paddingTop: 20, paddingBottom: 20}}>주소</th>
                        <td colSpan={3} style={{paddingTop: 20, paddingBottom: 20}}>
                            <div style={{paddingBottom: 10}}>
                            <button className="btn"
                                    style={{fontSize:11,fontWeight: "bold", color: "white", backgroundColor: "#217Af0", width: 70, height: 30}}
                                    onClick={() => {
                                        handleComplete()}}
                                    onChange={handleInput}> 주소검색 </button>
                            </div>
                            <input type="text" defaultValue={addr}  style={{width: '100%'}} ref={searchAddr} maxLength={60}/>
                            <div style={{paddingTop: 10}}>
                                <input type="text" defaultValue={data.data.detailAddr} style={{width: '100%'}} ref={addrDetailRef} maxLength={40}/>
                            </div>
                        </td>
                </tr>
                </tbody>
            </table>
            <div align="right">
                <form onSubmit={chkSubmit}>
                    <button className="btn"
                            style={{fontWeight: "bold", color: "white", backgroundColor: "#217Af0", width: 100}}>
                        수정하기
                    </button>
                </form>
            </div>
        </div>
    )
}


export default PersonInfoModi;