import React, {useRef, useState} from "react";
import {Link, useParams} from "react-router-dom";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import Loader from "../../components/Loader";


function ApplyAccept() {


    const navigate = useNavigate();
    // parameters
    const {applyId} = useParams();
    const {writer} = useParams(); // 지원자 ID
    const {writerEmail} = useParams();
    const {title} = useParams();
    const {itemBoard} = useParams();
    const myId = localStorage.getItem("memberId"); // 로그인 사용자 ID

    // ref
    const contentRef = useRef(null); //내용
    const titleRef = useRef(null);
    const writerRef = useRef(null);

    // Loading
    const [loading, setLoading] = useState(false);
    if(loading) {
        return <Loader type='spin' color='blue' message={"메시지를 보내는 중입니다."}/>;
    }


    const onClickApply = () => {
        const con = window.confirm("수락 답변을 보내시겠습니까?");
        if(con === false){
            return;
        }

        // loading
        setLoading(true);

        const data = {
            writerEmail: writerEmail,
            content: contentRef.current.value,
            title: titleRef.current.value,
            writer: writer,
            applyId: applyId,
            boardId: itemBoard
        }
        //axios
        axios.post('/apply/v1/applyReplyAccept',data, {
            headers: {
                AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
            }
        })
            .then(function (res) {
                console.log(res);
                window.alert("답변이 성공적으로 전달되었습니다!");
                setLoading(false);
                navigate('/'); // home 화면으로
            }).catch(function (err){
                window.alert("답변을 전송하던 도중 문제가 발생 했습니다!" + err);
                setLoading(false);
        });
    }


    return(
        <div>
            <div align="center" style={{padding : 100}}>
                <div >
                    <h1>지원서 수락 답변</h1>
                </div>
                <table className="table table-striped table-bordered table-hover">
                    <tbody>
                    <tr>
                        {/*TODO : td 작성자 부분 삭제 혹은 로그인 기반 데이터 입력*/}
                        <th scope="row" >지원자 ID</th>
                        <td colSpan="6">{writer}</td>
                        {/*<td colSpan='6'>*/}
                        {/*    <input ref={writerRef} type="text" style={{width:200}}/>*/}
                        {/*</td>*/}
                    </tr>
                    <tr>
                        <th scope="row">지원자 이메일</th>
                        <td colSpan="3">
                            {writerEmail}
                        </td>
                    </tr>
                    <tr>
                        <th scope="row" >제목</th>
                        <td colSpan="3" width="23%">
                            <input ref={titleRef} type="text" placeholder="제목을 입력하세요" style={{width:300}}/>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row">내용</th>
                        <td colSpan="6" className="view_text">
                            <textarea ref={contentRef} style={{width: 800, height: 300}}
                                      maxLength={1000}
                                      placeholder="본인이 답변 받을 이메일이나 연락처 정보를 꼭 기재해 주세요!! &#13;&#10;
                                      1. 연락할 연락처(이메일 필수 작성) &#13;&#10;
                                      2. 수락버튼 클릭시 해당 지원자에게 내용에 대한 이메일이 전송됩니다! . &#13;&#10;
                                      (1000자)">
                            </textarea>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div align="center">
                <button onClick={onClickApply} className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 380, fontSize: 22}} >
                    지원 수락하기
                </button>
            </div>
        </div>
    )
}

export default ApplyAccept;