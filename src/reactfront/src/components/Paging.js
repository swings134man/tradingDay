import React, { useState } from "react";
import Pagination from "react-js-pagination";
import css from "../css/pagination.css";

const Paging = () => {
    const [page, setPage] = useState(1);

    const handlePageChange = (page) => {
        setPage(page);
        console.log("나는 page",page);
    };

    return (
        <Pagination
            activePage={page} // 현재 페이지
            itemsCountPerPage={10} // 한 페이지 당 보여줄 아이템 갯수
            totalItemsCount={30} // 총 아이템 갯수
            pageRangeDisplayed={5} // paginator의 페이지 범위
            prevPageText={"‹"} // "이전"을 나타낼 텍스트
            nextPageText={"›"} // "다음"을 나타낼 텍스트
            onChange={handlePageChange} // 페이지 변경을 핸들링하는 함수
        />
    );
};

export default Paging;