import {useEffect, useRef, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";


function QnaPwdChk() {
    const {qnaId} = useParams();
    const pwdRef = useRef(null);
    const navigate = useNavigate();

    const chkPwd = async () => {
        const pwd = pwdRef.current.value;
        try {

            const inputPattern = /^\s+|\s+$/g;
            if(pwd.replace(inputPattern, '' ) === "" ) {
                alert('비밀번호엔 공백만 입력할수없어요.');
                return;
            }

            const chk = await axios.get(`/qna/v1/confirmpwd`, {
                params: { qnaId, pwd }
            });

            if(chk.data === 1) {
                navigate(`/qnaDetail/${qnaId}`);
            } else if (chk.data === 0) {

                alert("비밀번호가 일치하지 않습니다.");
            }

        } catch (err) {
            console.log("비밀번호 확인중에 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        };
    }

    return (
        <div align="center" style={{padding : 100}}>
        <h1>문의 비밀번호 확인</h1>
            <br />
            <input type="password" ref={pwdRef} placeholder="비밀번호를 입력하세요 (최대 12글자)" className="size" maxLength={12} style={{border: 30 }} autoFocus/>
            <br />
            <br />
            <button className="btn btn-warning" onClick={chkPwd} style={{backgroundColor: "#217Af0", color: "white"}}> 비밀번호 확인하기 </button>
            <br />

        </div>
    )
}

export default QnaPwdChk;