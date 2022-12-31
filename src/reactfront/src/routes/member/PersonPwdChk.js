import {useEffect, useRef, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import MyPage from "./MyPage";


function PersonPwdChk() {
    const pwdRef = useRef(null);
    const navigate = useNavigate();
    const [pwdChkSuccess, setPwdChkSuccess] = useState(false);


    const myPageProps = (res: boolean) => {
        console.log('res', res)
        if(res === true) {
            <MyPage pwdChk={res} />
        }
    }


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
            if(res.data === true) {
                myPageProps(res.data);
            }
        }).catch(function (err){
            myPageProps(err.data)
            alert('비밀번호를 확인 후 다시 입력해주세요.');
        })
    }


    return (

            <div align="center" style={{ marginLeft: 400}}>
                <h1>비밀번호 확인</h1>
                    <br />
                <form onSubmit={memberPwdChk}>
                    <input type="password" ref={pwdRef} placeholder="비밀번호를 입력하세요" className="size" maxLength={12} style={{border: 30 }} autoFocus/>
                    <br />
                    <br />
                        <button className="btn btn-warning"  style={{backgroundColor: "#217Af0", color: "white"}}> 비밀번호 확인하기 </button>
                    <br />
                </form>
            </div>
    )
}

export default PersonPwdChk;