import React, {useEffect, useRef, useState} from "react";
import {Link, useParams} from "react-router-dom";
import { useNavigate } from "react-router-dom";
import {v4} from 'uuid';
import axios from "axios";
import {render} from "react-dom";


function QnaDetail() {
    let writeDate = "";

    const answerRef = useRef(null);
    const modiAnswerRef = useRef(null);
    const {qnaId} = useParams();
    const navigate = useNavigate();

    const [qna, setQna] = useState([]);
    const [date, setDate] = useState("");

    const [selectIndex, setSelectIndex] = useState(0);
    const [modiTextArea, setModiTextArea] = useState(false);


    let answerId = 0;


    useEffect(() => {
        //pk로 조회 후 단건 세팅
        const getData = async () => {
            const uri = `/qna/v1/findByQnaId?qnaId=${qnaId}`;
            const encoded = encodeURI(uri);
            const json = await (
                await fetch(encoded)
            ).json();
            setDate(json.createdDate.substring(0, 10));
            setQna(json);
        }
        getData();
    },[]);

    // 재조회용 함수
    const reSearch = async () => {
            const uri = `/qna/v1/findByQnaId?qnaId=${qnaId}`;
            const encoded = encodeURI(uri);
            const json = await (
                await fetch(encoded)
            ).json();
            console.log(json);
            setDate(json.createdDate.substring(0, 10));
            setQna(json);
        }

    // 답변달기
    const answerSave = async () => {

        axios.post("/answer/v1/save", {
            qnaId: qna.qnaId,
            content: answerRef.current.value,
            writer : "tradingManager"
        })
            .then(function (response) {
                console.log('res : ' + response.status);
                console.log('답변저장성공함~');
                //재조회
                reSearch();
                //textarea 빈값 설정
                answerRef.current.value = "";

            }).catch(function (error) {
            // 오류발생시 실행
            console.log('error message : '+ error);
             window.alert("답글달기 실패");
        }); // axios
    }


    // 문의글 삭제 이벤트
    const onRemove = async () => {
        const confirm = window.confirm("해당 문의를 삭제하시겠습니까?");
        if(confirm === true) {
                const uri = `/qna/v1/deleteQna?qnaId=${qnaId}`;
                const encoded = encodeURI(uri);
                const json = await (
                    await fetch(encoded, {
                        method: 'DELETE',
                        headers: {
                            'Content-Type': 'application/json',
                        }})
                ).json();

                if(json === 1 ) {
                    window.alert('성공적으로 삭제하였습니다.');
                    navigate('/qna/qnaBoard');
                }
        }
    }; //remove function end


    // 답변 삭제 이벤트
    const answerClickDelete = async (e) => {
        let answerId = e.target.getAttribute("data-id");

        const confirm = window.confirm("해당 답변을 삭제하시겠습니까?");
        if(confirm === true) {
            const uri = `/answer/v1/delete?id=${answerId}`;
            const encoded = encodeURI(uri);
            const json = await (
                await fetch(encoded, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                    }})
            ).json();
        }
        reSearch();
    }; //remove function end



    function modiInputShow (e) {

        answerId = e.target.getAttribute("data-id");
        setSelectIndex(answerId);

        if (!modiTextArea ) {
            setModiTextArea(true);
        } else {
            setModiTextArea(false);
        }

    }

    const answerClickUpdate = async (e) => {

        const modiAnswerVal = modiAnswerRef.current.value;
        console.log(modiAnswerVal)
        answerId = e.target.getAttribute("data-id");
        // const inputPattern = /^\s+|\s+$/g;
        // if(modiAnswerVal.replace(inputPattern, '' ) === "" ) {
        //     alert('내용엔 공백만 입력할수없습니다');
        //     return;
        // }

        console.log(answerId);
        axios.put(`http://localhost:8080/answer/v1/update`, {
            id: answerId,
            qnaId: qnaId,
            content: modiAnswerVal,
            writer: "tradingManager"
        })
        .then(function (res){
        console.log('업데이트 성공')

            setModiTextArea(false);
            reSearch();

        })
        .catch(function (err) {
            alert("수정에 실패했어요")
           console.log('실패 ')
        });



        // const uri = `http://localhost:8080/answer/v1/update`;
        // const encoded = encodeURI(uri);
        // await fetch(encoded, {
        //     method: "POST",
        //     headers: {
        //         "Content-type": "application/json",
        //     },
        //     body: JSON.stringify({
        //         qnaId: qnaId,
        //         id: answerId,
        //         content: modiAnswerVal,
        //         writer: "tradingManager"
        //     }),
        // }).then(res => {
        //     if(res.ok) {
        //         reSearch();
        //     } else {
        //         alert("답변 수정에 실패했습니다. 잠시 후 다시 시도해주세요");
        //     }
        // })
    }

    return (
        <div>
            <div align="center" style={{ padding : 40}}>
                <h1>문의 게시판</h1>
                <div align="left">
                    <table className="table table-striped table-bordered table-hover">
                        <colgroup>
                            <col width="150px"/>
                            <col/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th className="active">문의번호</th>
                            <td>
                                {qna.qnaId}
                            </td>
                        </tr>
                        <tr>
                            <th className="active">작성자</th>
                            <td>
                                {qna.writer}
                            </td>
                        </tr>
                        <tr>
                            <th className="active">제목</th>
                            <td>
                                {qna.title}
                            </td>
                        </tr>
                        <tr>
                            <th className="active">내용</th>
                            <td>
                                {qna.content}
                            </td>
                        </tr>
                        <tr>
                            <th className="active">작성 날짜</th>
                            <td>
                                {date}
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div align="right">
                        <button className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}}>
                            <Link to={`/qnaUpdate/${qna.qnaId}/${qna.title}/${qna.writer}/${qna.content}/${qna.createdDate}`}
                            style={{color: "white"}}> 게시글 수정 </Link>
                        </button>
                    <button className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}} onClick={onRemove}>게시글 삭제</button>
                </div>


            <hr/>

            <div align="center" style={{ padding : 75}}>
                    <table border={1} className="table table-striped table-bordered ">
                        <tbody >
                        {qna.answers && qna.answers.map(answer =>  (
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
                                                        onClick={(e) => {answerClickDelete(e)}}>삭제</button>

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
                                            onClick={answerSave}> 답변 입력 </button>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>

            {/* 최상단 div   */}
            </div>
        </div>
        </div>

    )
}

export default QnaDetail;