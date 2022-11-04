import PropTypes from 'prop-types';
import React, {useState} from "react";



function TableTest({ data  }) {
return (
    <div>
        <table border={1}>
            <caption>게시판정보 조회하기</caption>
            <thead>
            <tr>
                <th scope="col">문의글 번호</th>
                <th scope="col">제목</th>
                <th scope="col">이름</th>
                <th scope="col">내용</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                {/*<th scope="row"></th>*/}
                <td>{data.qnaId}</td>
                <td>{data.title}</td>
                <td>{data.writer}</td>
                <td>{data.content}</td>
            </tr>
            </tbody>
        </table>
    </div>
)
}


export default TableTest;