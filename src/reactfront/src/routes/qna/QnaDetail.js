import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import TableTest from "../../components/TableTest";
import { useNavigate } from "react-router-dom";


function QnaDetail() {
    const [qna, setQna] = useState([]);
    const {qnaId} = useParams();
    const navigate = useNavigate();

    // pk로 조회 후 단건 세팅
    const getData = async () => {
        const uri = `http://localhost:8080/qna/v1/findByQnaId?qnaId=${qnaId}`;
        const encoded = encodeURI(uri);
        const json = await (
            await fetch(encoded)
        ).json();
        setQna(json);
    }
    useEffect(() => {
        getData();
    }, []);

    // delete onclick event
    const onRemove = async () => {
        const confirm = window.confirm("해당 문의를 삭제하시겠습니까?");
        if(confirm === true) {
                const uri = `http://localhost:8080/qna/v1/deleteQna?qnaId=${qnaId}`;
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
            <div align="center" style={{ padding : 100 , paddingRight: 330, paddingLeft: 330}}>
                <h1>나는 문의글에 detail.</h1>
                <div align="left">
                    <TableTest data={qna} />
                </div>
                <div align="right">
                    <button className="btn btn-warning" >게시글 수정</button>
                    <button className="btn btn-danger" onClick={onRemove}>게시글 삭제</button>
                </div>
            </div>
        </div>
    )
}

export default QnaDetail;