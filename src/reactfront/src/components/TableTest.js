import React, {useEffect, useState} from "react";
import {v4} from 'uuid';
import {Link} from "react-router-dom";
import {manageQuarter} from "./RoleQuarterUtil";


function TableTest({data}) {
    let date = "";
    const [userRole, setUserRole] = useState("");
    //
    // setUserRole(manageQuarter(localStorage.getItem("memberId")));
    useEffect(() => {
        setUserRole(manageQuarter(localStorage.getItem("userRole")));
    }, []);


    return (
        <div>
            <table className="table table-striped table-bordered table-hover">
                <thead>
                    <tr>
                        <th scope="col">문의글 번호</th>
                        <th scope="col">제목</th>
                        <th scope="col">이름</th>
                        <th scope="col">작성 날짜</th>
                    </tr>
                </thead>
                <tbody>
                {data.content && data.content.map(dataList => (
                        <tr key={v4()}>
                            <td>
                                {dataList.qnaId}
                            </td>
                                <td>
                                    {userRole !== "admin" ?
                                    <Link to={`/qnapwdchk/${dataList.qnaId}`}> {dataList.title} </Link>
                                     : <Link to={`/qnadetail/${dataList.qnaId}`}> {dataList.title} </Link>
                                    }
                                </td>
                            <td>
                                {dataList.writer}
                            </td>
                            <td>
                                {date = dataList.createdDate.substring(0, 10)}
                            </td>
                        </tr> ))}
                </tbody>
                    </table>
        </div>
    )
}

export default TableTest;
