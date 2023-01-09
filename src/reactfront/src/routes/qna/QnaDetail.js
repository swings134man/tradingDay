import React, {useEffect, useRef, useState} from "react";
import {Link, useParams} from "react-router-dom";
import { useNavigate } from "react-router-dom";
import {v4} from 'uuid';
import axios from "axios";
import {render} from "react-dom";
import {manageQuarter} from "../../components/RoleQuarterUtil";


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
    const [userRole, setUserRole] = useState("");

    let answerId = 0;
    useEffect(() => {
        //pk로 조회 후 단건 세팅
        const getData = async () => {
            const uri = `/qna/v1/findbyqnaid?qnaId=${qnaId}`;
            const encoded = encodeURI(uri);
            const json = await (
                await fetch(encoded, {
                    headers: {
                        AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
                    }
                })
            ).json();
            if(getData.status === 403) {
                // 403 에러발생 --> 어스토큰 , 리프레시 토큰 둘다 만료거나 유효하지 않기 때문에 다시 로그인.
                navigate("/member/signin");
            }
            setDate(json.createdDate.substring(0, 10));
            setQna(json);
        }
        getData();
        // 답글, 답글 수정 삭제 버튼 분기를 위한 처리
        setUserRole(manageQuarter(localStorage.getItem("userRole")));
    },[]);

    // 재조회용 함수
    const reSearch = async () => {
            const uri = `/qna/v1/findbyqnaid?qnaId=${qnaId}`;
            const encoded = encodeURI(uri);
            const json = await (
                await fetch(encoded, {
                    headers: {
                        AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
                    }
                })
            ).json();
            if(reSearch.status === 403) {
                // 403 에러발생 --> 어스토큰 , 리프레시 토큰 둘다 만료거나 유효하지 않기 때문에 다시 로그인.
                navigate("/member/signin");
            }
            setDate(json.createdDate.substring(0, 10));
            setQna(json);
        }

    // 답변달기
    const answerSave = async () => {

        axios.post("/answer/v1/save", {
            qnaId: qna.qnaId,
            content: answerRef.current.value,
            writer : "tradingManager"
        },
        {
            headers: {
                AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
            }
        }).then(function (response) {
                if(response.status === 403) {
                    navigate("/member/signin");
                }
                //재조회
                reSearch();
                //textarea 빈값 설정
                answerRef.current.value = "";
            }).catch(function (error) {
            // 오류발생시 실행
            console.log('error message : '+ error);
            window.alert("답변 등록에 실패했어요.");
        }); // axios
    }


    // 문의글 삭제 이벤트
    const onRemove = async () => {
        const confirm = window.confirm("해당 문의를 삭제하시겠습니까?");
        if(confirm === true) {
                const uri = `/qna/v1/deleteqna?qnaId=${qnaId}`;
                const encoded = encodeURI(uri);
                const json = await (
                    await fetch(encoded, {
                        method: 'DELETE',
                        headers: {
                            'Content-Type': 'application/json',
                             AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
                        }})
                ).json();
                if(json === 1 ) {
                    navigate('/qna/qnaboard');
                } else if(json.status === 403) {
                    navigate("/member/signin");
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
                         AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
                    }})
            ).json();
            if(json.status === 403) {
                navigate("/member/signin");
            }
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

        console.log(answerId);
        axios.put(`http://localhost:8080/answer/v1/update`, {
            id: answerId,
            qnaId: qnaId,
            content: modiAnswerVal,
            writer: "tradingManager",
        },
        {
            headers: {
                'Content-Type': 'application/json',
                AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
            }
        })
        .then(function (res){
            if(res.status === 403) {
                navigate("/member/signin");
            }
            console.log('업데이트 성공')
            setModiTextArea(false);
            reSearch();
        })
        .catch(function (err) {
            alert("수정에 실패했어요")
           console.log('실패 ')
        });
    }
    return (
        <div>
            <div align="center" style={{ padding : 50}}>
                <h1>문의 게시판</h1>
                <div align="left">
                    <table className="table table-striped table-bordered table-hover">
                        <colgroup>
                            <col width="150px"/>
                            <col/>
                        </colgroup>
                        <tbody>
                        <tr align="center">
                            <td colSpan={2}>
                                {qna.title}
                            </td>
                        </tr>
                        <tr >
                            <td align="right" colSpan={2}> {date} <br/>{qna.writer}
                            </td>
                        </tr>
                        <tr >
                            <td align="center">
                                <div style={{padding: 40}}>{qna.content}</div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div align="right">
                        <button className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}}>
                            <Link to={`/qnaupdate/${qna.qnaId}/${qna.title}/${qna.writer}/${qna.content}/${qna.createdDate}`}
                            style={{color: "white"}}> 게시글 수정 </Link>
                        </button>
                    <button className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}} onClick={onRemove}>게시글 삭제</button>
                </div>

                <div align="center" style={{ padding : 75}}>
                    <hr />
                    {/*입력 form ---------------분기*/}
                    {userRole === "admin" ?
                        <table border={1} style={{margin: 20}}>
                            <tbody>
                            <tr height="50">
                                <td width="10%" className="col1" align="center">댓글 달기</td>
                                <td width="90%" className="col2">
                                    <div style={{float: "left"}}>
                                        <textarea style={{width: 800, height: 100}} ref={answerRef}></textarea>
                                    </div>
                                    <div style={{
                                        float: "left",
                                        paddingTop: 34,
                                        paddingRight: 12,
                                        paddingLeft: 34,
                                        paddingBottom: 12
                                    }}>
                                        <button className="btn btn-warning"
                                                style={{backgroundColor: "#217Af0", width: 100, color: "white"}}
                                                onClick={answerSave}> 답변 입력
                                        </button>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                     : null}
                    {userRole !== "admin" ?
                        <table border={1} className="table table-bordered ">
                            <tbody>
                            {qna.answers && qna.answers.map(answer => (
                                <tr key={v4()}>
                                    <td>
                                        <div border={1}>
                                            <div style={{backgroundColor: "#EBEBEB"}}>
                                                <div style={{
                                                    padding: 10,
                                                    fontWeight: "bold"
                                                }}> {answer.writer} {writeDate = answer.createdDate.substring(0, 10)}</div>
                                            </div>
                                        </div>
                                        <div style={{padding: 10}}>
                                            {answer.content}
                                        </div>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table> : null}



                    {userRole === "admin" ?
                        <table border={1} className="table table-bordered ">
                            <tbody>
                            {qna.answers && qna.answers.map(answer => (
                                <tr key={v4()}>
                                    <td>
                                        <div border={1}>
                                            <div style={{backgroundColor: "#EBEBEB"}}>
                                                <div style={{
                                                    padding: 10,
                                                    fontWeight: "bold"
                                                }}> {answer.writer} {writeDate = answer.createdDate.substring(0, 10)}</div>
                                            </div>
                                        </div>
                                        <div style={{padding: 10}}>
                                            {answer.content}
                                            <div></div>
                                            {modiTextArea && selectIndex == answer.id ? (
                                                <div style={{float: "left"}}>
                                                    <textarea style={{width: 800, height: 100}}
                                                              placeholder="수정내용을 입력하세요" ref={modiAnswerRef}/>

                                                    <div align="right">
                                                        <button className="btn btn-warning"
                                                                style={{
                                                                    backgroundColor: "#217Af0",
                                                                    width: 45,
                                                                    height: 25,
                                                                    color: "white",
                                                                    fontSize: 10
                                                                }}
                                                                data-id={answer.id}
                                                                onClick={answerClickUpdate}
                                                        > 수정
                                                        </button>
                                                        <button className="btn btn-warning"
                                                                style={{
                                                                    backgroundColor: "#217Af0",
                                                                    width: 70,
                                                                    height: 25,
                                                                    color: "white",
                                                                    fontSize: 10
                                                                }}
                                                                onClick={modiInputShow}> 수정취소
                                                        </button>
                                                    </div>
                                                </div>
                                            ) : (
                                                // {userRole === "admin" ? }
                                                <div align="right">
                                                    <button className="btn btn-warning"
                                                            style={{
                                                                backgroundColor: "#217Af0",
                                                                width: 45,
                                                                height: 25,
                                                                color: "white",
                                                                fontSize: 10
                                                            }}
                                                            data-id={answer.id}
                                                            onClick={(e) => {
                                                                answerClickDelete(e)
                                                            }}>삭제
                                                    </button>

                                                    <button className="btn btn-warning"
                                                            style={{
                                                                backgroundColor: "#217Af0",
                                                                width: 45,
                                                                height: 25,
                                                                color: "white",
                                                                fontSize: 10
                                                            }}
                                                            data-id={answer.id}
                                                            onClick={modiInputShow}>수정
                                                    </button>
                                                </div>
                                            )}
                                        </div>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table> : null }
                            </div>
                        </div>
                    </div>
        )
    }

export default QnaDetail;