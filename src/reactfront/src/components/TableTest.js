import PropTypes from 'prop-types';
import React, {useState} from "react";
import {v4} from 'uuid';
import {BrowserRouter as Router, Link, Route, Routes} from "react-router-dom";


function TableTest({data}) {
    console.log(data.content);
    return (

        <div>
                {/*{data.length > 1 ? (*/}
                {data.content.length > 1 ? (
                    <table className="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th scope="col">문의글 번호</th>
                    <th scope="col">제목</th>
                    <th scope="col">내용</th>
                    <th scope="col">이름</th>
                    <th scope="col">작성 날짜</th>
                </tr>
                </thead>
                <tbody>
                {data.content.map(dataList => (
                        <tr key={v4()}>
                            <td>
                                {dataList.qnaId}
                            </td>
                            <td>
                                <Link to={`/qnaDetail/${dataList.qnaId}`}> {dataList.title} </Link>
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
                        </tr>))}
                </tbody>
                    </table>
                    ) : (
                        <div>
                            <h1>Null!</h1>
                        </div>

                    // <table className="table table-striped table-bordered table-hover">
                    //     <colgroup>
                    //         <col width="150px"/>
                    //         <col/>
                    //     </colgroup>
                    //     <tbody>
                    //     <tr>
                    //         <th className="active">문의번호</th>
                    //         <td>
                    //             {data.content.qnaId}
                    //         </td>
                    //     </tr>
                    //     <tr>
                    //         <th className="active">작성자</th>
                    //         <td>
                    //             {data.content.writer}
                    //         </td>
                    //     </tr>
                    //     <tr>
                    //         <th className="active">제목</th>
                    //         <td>
                    //             {data.content.title}
                    //         </td>
                    //     </tr>
                    //     <tr>
                    //         <th className="active">내용</th>
                    //         <td>
                    //             {data.content.content}
                    //         </td>
                    //     </tr>
                    //     <tr>
                    //         <th className="active">작성 날짜</th>
                    //         <td>
                    //             {data.content.createdDate}
                    //         </td>
                    //     </tr>}
                    //     </tbody>
                    // </table>

                    ) // 분기 end
                    } // if end
        </div>
    )
}

export default TableTest;
