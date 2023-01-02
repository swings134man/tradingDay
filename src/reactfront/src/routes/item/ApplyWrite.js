import React,{useRef, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";

function ApplyWrite() {
    // param : boardID, writer(String : server 에서 Long 변환.)
    const {id, writer} = useParams(); //param
    const titleRef = useRef(null);
    const contentRef = useRef(null);
    const writerEmailRef = useRef(null);
    const applyType = useRef(null);

    const navigate = useNavigate();

    // 셀렉트박스.
    const[select, setSelect] = React.useState('');
    const onChangeSelect = (e) => {
        setSelect(e.target.value);
    }

    // click event
    const onClickSubmit = () => {
        const confirm = window.confirm("제출하면 수정하실수 없습니다. 제출하시겠습니까?");
        if(confirm === false){
            return;
        }

        const memberId = writer;// 게시판 작성자
        const boardId = id; //게시판 아이디
        const applyWriter = localStorage.getItem("memberId"); // 지원자 ID
        const applyWriterEmail = writerEmailRef.current.value; // 지원자 email
        const titleVal = titleRef.current.value; // title
        const contentVal = contentRef.current.value; // 내용
        const skillLevelVal = select; // 스킬레벨
        const applyTypeVal = applyType.current.value; // 지원분야

        // memberId : -> 작성자는 String 으로 Request, 서버에서 해당 ID값 조회.
        axios.post('/apply/v1/save', {
            title: titleVal,
            type: applyTypeVal,
            level: skillLevelVal,
            writer: applyWriter,
            writerEmail: applyWriterEmail,
            content: contentVal,
            itemBoard: boardId,
            memberId: memberId,
            applyStatus: 'none'
        }, {
            headers: {
                AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
            }
        }).then(function (res){
            window.alert("지원서 제출이 완료되었습니다.");
            navigate('/itemDetail/' + id);
        }).catch(function (err) {
            if(err.response.status === 403) {
                navigate("/member/signin");
            }
            window.alert("지원서 제출중 문제가 발생했습니다. 잠시후 다시 시도해주세요.");
        })
    }

    return(
        <div>
            <div align="center" style={{padding : 100}}>
                <div >
                    <h1>지원서 작성</h1>
                </div>
                <table className="table table-striped table-bordered table-hover">
                    <tbody>
                    <tr>
                        {/*TODO : td 작성자 부분 삭제 혹은 로그인 기반 데이터 입력*/}
                        <th scope="row" >작성자 ID</th>
                        <td colSpan="6">{localStorage.getItem("memberId")}</td>
                    </tr>
                    <tr>
                        <th scope="row" >제목</th>
                        <td colSpan="3" width="23%">
                            <input ref={titleRef} type="text" placeholder="제목을 입력하세요" style={{width:300}}/>
                        </td>
                        <th scope="row" width="10%">지원자 스킬레벨</th>
                        <td>
                            <select value={select} onChange={onChangeSelect}>
                                <option value='none'>== 선택 ==</option>
                                <option value='beginner'>초보자(1년미만)</option>
                                <option value='junior'>주니어(1~3년차)</option>
                                <option value='senior'>시니어(4~8년차)</option>
                                <option value='master'>마스터(9년차 이상)</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row">지원자 이메일</th>
                        <td colSpan="3">
                            <input type="text" ref={writerEmailRef} style={{width: 300}}  placeholder="답변을 받으실 이메일을 기입해주세요." />
                        </td>
                        <th scope="row">지원분야</th>
                        <td>
                            <input type="text" ref={applyType} placeholder='ex) 백엔드, 프론트엔드, 디자이너' style={{width:300}}/>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row">내용</th>
                        <td colSpan="6" className="view_text">
                            <textarea ref={contentRef} style={{width: 800, height: 300}} maxLength={1000} placeholder="내용을 입력하세요. (1000자)"></textarea>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div align="center">
                <button onClick={onClickSubmit} className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 380, fontSize: 22}} >
                    지원서 제출
                </button>
            </div>
        </div>
    )

}//func

export default ApplyWrite;
