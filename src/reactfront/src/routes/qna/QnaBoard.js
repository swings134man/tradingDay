import React, {useState, useEffect, useMemo} from 'react';
import TableTest from "../../components/TableTest";
import 'bootstrap/dist/css/bootstrap.css';
import {Link} from "react-router-dom";
import Paging from "../../components/Paging";

function QnaBoard() {
    const [qnaList, setQnaList] = useState([]);
    // const getData = async () => {
    //     const res = await fetch('/qna/v1/qnaList');
    //     const data = await res.json();
    //     setQnaList(data);
    // };
    //
    // useEffect(() =>{
    //     getData();
    // }, []);


    const getData = async () => {
        const res = await fetch(`/qna/v1/qnabylist?pageNumber=0&pageSize=10`);
        const data = await res.json();
        setQnaList(data);
    }
    useEffect(() => {
        getData();
    }, [])








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
            <Paging />
            </div>

        </div>
    )
}

export default QnaBoard;