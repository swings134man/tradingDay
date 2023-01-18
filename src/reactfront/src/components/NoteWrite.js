import {Link, useNavigate, useParams} from "react-router-dom";
import React, {useRef} from "react";
import axios from "axios";

/************
 * @info : 쪽지 발송 화면 (공통 컴포넌트)
 * @name : NoteWrite
 * @date : 2023/01/03 7:34 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 *
 * - Param : Note.JS 화면에서 Param : receiveId 받아야함.
 * - 비로그인시 튕겨냄.
 *
 ************/
function NoteWrite() {

    const navigate = useNavigate();
    const {receiveId} = useParams(); // 받는사람 ID
    const senderId = localStorage.getItem("memberId");

    //ref
    const contentRef = useRef(null);
    const titleRef = useRef(null);

    // 이벤트
    // 쪽지 취소
    function onClickReject() {
        navigate(-1);
    }

    // 쪽지 전송
    function onClickSubmit() {
        // val
        const contentVal = contentRef.current.value;
        const titleVal = titleRef.current.value;

        if(titleVal === null || titleVal === ' ') {
            alert("제목을 입력하세요.");
            return;
        }else if(contentVal === null || contentVal ===' ') {
            alert("내용을 입력하세요.");
            return;
        }

        // 쪽지 발송
        let confirm1 = window.confirm("쪽지를 보내시겠습니까?");
        if(confirm1 === false) {
            return;
        }
        // 보내는 사람 & 받는사람이 같으면 denied
        if(receiveId === senderId) {
            window.alert("본인에게 쪽지를 보낼 수 없습니다!");
            return;
        }

            // 통신
            axios.post('/note/v1/auth/sendnote', {
                receiveMemberId: receiveId, // 받는 사람
                senderMemberId: senderId, // 보내는 사람
                title: titleVal,
                content: contentVal
            }, {
                headers: {
                    AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
                }
            }).then(function (res){
                if(res.status === 200){
                    alert("쪽지를 성공적으로 전송했습니다.");
                    navigate(-2);
                }
            }).catch(function (err){
                if(err.response.status === 403){
                    navigate("/member/signin");
                }
                alert("쪽지를 전송하던 도중 문제가 발생했습니다.\n 잠시 후 다시 시도해주세요.");
            })

    }

    return (
        <div>
            <div align="center" style={{padding: 100, paddingBottom: 20}}>
                <h3>쪽지 보내기</h3>
                <table className="table table-striped table-bordered table-hover" style={{width: 1000}}>
                    <tbody>
                    <tr>
                        <th scope="col" width="10%">받는 사람</th>
                        <td>
                            {receiveId}
                        </td>
                    </tr>
                    <tr>
                        <th scope="col" width="10%">보내는 사람</th>
                        <td>
                            {senderId}
                        </td>
                    </tr>
                    <tr>
                        <th scope="col" width="10%">제목</th>
                        <td>
                            <input ref={titleRef} style={{width:800}} placeholder="제목을 입력하세요"></input>
                        </td>
                    </tr>
                    <tr>
                        <th scope="col" colSpan="2" width="10%">내용</th>
                    </tr>
                    <tr>
                        <td colSpan="2" className="view_text">
                            <textarea ref={contentRef} style={{width: 980, height: 300}} placeholder="내용을 입력하세요. (500자)"></textarea>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            {/*  버튼  */}
            <div align="center">
                <button  onClick={onClickReject} className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}}>
                    취소
                </button>
                <button onClick={onClickSubmit} className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}} >
                    보내기
                </button>
            </div>
        </div>
    )

}

export default NoteWrite;