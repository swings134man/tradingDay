import React, {useState, useEffect} from 'react';
import TableTest from "../../components/TableTest";
import 'bootstrap/dist/css/bootstrap.css';
import {Link} from "react-router-dom";

import Pagination from "react-js-pagination";

function QnaBoard() {
    const [qnaList, setQnaList] = useState([]);
    const [totalElements, setTotalElements] = useState(0);

    const [page, setPage] = useState(1);

    const handlePageChange = (page) => {
        setPage(page);
    };

    useEffect(() => {
        const getData = async () => {
            const res = await fetch(`/qna/v1/qnabylist?page=${page}`);
            const data = await res.json();
            setQnaList(data);
            setTotalElements(data.totalElements);
        }
        getData();

    }, [page])





    return (

        <div>
            <div align="center" style={{padding : 100, paddingRight: 330, paddingLeft: 330}}>
                <div>
                    <h1>문의 게시판</h1>
                    <h3>{qnaList.totalElements}</h3>
                </div>

                <TableTest data={qnaList} />
                <div align="right">
                    <button className="btn btn-warning" style={{fontWeight: "bold", color: "white"}}>
                        <Link to={"/qnawrite"}>
                        문의하기
                        </Link>
                    </button>
                </div>
            {/*<Paging />*/}
                <Pagination
                    activePage={page} // 현재 페이지
                    itemsCountPerPage={10} // 한 페이지 당 보여줄 아이템 갯수
                    totalItemsCount={totalElements} // 총 아이템 갯수
                    pageRangeDisplayed={5} // paginator의 페이지 범위
                    prevPageText={"‹"} // "이전"을 나타낼 텍스트
                    nextPageText={"›"} // "다음"을 나타낼 텍스트
                    onChange={handlePageChange} // 페이지 변경을 핸들링하는 함수
                />
            </div>
        </div>
    )
}

export default QnaBoard;
