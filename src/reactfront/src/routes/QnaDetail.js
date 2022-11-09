import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";

function QnaDetail() {
    const [qna, setQna] = useState([]);
    const {qnaId} = useParams();

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

    return (
        <div>
            <h1>나는 문의글에 detail.</h1>
            <div>
                문의번호 : {qna.qnaId} <br/>
                제목 : {qna.title} <br/>
                내용 : {qna.content} <br/>
                작성자 : {qna.writer} <br/>
                작성일 : {qna.createdDate} <br/>
            </div>
        </div>
    )
}

export default QnaDetail;