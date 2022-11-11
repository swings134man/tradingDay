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
        console.log("나는 title" ,titleRef.current.value);
        console.log("나는 content" ,contentRef.current.value);
        console.log("나는 qnaId" , qnaId);

        const titleVal = titleRef.current.value;
        const contentVal = contentRef.current.value;
        console.log(titleVal);
        console.log(contentVal);
        const uri = `http://localhost:8080/qna/v1/updateQna/`;
                //const uri = `/qna/v1/updateQna?${changeQna[0]}&${changeQna[1]}&${changeQna[3]}`;
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
                alert("성공적으로 수정했다!");
                navigate('/qna/qnaBoard');
            }
        })
     }

    // const onUpdate = async () => {
    //     const confirm = window.confirm("수정 하시겠습니까?");
    //     if (confirm === true) {
    //         //const uri = `http://localhost:8080/qna/v1/updateQna`;
    //         //const uri = `/qna/v1/updateQna?${changeQna[0]}&${changeQna[1]}&${changeQna[3]}`;
    //         //const encoded = encodeURI(uri);
    //         const json = await (
    //             await fetch(`http://localhost:8080/qna/v1/updateQna`, {
    //                 method: 'PUT',
    //                 headers: {
    //                     'Content-Type': 'application/json charset=utf-8',
    //                 },
    //                 body: JSON.stringify({
    //                     qnaId: qnaId,
    //                     title: titleRef.current.value,
    //                     content: contentRef.current.value,
    //                     writer: writer,
    //                     createdDate: createdDate
    //                 })
    //             })
    //         ).json();
    //         console.log(json);
    //     }
    // }

    // const onUpdate = () => {
    //     const confirm = window.confirm("수정 하시겠습니까?");
    //     if (confirm === true) {
    //             fetch(`http://localhost:8080/qna/v1/updateQna/`, {
    //                 method: 'PUT',
    //                 headers: {
    //                     'Content-Type': 'application/json charset=utf-8',
    //                 },
    //                 body: JSON.stringify({
    //                     qnaId": 13,
    //                 })
    //             }).then(res => {
    //                 if(res.ok) {
    //                     alert("수정 성공");
    //                 }
    //             }
    //         );
    //     }
    // }



    // const onUpdate = async () => {
    //         //const uri = `http://localhost:8080/qna/v1/updateQna/`;
    //         //const uri = `/qna/v1/updateQna?${changeQna[0]}&${changeQna[1]}&${changeQna[3]}`;
    //         //const encoded = encodeURI(uri);
    //         await fetch(`http://localhost:8080/qna/v1/updateQna`, {
    //             method: "PUT",
    //             headers: {
    //                 "Content-type": "application/json charset=utf-8",
    //             },
    //             body: JSON.stringify({
    //                 qnaId: qnaId
    //             }),
    //         })
    //     //         .then(res => {
    //     // if (res.ok) {
    //     //     alert("성공적으로 수정하였습니다");
    //     // }
    // //})
    // }
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