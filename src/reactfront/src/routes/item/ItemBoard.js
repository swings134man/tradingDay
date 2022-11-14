import React, {useState, useEffect} from "react";
import {Link} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.css';
import {v4} from 'uuid';
import axios from "axios";

import Paging from "../../components/Paging";
import TableTest from "../../components/TableTest";

function ItemBoard() {
    const[itemList, setItemList] = React.useState([]);

    useEffect(() => {
        const getData = async () => {
            const item = await axios.get(`/item/v1/findAllPage?pageNumber=0&pageSize=10`);
            setItemList(item.data);
            console.log(itemList);
        }
        getData();
    }, [])//use eff

    return (
        <div>
            <div align="center" style={{padding : 100, paddingRight: 330, paddingLeft: 330}}>
                <div>
                    <h1>거래 게시판</h1>
                </div>

                {/*테이블*/}
                <div>
                    <table className="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th scope="col">게시글 번호</th>
                            <th scope="col">제목</th>
                            <th scope="col">내용</th>
                            <th scope="col">이름</th>
                            <th scope="col">작성 날짜</th>
                            <th scope="col">조회수</th>
                        </tr>
                        </thead>
                        <tbody>
                        {/* 이부분 코드 이해 */}
                        {itemList.content && itemList.content.map(data => (
                            <tr key={v4()}>
                                <td>
                                    {data.id}
                                </td>
                                <td>
                                    <Link to={`/itemDetail/${data.id}`}> {data.title} </Link>
                                </td>
                                <td>
                                    {data.content}
                                </td>
                                <td>
                                    {data.writer}
                                </td>
                                <td>
                                    {data.createdDate}
                                </td>
                                <td>
                                    {data.view}
                                </td>
                            </tr> ))}
                        </tbody>
                    </table>
                </div>

                {/*<div align="right">*/}
                {/*    <button className="btn btn-warning" style={{fontWeight: "bold", color: "white"}}>*/}
                {/*        <Link to={"/qnawrite"}>*/}
                {/*            문의하기*/}
                {/*        </Link>*/}
                {/*    </button>*/}
                {/*</div>*/}
                <Paging />
            </div>
        </div>
    )//return
}// func

export default ItemBoard;