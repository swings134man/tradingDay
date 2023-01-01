import {useEffect, useRef, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import MyPage from "./MyPage";
import PersonInfoModi from "./PersonInfoModi";


function PersonPwdChk() {
    const pwdRef = useRef(null);
    const navigate = useNavigate();
    const [pwdSuccess, setPwdSuccess] = useState(false);
    console.log(pwdSuccess)

    const memberPwdChk = (e) =>  {
        e.preventDefault();
        const pwdVal = pwdRef.current.value;
        if (pwdVal === "") {
            alert("비밀번호를 입력하세요");
            pwdRef.current.focus();
            return;
        }

        axios.post('/member/v1/pwdchk', {
                memberId : localStorage.getItem("memberId"),
                pwd : pwdVal
            },
            {
              headers : {
                  AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
              }
            },
            {withCredentials:true}
        ).then(function (res) {
            console.log(res)
            setPwdSuccess(res.data);
        }).catch(function (err){
            setPwdSuccess(false);
            // setPwdSuccess(err.data);
            alert('비밀번호를 확인 후 다시 입력해주세요.');
        })
    }
    return (
        <div>
        {pwdSuccess === false ? <div align="center" style={{ marginLeft: 400}}>
            <h1>비밀번호 확인</h1>
            <br />
            <form onSubmit={memberPwdChk}>
                <input type="password"
                       ref={pwdRef}
                       placeholder="비밀번호를 입력하세요"
                       className="size"
                       maxLength={16}
                       style={{border: 30 }}
                       autoFocus />
                <br />
                <br />
                <button className="btn btn-warning"  style={{backgroundColor: "#217Af0", color: "white"}}> 비밀번호 확인하기 </button>
                <br />
            </form>
            </div>
             : <PersonInfoModi/> }
        </div>

    )
}

export default PersonPwdChk;