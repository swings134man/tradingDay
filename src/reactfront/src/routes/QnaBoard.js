import React, {useState, useEffect, useMemo} from 'react';
import Button from "../Button";
import TableTest from "../components/TableTest";
import  { Outlet} from 'react-router-dom'


function QnaBoard() {
    const [qnaList, setQnaList] = useState([]);
    const onSubmit = (event) => {
        event.preventDefault();
    }
    const getData = async () => {
        const res = await fetch('http://localhost:8080/qna/v1/qnaList');
        const data = await res.json();
        setQnaList(data);
    };

    return (

        <div align="center" style={{padding: 100}}>
            <form onSubmit={onSubmit}>
                <button onClick={getData}>게시판 정보 가져오기</button>
                <TableTest data={qnaList} />
            </form>
        </div>
    )
}

export default QnaBoard;