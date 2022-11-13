import {useParams} from "react-router-dom";
import {useEffect, useState, useRef} from "react";
import { useNavigate } from "react-router-dom";

function QnaUpdate() {
    const { qnaId, title, writer, content, createdDate  } = useParams();
    const titleRef = useRef(null);
    const contentRef = useRef(null);
    const navigate = useNavigate();

    function onSubmit(e) {
        e.preventDefault();

        const titleVal = titleRef.current.value;
        const contentVal = contentRef.current.value;

        const uri = `http://localhost:8080/qna/v1/updateQna/`;
        const encoded = encodeURI(uri);

        fetch(encoded, {
            method: "POST",
            headers: {
                "Content-type": "application/json",
            },
            body: JSON.stringify({
                qnaId: qnaId,
                title: titleVal,
                content: contentVal,
                writer: writer
            }),
        }).then(res => {
            if(res.ok) {
                navigate('/qna/qnaBoard');
            } else {
                alert("문의 수정에 실패했습니다. 잠시 후 다시 시도해주세요");
            }
        })
     }


    return (
        <div>
        <div align="center" style={{padding : 100, paddingRight: 330, paddingLeft: 330, }}>
            <div >
            <h1>나는 qna수정 페이지</h1>
            </div>
            <form onSubmit={onSubmit}>
            <table className="table table-striped table-bordered table-hover">

                <tbody>
                <tr>
                    <th scope="row">글 번호</th>
                    <td>{qnaId}</td>
                    <th scope="row">작성일</th>
                    <td>{createdDate}</td>
                </tr>
                <tr>
                    <th scope="row">작성자</th>
                    <td >{writer}</td>

                </tr>
                <tr>
                    <th scope="row">제목</th>
                    <td colSpan="3">
                        <input type="text" id="title" name="title" defaultValue={title} ref={titleRef} />
                    </td>
                </tr>
                <tr>
                    <td colSpan="4" className="view_text">
                            <textarea ref={contentRef} title="내용"  name="contents" defaultValue={content}></textarea>
                    </td>
                </tr>
                </tbody>
            </table>
            <div align="right">
                <button className="btn btn-warning" > 수정하기 </button>
            </div>
            </form>
            </div>
        </div>
    )

}

export default QnaUpdate;