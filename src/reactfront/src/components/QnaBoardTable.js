import React, {useEffect, useState} from "react";
import {v4} from 'uuid';
import {Link} from "react-router-dom";
import {manageQuarter} from "./RoleQuarterUtil";

import Note from "./Note";

function QnaBoardTable({data}) {
    let date = "";
    const [userRole, setUserRole] = useState("");

    useEffect(() => {
        setUserRole(manageQuarter(localStorage.getItem("userRole")));
    }, []);

    // 쪽지 스테이트
    const [note, setNote] =useState(false);
    const [selectIndex, setSelectIndex] = useState(0);
    const [click, setClick] = useState(false);

    // 쪽지 show hide , 데이터 세팅
    const noteClick = ((e) => {
        setSelectIndex(e.target.getAttribute("data-id"));
        // 해당 코드는 한번 더 클릭시 사라지지 않음.
        // if(note) {
        //     setNote(true);
        // } else {
        //     setNote(false);
        // }
        if(click === false) {
            setNote(true);
            setClick(true);
        }else {
            setNote(false);
            setClick(false);
        }
    });

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
                                <div >
                                    <a href={"#!"} onClick={noteClick} data-id={dataList.qnaId}>
                                        {dataList.writer}

                                    </a>
                                    {/*--------------- 컴포넌트 보여 주기 -------------*/}
                                    {/*2023.01.03 03:00:00 시 코드 수정 setNote -> note */}
                                    <div>
                                        <div style={{paddingLeft: 30}}>
                                            {note && selectIndex == dataList.qnaId ? <Note memberId={dataList.writer}/> : null }
                                        </div>
                                    </div>
                                </div>

                            </td>
                            <td>
                                {date = dataList.createdDate.substring(0, 10)}
                            </td>
                        </tr>
                ))}
                </tbody>
                    </table>
        </div>
    )
}

export default QnaBoardTable;
