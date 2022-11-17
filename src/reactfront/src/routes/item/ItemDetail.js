import React, {useState, useEffect} from "react";
import {Link, useParams} from "react-router-dom";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import {v4} from 'uuid';


function ItemDetail() {

    const [data, setData] = React.useState([]);
    const [dataReply, setDataReply] = React.useState([]);
    const {id} = useParams();
    const navigate = useNavigate();

    //use Effect
    /*
        초기 Data
     */
    let replyList = [];
    useEffect(() => {
        const getData = async () => {
            // 파라미터 -> 보낼이름 : data

            const item = await axios.get('/item/v1/detailPost', {
                params:{
                    id: id
                }
            });
            setData(item.data);

            // for (let i = 0; i < item.data.replys.length; i++) {
            //     replyList.push(item.data.replys[i]);
            //     console.log('리스트 : ' + replyList[i]);
            // }

            console.log(item);
            console.log("댓글 : "+ item.data.replys[0].writer);
            console.log("댓글 : "+ item.data.replys.length);

        }
        getData();

    }, []);//use Effect


    /*
        각 버튼 이벤트.
     */
    const onClickGame = () => {
        window.alert('해당 기능은 현재 준비중입니다! 조만간 봐용!');
    }

    // 삭제 버튼
    const onClickDelete = () => {
        const confirm = window.confirm("해당 게시물을 삭제하시겠습니까?");
        if (confirm === true) {
            const delData = async () => {
                const res = await axios.delete('/item/v1/deletePostOne', {
                    params:{
                        id:id
                    }
                }).then(function (response) {
                    if(response.status === 200) {
                        window.alert("게시물이 성공적으로 삭제되었습니다.");
                        navigate('/item/itemBoard');
                    }
                }).catch(function (error) {
                    window.alert("삭제 도중 오류가 발생했습니다. " + error);
                });
            }// delData
            delData();
        }//if
    }// delete


    return (
        // padding: 5px 1px 2px 3px
        <div>
            <div align="center" style={{ padding : 50 , paddingRight: 60, paddingLeft: 60, paddingTop: 100, paddingBottom: 60}}>
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
                                <button onClick={onClickGame} className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 150, color: "white"}}>한방게임 버튼~</button>
                            </td>
                        </tr>
                        <tr>
                            <td colSpan="6" height="300">
                                {data.content}
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div align="right">
                    <button className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}} >
                        <Link to={`/qnaUpdate/${data.id}/${data.title}/${data.writer}/${data.content}/${data.createdDate}`} style={{color: "white"}}>
                            게시글 수정
                        </Link>
                    </button>
                    <button onClick={onClickDelete} className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}}>
                        게시글 삭제
                    </button>
                </div>
                <div style={{paddingLeft: 60, paddingRight: 60, paddingBottom: 100}}>
                    <hr align="center" style={{width: "100%"}}/>
                    <h2 className="navbar-brand" style={{fontSize: 32}}>Comment</h2>

                    <table border='1' align='center'>
                        <tbody>
                            <tr>
                                <td style={{width: 60}} align='center'>
                                    <label>id</label>
                                </td>
                                <td colSpan="2">
                                    <textarea rows="5" cols="80" placeholder="댓글을 입력하세요!"></textarea>
                                </td>
                                <td align='center'>
                                    <button type="button" className="btn btn-warning"
                                            style={{backgroundColor: "#217Af0", width: 100, color: "white"}}>
                                        댓글작성
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td align="right">

                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                {/* 댓글 입력창. 1650*/}
                <div style={{paddingLeft: 60, paddingBottom: 200, paddingRight: 60}}>
                    <table border='1' >
                        <thead>
                            <tr>
                                <th scope="col">작성자</th>
                                <th scope="col">내용</th>
                                <th scope="col">작성날짜</th>
                                <th scope="col">수정</th>
                                <th scope="col">삭제</th>
                            </tr>
                        </thead>
                        <tbody>
                        {replyList.content && replyList.content.map(reply => (
                                <tr key={v4()}>
                                    <td>
                                        {reply.replys.writer}
                                    </td>
                                    <td>
                                        {reply.replys.content}
                                    </td>
                                    <td>
                                        {reply.replys.createdDate}
                                    </td>
                                    <td>
                                        <label>수정버튼</label>
                                    </td>
                                    <td>
                                        <label>삭제버튼</label>
                                    </td>
                                </tr>))}
                        </tbody>
                    </table>
                </div>
            </div>


        {/*  전체area  */}
        </div>
    ) //return
}//func

export default ItemDetail;