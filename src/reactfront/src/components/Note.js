import React,{useState} from "react";
import {Link, useNavigate} from "react-router-dom";

function Note(props) {

    const navigate = useNavigate();
    const receiveId = props.memberId;

    const onClick = () => {
        /*
         본인 아이디, 비로그인시
        */
        if(localStorage.getItem("memberId") === null){
            let confirm = window.confirm("해당기능은 로그인이 필요한 기능입니다. \n로그인 하시겠습니까?");
            if(confirm) {
                navigate("/member/signin");
                return;
            }else {
                return;
            }
        }
        if(receiveId === localStorage.getItem("memberId")) {
            alert("본인에게 쪽지를 전송 할 수 없습니다.");
            return;
        }
        navigate('/noteWrite/'+receiveId);
    }


    return (
        <div style={{position:"absolute", border: "solid", backgroundColor: "#000000"	, opacity: 0.7}}>
              <button onClick={onClick} style={{backgroundColor: "#000000" , opacity: 0.7}}>
                    <li style={{listStyle: "none", paddingLeft: 0, color: "white"}}>쪽지 보내기</li>
              </button>
        </div>
    )
}

export default Note;