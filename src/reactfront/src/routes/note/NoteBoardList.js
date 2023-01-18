import React from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import {v4} from 'uuid';
import Note from "../../components/Note";
import Pagination from "react-js-pagination";
import data from "bootstrap/js/src/dom/data";


function NoteBoardList() {

    const navigate = useNavigate();

    const [noteList, setNoteList] = React.useState([]); //data
    const [page, setPage] = React.useState(1) // page Data
    let total = 0;

    // const {memberId} = useParams();
    // console.log(memberId);
    const memberId = localStorage.getItem("memberId");

    // paging handler
    const handlePageChange = (page) => {
        setPage(page);
    }

    //use Effect - 초기
    useEffect(() => {
        const getData = async () => {
            try{
                const list = await axios.get('/note/v1/auth/findByReceivePage', {
                    params: {
                        receiveMemberId: memberId,
                        pageable: page
                    },
                    headers: {
                        AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
                    }
                })
                setNoteList(list.data);
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
    total = noteList.totalElements;
    total *= 1;


    // 쪽지 스테이트
    const [note, setNote] =useState(false);
    const [selectIndex, setSelectIndex] = useState(0);
    const [click, setClick] = useState(false);

    // 쪽지 show hide , 데이터 세팅
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

    /*
        체크 박스
     */
    // 체크 배열
    const [checkItems, setCheckItems] = React.useState([]);

    // 체크박스 전체 선택
    const handleAllCheck = (checked) => {
        console.log(checked);

        if(checked) {
            const noArray = [];
            noteList.content.forEach((el) => noArray.push(el.noteNo));
            setCheckItems(noArray);
        }else {
            // 빈 배열
            setCheckItems([]);
        }
    }
    // 체크박스 단일 선택
    const handleSingleCheck = (checked, noteNo) => {
        if(checked){
            setCheckItems(prev => [...prev, noteNo])
        }else {
            setCheckItems(checkItems.filter((el) => el !== noteNo));
        }
    }

    /*
        삭제
     */
    const delContent = () => {
        console.log(checkItems);
        if(checkItems.length === 0) {
            alert("삭제할 쪽지를 선택해주세요");
            return;
        }

        const conf = window.confirm("선택한 쪽지를 삭제하시겠습니까?");
        if(conf === false) {
            return;
        }

        axios.delete('/note/v1/auth/delete?data=' + checkItems,{
            headers: {
                AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
            }
        }).then(function (res) {
            if(res.status === 200) {
                window.alert("쪽지가 삭제되었습니다.");
                navigate("/noteBoardList/"+memberId);
                window.location.reload();
            }
        }).catch(function (err) {
            if(err.response.status === 403) {
                navigate("/member/signin");
            }
            window.alert("삭제 도중 오류가 발생했습니다. " + err);
        });
    }

    return (
        <div className="d-flex  justify-content-center">
            {/*<div align="center" style={{padding : 100, paddingRight: 330, paddingLeft: 330}}>*/}
            <div style={{paddingTop: 100}}>
                <div className="d-flex  justify-content-center">
                    <h1>쪽지 목록</h1>
                </div>

                {/*테이블*/}
                <div className="d-flex  justify-content-center">
                    <table className="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>
                                <input type="checkbox" name="select-all"
                                onChange={(e) => handleAllCheck(e.target.checked)}
                                defaultChecked={checkItems.length === noteList.length ? true : false}
                                />
                            </th>
                            <th scope="col">쪽지 번호</th>
                            <th scope="col">제목</th>
                            <th scope="col">보낸사람</th>
                            <th scope="col">쪽지 발신 날짜</th>
                            <th scope="col">쪽지 상태</th>
                        </tr>
                        </thead>
                        <tbody>
                        {noteList.content && noteList.content.map(data => (
                            // <tr key={v4()} style={data.status === true ? {color: "gray"} : {color: "blue"}}>
                            <tr key={v4()}>
                                {/*체크 박스*/}
                                <td>
                                    <input type="checkbox" name={'select-${noteList.noteNo}'}
                                        onChange={(e) => handleSingleCheck(e.target.checked, data.noteNo)}
                                        defaultChecked={checkItems.includes(data.noteNo) ? true : false}
                                    />
                                </td>

                                {/*아래부터 내용*/}
                                <td>
                                    {data.noteNo}
                                </td>
                                <td>
                                    <Link to={`/noteDetail/${data.noteNo}/${memberId}`}> {data.title} </Link>
                                </td>
                                <td>
                                    {/*{data.writer}*/}
                                    <div >
                                        <a href={"#!"} onClick={noteClick} data-id={data.noteNo} data-writer={data.senderMemberId}>
                                            {data.senderMemberId}
                                        </a>

                                        {/*--------------- 컴포넌트 보여 주기 -------------*/}
                                        <div>
                                            <div style={{paddingLeft: 30}}>
                                                {note && selectIndex == data.noteNo ? <Note memberId={data.senderMemberId}/> : null }
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    {data.createdDate}
                                </td>
                                {/* 상태확인 */}
                                {
                                    data.status === true
                                        ?
                                        <td style={{color:"gray"}}>
                                            읽음
                                        </td>
                                        :<td style={{color:"blue"}}>
                                            읽지않음
                                        </td>
                                }
                            </tr> ))}
                        </tbody>
                    </table>
                </div>

                <div align="right" style={{paddingBottom:50}}>
                    <button onClick={delContent} className="btn btn-warning"  style={{fontWeight: "bold", color: "white", backgroundColor: "#217Af0", width: 100}}>
                        쪽지 삭제
                    </button>
                </div>

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

export default NoteBoardList;