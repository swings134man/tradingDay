import React, {useState, useEffect, useRef} from "react";
import {Link, useParams} from "react-router-dom";
import { useNavigate } from "react-router-dom";
import PropTypes from "prop-types";
import axios from "axios";
import {v4} from 'uuid';


function ItemDetail() {

    const [data, setData] = React.useState([]); // DTO Data
    const [images, setImages] = React.useState([]); // Image Data(Binary)
    const {id} = useParams();
    const navigate = useNavigate();

    //use Effect
    /*
        초기 Data
     */
    let replyList = [];
    const [refresh, setRefresh] = React.useState(""); // 댓글 작성시 페이지 refresh하기 위한 state
    useEffect(() => {
        const getData = async () => {
            // 파라미터 -> 보낼이름 : data
            const item = await axios.get('/item/v1/detailPost', {
                params:{
                    id: id
                }
            });
            setData(item.data);
            console.log(item.data);
        }
        getData();

        // 이미지 to Server
        // const dataImageList = item.data.images; // Image List
        // console.log('이미지 리스트 : '+dataImageList);
        //
        // const getImage = async () => {
        //     const fromServerImage = await axios.get('/image/v1/showImageList');
        //
        //     // setImages(fromServerImage.data)
        // }//getImage
        // getImage();
        // console.log(images.data);
    }, []);//use Effect

    // Refresh
    const reSearch = async () => {
        const getRes = async () => {
            // 파라미터 -> 보낼이름 : data
            const item = await axios.get('/item/v1/detailPost', {
                params:{
                    id: id
                }
            });
            setData(item.data);
            console.log(item.data);
        }
        getRes();
    }//research


    /*
        각 버튼 이벤트.
     */
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
                        // navigate('/item/itemBoard');
                    }
                }).catch(function (error) {
                    window.alert("삭제 도중 오류가 발생했습니다. " + error);
                });
            }// delData
            delData();
        }//if
    }// delete

    /*
     댓글
    */
    const writerId = "iu1234"; // 작성자 ID --> TODO : Login session ID
    let replyId = 0; // 댓글 ID
    const answerRef = useRef(null);

    // 댓글 post
    const onClickReply = () => {
        const replyInputVal = answerRef.current.value; // 댓글 입력값.

            axios.post('/item/v1/reply/save', {
                    boardId: id,
                    writer: writerId,
                    content: replyInputVal
            }, {
                headers: {
                    "Content-Type": `application/json`,
                }}).then(function (response){
                console.log('댓글 작성 완료');
                answerRef.current.value = "";
                // navigate('/itemDetail/'+id);
                reSearch();
            }).catch(function (error){
                console.log('댓글 작성 에러' + error);
                window.alert('댓글 작성중 문제가 발생하였습니다.');
            });
    }//on click

    //댓글 delete
    const onClickReplyDelete = (e) => {
        // TODO : 로그인 상황에 따라 삭제 버튼을 disable, 혹은 해당 함수에서 제거.
        let replyId = e.target.getAttribute('data-id');
        const cfm = window.confirm('해당 댓글을 정말 삭제하시겠습니까?');
        if(cfm === true){
            axios.delete('/item/v1/reply/delete', {
                params: {
                    id:replyId
                }
            }).then(function (res) {
                console.log('댓글 삭제 완료');
                reSearch();
            }).catch(function (error) {
                console.log('댓글 삭제중 문제가 발생 : '+error);
                window.alert('댓글 삭제중 문제가 발생');
            })
        }
    } //del

    // 댓글 update
    const [selectIndex, setSelectIndex] = useState(0);
    const [modiTextArea, setModiTextArea] = useState(false);
    const modiAnswerRef = useRef(null); // 수정 texe area
    let writeDate = ""; // 댓글 list date format

    function modiInputShow (e) {
        replyId = e.target.getAttribute("data-id");
        setSelectIndex(replyId);

        if (!modiTextArea ) {
            setModiTextArea(true);
        } else {
            setModiTextArea(false);
        }
    }

    const answerClickUpdate = (e) => {
        const modiAnswerVal = modiAnswerRef.current.value; // textarea
        replyId = e.target.getAttribute("data-id"); // 댓글의 아이디

        axios.put(`/item/v1/reply/update`, {
            id: replyId,
            writer: writerId,
            content: modiAnswerVal,
            boardId: id
        })
            .then(function (res){
                window.alert("댓글 수정 완료.");
                setModiTextArea(false);
                reSearch();
            })
            .catch(function (err) {
                window.alert("댓글을 수정하던 도중 문제가 발생했습니다.");
            });
    }

    // 지원하기 버튼
    const clickApply = () => {
        if(data.type === '모집완료') {
            window.alert('해당 게시글은 모집이 완료되었습니다.');
            return;
        }
        navigate('/applyWrite/' + data.id + '/' + data.writer);
    }


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
                            <td colSpan='3'>{data.view}</td>
                        </tr>
                        <tr>
                            <th scope="row">작성자</th>
                            <td >{data.writer}</td>
                            <th scope='row'>모집 상태</th>
                            {
                                data.type === '모집중'
                                    ?
                                    <td style={{color:"green"}}>
                                        {data.type}
                                    </td>
                                    :<td style={{color:"red"}}>
                                        {data.type}
                                    </td>
                            }
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
                                {
                                    data.type === '모집중'
                                    ?
                                    <button className="btn btn-warning"
                                            style={{backgroundColor: "#217Af0", width: 150, color: "white"}}
                                            onClick={clickApply}>
                                        지원하기
                                    </button>
                                        :<button className="btn btn-warning"
                                                 style={{backgroundColor: "#217Af0", width: 300, color: "white"}}
                                                 onClick={clickApply}
                                                 disabled={true}>
                                            해당 모집공고는 마감되었습니다.
                                        </button>
                                }
                            </td>
                        </tr>
                        <tr>
                            <td colSpan="6" height="300">
                                {data.content}
                            </td>
                        </tr>
                        </tbody>
                    </table>
                {/*          아래부터 이미지              */}
                {/*<img  style={{height:'30%',width:'30%'}}*/}
                {/*        src={'/Users/seokjunKang/intellij-gradle/day-file/30c746db-55f4-4f12-a993-72df17b8b78d.png'}/>*/}

                </div>

                <div align="right">
                    <button className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}} >
                        <Link to={`/itemBoardUpdate/${data.id}/${data.title}/${data.writer}/${data.content}/${data.createdDate}`} style={{color: "white"}}>
                            게시글 수정
                        </Link>
                    </button>
                    <button onClick={onClickDelete} className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}}>
                        게시글 삭제
                    </button>
                </div>
                {/* 댓글 */}
                <div align="center" style={{ padding : 75}}>
                    <hr />
                    {/*입력 form*/}
                    <table border={1}>
                        <tbody>
                        <tr height="50">
                            <td width="10%" className="col1" align="center">댓글 달기</td>
                            <td width="90%" className="col2">
                                <div style={{float:"left"}}>
                                    <textarea  style={{width: 800, height: 100}} ref={answerRef}></textarea>
                                </div>
                                {/*style="padding:;">  상우좌하*/}
                                <div style={{float:"left", paddingTop: 34, paddingRight: 12, paddingLeft:34, paddingBottom: 12}}>
                                    <button className="btn btn-warning"
                                            style={{backgroundColor: "#217Af0", width: 100, color: "white"}}
                                            onClick={onClickReply}> 답변 입력 </button>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    {/*댓글 list*/}
                    <table border={1} className="table table-striped table-bordered ">
                        <tbody >
                        {data.replys && data.replys.map(answer =>  (
                            <tr key={v4()}>
                                <td>
                                    <div border={1}>
                                        <div  style={{ backgroundColor: "#EBEBEB"}}>
                                            <div style={{padding:10, fontWeight:"bold"}}> {answer.writer} {writeDate = answer.createdDate.substring(0, 10)}</div>
                                        </div>
                                    </div>
                                    <div>
                                        {answer.content}
                                        <div>

                                        </div>
                                        {/*<div align="right">*/}
                                        {/*    <button className="btn btn-warning"*/}
                                        {/*            style={{backgroundColor: "#217Af0", width: 45, height: 25, color: "white", fontSize:10}}*/}
                                        {/*            data-id={answer.id}*/}
                                        {/*            onClick={(e) => {answerClickDelete(e)}}>삭제</button>*/}

                                        {/*    <button className="btn btn-warning"*/}
                                        {/*            style={{backgroundColor: "#217Af0", width: 45, height: 25, color: "white", fontSize:10}}*/}
                                        {/*            data-id={answer.id}*/}
                                        {/*            // onClick={(e) => {answerClickUpdate(e)}}>>수정</button>*/}
                                        {/*            onClick={modiInputShow}>수정</button>*/}
                                        {/*</div>*/}

                                        {modiTextArea && selectIndex == answer.id? (
                                            <div style={{float:"left"}}>
                                                <textarea style={{width: 800, height: 100}} placeholder="수정내용을 입력하세요" ref={modiAnswerRef}/>

                                                <div align="right">
                                                    <button className="btn btn-warning"
                                                            style={{backgroundColor: "#217Af0", width: 45, height: 25, color: "white", fontSize:10}}
                                                            data-id={answer.id}
                                                            onClick={answerClickUpdate}
                                                    > 수정 </button>
                                                    <button className="btn btn-warning"
                                                            style={{backgroundColor: "#217Af0", width: 70, height: 25, color: "white", fontSize:10}}
                                                            onClick={modiInputShow} > 수정취소 </button>
                                                </div>
                                            </div>
                                        ) :  (
                                            <div align="right">
                                                <button className="btn btn-warning"
                                                        style={{backgroundColor: "#217Af0", width: 45, height: 25, color: "white", fontSize:10}}
                                                        data-id={answer.id}
                                                        onClick={(e) => {onClickReplyDelete(e)}}>삭제</button>

                                                <button className="btn btn-warning"
                                                        style={{backgroundColor: "#217Af0", width: 45, height: 25, color: "white", fontSize:10}}
                                                        data-id={answer.id}
                                                        onClick={modiInputShow}>수정</button>
                                            </div>
                                        )}
                                    </div>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                    {/*상단*/}
                </div>
            {/*  center  */}
            </div>
        {/*  전체area  */}
        </div>
    ) //return
}//func

export default ItemDetail;