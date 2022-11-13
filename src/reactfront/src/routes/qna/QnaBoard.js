import React, {useState, useEffect, useMemo} from 'react';
import TableTest from "../../components/TableTest";
import 'bootstrap/dist/css/bootstrap.css';
import {Link} from "react-router-dom";

function QnaBoard() {
    const [qnaList, setQnaList] = useState([]);
    const onSubmit = (event) => {
        event.preventDefault();
    }
    const getData = async () => {
        const res = await fetch('/qna/v1/qnaList');
        const data = await res.json();
        setQnaList(data);
    };

    useEffect(() =>{
        getData();
    }, []);


    return (

        <div>
            <div align="center" style={{padding : 100, paddingRight: 330, paddingLeft: 330}}>
                <div>
                    <h1>문의 게시판</h1>
                </div>
                <TableTest data={qnaList} />
                <div align="right">
                    <button className="btn btn-warning" style={{fontWeight: "bold", color: "white"}}>
                        <Link to={"/qnawrite"}>
                        문의하기
                        </Link>
                    </button>
                </div>
            </div>

        </div>
    )
}

export default QnaBoard;
