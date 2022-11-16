import React, {useEffect, useState} from "react";
import {Link, useParams} from "react-router-dom";
import { useNavigate } from "react-router-dom";


function QnaDetail() {
    const [qna, setQna] = useState([]);
    const {qnaId} = useParams();
    const navigate = useNavigate();
    const [date, setDate] = useState("");


    useEffect(() => {
        //pk로 조회 후 단건 세팅
        const getData = async () => {
            const uri = `/qna/v1/findByQnaId?qnaId=${qnaId}`;
            const encoded = encodeURI(uri);
            const json = await (
                await fetch(encoded)
            ).json();

            setDate(json.createdDate.substring(0, 10));
            setQna(json);
        }
        getData();
    },[]);

    // delete onclick event
    const onRemove = async () => {
        const confirm = window.confirm("해당 문의를 삭제하시겠습니까?");
        if(confirm === true) {
                const uri = `/qna/v1/deleteQna?qnaId=${qnaId}`;
                const encoded = encodeURI(uri);
                const json = await (
                    await fetch(encoded, {
                        method: 'DELETE',
                        headers: {
                            'Content-Type': 'application/json',
                        }})
                ).json();

                if(json === 1 ) {
                    window.alert('성공적으로 삭제하였습니다.');
                    navigate('/qna/qnaBoard');
                }
        }
    }; //remove function end

    return (
        // padding: 5px 1px 2px 3px
        <div>
            {/*<div align="center" style={{ padding : 100 , paddingRight: 330, paddingLeft: 330}}>*/}
            <div align="center" style={{ padding : 100}}>
                <h1>나는 문의글에 detail.</h1>
                <div align="left">
                    <table className="table table-striped table-bordered table-hover">
                        <colgroup>
                            <col width="150px"/>
                            <col/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th className="active">문의번호</th>
                            <td>
                                {qna.qnaId}
                            </td>
                        </tr>
                        <tr>
                            <th className="active">작성자</th>
                            <td>
                                {qna.writer}
                            </td>
                        </tr>
                        <tr>
                            <th className="active">제목</th>
                            <td>
                                {qna.title}
                            </td>
                        </tr>
                        <tr>
                            <th className="active">내용</th>
                            <td>
                                {qna.content}
                            </td>
                        </tr>
                        <tr>
                            <th className="active">작성 날짜</th>
                            <td>
                                {date}
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div align="right">
                        <button className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}}>
                            <Link to={`/qnaUpdate/${qna.qnaId}/${qna.title}/${qna.writer}/${qna.content}/${qna.createdDate}`}
                            style={{color: "white"}}>
                            게시글 수정
                            </Link>
                        </button>
                    <button className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}} onClick={onRemove}>게시글 삭제</button>
                </div>
            </div>
        </div>
    )
}

export default QnaDetail;