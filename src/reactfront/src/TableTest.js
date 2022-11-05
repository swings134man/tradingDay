import PropTypes from 'prop-types';
import React, {useState} from "react";
import { v4 } from 'uuid';


function TableTest({ data }) {
    return (
    <div>
        <table border={1}>
            <caption>게시판정보 조회하기</caption>
            <thead>
            <tr>
                <th scope="col">문의글 번호</th>
                <th scope="col">제목</th>
                <th scope="col">내용</th>
                <th scope="col">이름</th>
                <th scope="col">작성 날짜</th>
                <th scope="col">수정 날짜</th>
            </tr>
            </thead>
            <tbody>
                {data.map(dataList => (
                    <tr key={v4()}>
                        <td >
                            {dataList.qnaId}
                        </td>
                        <td>
                            {dataList.title}
                        </td>
                        <td>
                            {dataList.content}
                        </td>
                        <td>
                            {dataList.writer}
                        </td>
                        <td>
                            {dataList.createdDate}
                        </td>
                        <td>
                            {dataList.modifiedDate}
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
    </div>
)


}


export default TableTest;