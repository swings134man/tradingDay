import React, {useEffect} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import Pagination from "react-js-pagination";
import {v4} from 'uuid';
import axios from "axios";
import {useState} from "react";
import Note from "../../components/Note";

function ApplyBoardList() {

    const navigate = useNavigate();
    const [applyList, setApplyList] = React.useState([]); //data
    const [page, setPage] = React.useState(1) // page Data
    let total = 0;

    const {memberId} = useParams();

    // paging handler
    const handlePageChange = (page) => {
        setPage(page);
    }

    //use Effect - 초기
    useEffect(() => {
        const getData = async () => {
            try{
                const list = await axios.get('/apply/v1/findByWriter', {
                    params: {
                        memberId: localStorage.getItem("memberId"),
                        pageable: page
                    },
                    headers: {
                        AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
                    }
                })
                setApplyList(list.data);
                console.log(list.data);
            } catch (err) {
                if(err.response.status === 403) {
                    navigate("/member/signin");
                }
                console.log(err);
            }
        }

        getData();
    }, [page])

    /*
    페이징 total count 를 위한 형변환.
    */
    total = applyList.totalElements;
    total *= 1;


    // 쪽지 스테이트
    const [note, setNote] =useState(false);
    const [selectIndex, setSelectIndex] = useState(0);
    const [click, setClick] = useState(false);

    // 쪽지 show hide , 데이터 세팅
    // TODO : 01.03 데이터 넘어온걸로 Note.JS 에 파라미터 던질것. + 팝업 처리 해야함
    const noteClick = ((e) => {
        setSelectIndex(e.target.getAttribute("data-id")); // index
        const strWriter = e.target.getAttribute("data-writer") // writer String 값

        // console.log(selectIndex);
        // console.log(strWriter);

        // Click Show & hide
        if(click === false) {
            setNote(true);
            setClick(true);
        }else {
            setNote(false);
            setClick(false);
        }

    });

    return (
        <div className="d-flex  justify-content-center">
            {/*<div align="center" style={{padding : 100, paddingRight: 330, paddingLeft: 330}}>*/}
            <div>

                <div className="d-flex  justify-content-center">
                    <h1>지원서 목록</h1>
                </div>

                {/*테이블*/}
                <div className="d-flex  justify-content-center">
                    <table className="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th scope="col">지원서 번호</th>
                            <th scope="col">제목</th>
                            <th scope="col">지원자 아이디</th>
                            <th scope="col">지원 날짜</th>
                            <th scope="col">지원서 상태</th>
                        </tr>
                        </thead>
                        <tbody>
                        {applyList.content && applyList.content.map(data => (
                            <tr key={v4()}>
                                <td>
                                    {data.applyId}
                                </td>
                                <td>
                                    <Link to={`/applyDetail/${data.applyId}/${data.itemBoard}`}> {data.title} </Link>
                                </td>
                                <td>
                                    {/*{data.writer}*/}
                                    <div >
                                        <a href={"#!"} onClick={noteClick} data-id={data.applyId} data-writer={data.writer}>
                                            {data.writer}
                                        </a>

                                        {/*--------------- 컴포넌트 보여 주기 -------------*/}
                                        <div>
                                            <div style={{paddingLeft: 30}}>
                                                {note && selectIndex == data.applyId ? <Note /> : null }
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    {data.createdDate}
                                </td>
                                    {/* 상태확인 */}
                                    {
                                        data.applyStatus === 'accept'
                                            ?
                                            <td style={{color:"green"}}>
                                                수락함
                                            </td>
                                            :( data.applyStatus === 'denied'
                                                ?
                                                    <td style={{color:"red"}}>
                                                        거절
                                                    </td>
                                                : <td style={{color:"gray"}}>
                                                        답변안함
                                                    </td>
                                            )
                                    }
                            </tr> ))}
                        </tbody>
                    </table>
                </div>

                {/*<div align="right" style={{paddingBottom:50}}>*/}
                {/*    <button className="btn btn-warning" style={{fontWeight: "bold", color: "white", backgroundColor: "#217Af0", width: 100}}>*/}
                {/*        <Link to={"/itemWrite"} style={{color: "white"}}>*/}
                {/*            글작성*/}
                {/*        </Link>*/}
                {/*    </button>*/}
                {/*</div>*/}

                {/* 검색 */}
                {/*<div align="center">*/}
                {/*    <select value={select} onChange={onChangeSelect} className="size num1" style={{width:120}}>*/}
                {/*        <option value='none'>== 선택 ==</option>*/}
                {/*        <option value='title'>제목</option>*/}
                {/*        <option value='writer'>작성자</option>*/}
                {/*    </select>*/}

                {/*    <input id="keyWord" type="text" placeholder="작성자 혹은 제목을 입력." onChange={} onKeyPress={} className="size" style={{width:350}}/>*/}
                {/*    <button onClick={} className="btn btn-warning" style={{fontWeight: "bold", color: "white", backgroundColor: "#217Af0", width: 100}}>*/}
                {/*        검색*/}
                {/*    </button>*/}
                {/*</div>*/}

                <div className="d-flex  justify-content-center">
                {/*  paging  */}
                <Pagination
                    activePage={page} // 현재 페이지
                    itemsCountPerPage={10} // 한 페이지 당 보여줄 아이템 갯수
                    totalItemsCount={total} // 총 아이템 갯수
                    pageRangeDisplayed={5} // paginator의 페이지 범위
                    prevPageText={"‹"} // "이전"을 나타낼 텍스트
                    nextPageText={"›"} // "다음"을 나타낼 텍스트
                    onChange={handlePageChange} // 페이지 변경을 핸들링하는 함수
                />
                </div>
            </div>
        </div>
    )//return
}


export default ApplyBoardList;