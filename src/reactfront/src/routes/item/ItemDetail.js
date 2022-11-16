import React, {useState, useEffect} from "react";
import {Link, useParams} from "react-router-dom";
import { useNavigate } from "react-router-dom";
import axios from "axios";


function ItemDetail() {

    const [data, setData] = React.useState([]);
    const {id} = useParams();

    //use Effect
    /*
        초기 Data
     */
    useEffect(() => {
        const getData = async () => {
            // 파라미터 -> 보낼이름 : data

            const item = await axios.get('/item/v1/detailPost', {
                params:{
                    id: id
                }
            });
            setData(item.data);
            console.log(item);
        }
        getData();
    }, []);//use Effect


    /*
        각 버튼 이벤트.
     */


    return (
        // padding: 5px 1px 2px 3px
        <div>
            <div align="center" style={{ padding : 50 , paddingRight: 60, paddingLeft: 60, paddingTop: 100}}>
                <h1>상세 게시판: {data.title}</h1>
                <div align="left">
                    <table className="table table-striped table-bordered table-hover">
                        <tbody>
                        <tr>
                            <th scope="row">글 번호</th>
                            <td>{data.id}</td>
                            <th scope="row">조회수</th>
                            <td>{data.view}</td>
                        </tr>
                        <tr>
                            <th scope="row">작성자</th>
                            <td >{data.writer}</td>
                            <th scope="row">작성일</th>
                            <td>{data.createdDate}</td>
                        </tr>
                        <tr>
                            <th scope="row">제목</th>
                            <td colSpan="5">
                                {data.title}
                            </td>
                        </tr>
                        <tr>
                            <td colSpan="6" align="center">
                                <button>한방게임 버튼~</button>
                            </td>
                        </tr>
                        <tr>
                            <td colSpan="6" >
                                {data.content}
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div align="right">
                    <button className="btn btn-warning" >
                        <Link to={`/qnaUpdate/${data.id}/${data.title}/${data.writer}/${data.content}/${data.createdDate}`}>
                            게시글 수정
                        </Link>
                    </button>
                    <button className="btn btn-danger">게시글 삭제</button>
                </div>
            </div>
        </div>
    ) //return
}//func

export default ItemDetail;