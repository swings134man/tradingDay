import {useParams} from "react-router-dom";
import {useEffect, useState, useRef} from "react";
import { useNavigate } from "react-router-dom";

function QnaUpdate() {
    const { qnaId, title, writer, content, createdDate  } = useParams();
    const titleRef = useRef(null);
    const contentRef = useRef(null);
    const navigate = useNavigate();

    let date = "";

    function onSubmit(e) {
        e.preventDefault();

        const titleVal = titleRef.current.value;
        const contentVal = contentRef.current.value;

        const inputPattern = /^\s+|\s+$/g;
        if(titleVal.replace(inputPattern, '' ) === "" ){
            alert('제목엔 공백만 입력할수없어요.');
            return;
        } else if(contentVal.replace(inputPattern, '' ) === "") {
            alert('내용엔 공백만 입력할수없어요.')
            return;
        }

        const uri = `http://localhost:8080/qna/v1/updateqna/`;
        const encoded = encodeURI(uri);
        fetch(encoded, {
            method: "PUT",
            headers: {
                "Content-type": "application/json",
                 AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
            },
            body: JSON.stringify({
                qnaId: qnaId,
                title: titleVal,
                content: contentVal,
                writer: writer
            }),
        }).then(res => {
            if(res.status === 403) {
                // 403 에러발생 --> 어스토큰 , 리프레시 토큰 둘다 만료거나 유효하지 않기 때문에 다시 로그인.
                navigate("/member/signin");
            }
            if(res.ok) {
                navigate('/qnaboard');
            } else {
                alert("문의 수정에 실패했습니다. 잠시 후 다시 시도해주세요");
            }
        })
     }


    return (
        <div>
        {/*<div align="center" style={{padding : 100, paddingRight: 330, paddingLeft: 330, }}>*/}
        <div align="center" style={{padding : 100}}>
            <div >
            <h1>문의 수정</h1>
            </div>
            <form onSubmit={onSubmit}>
            <table className="table table-striped table-bordered table-hover">
                <tbody>
                    <tr>
                        <th scope="row">글 번호</th>
                        <td>{qnaId}</td>
                    </tr>
                    <tr>
                        <th scope="row">작성일</th>
                        <td>{date = createdDate.substring(0, 10)}</td>
                    </tr>
                    <tr>
                        <th scope="row">작성자</th>
                        <td colSpan="2" >{writer}</td>

                    </tr>
                    <tr>
                        <th scope="row">제목</th>
                        <td colSpan="3">
                            <input type="text" id="title" name="title" defaultValue={title} ref={titleRef} style={{width: 500}} />
                        </td>
                    </tr>
                    <tr>
                        <th scope="row">내용</th>
                        <td colSpan="4" className="view_text">
                                <textarea style={{width: 500, height: 200}} ref={contentRef}  title="내용"  name="contents" defaultValue={content}></textarea>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div align="right">
                    <button className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100}}> 수정하기 </button>
                </div>
                </form>
            </div>
        </div>
    )

}

export default QnaUpdate;