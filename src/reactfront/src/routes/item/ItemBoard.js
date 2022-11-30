import React, {useState, useEffect} from "react";
import {Link} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.css';
import {v4} from 'uuid';
import axios from "axios";

import Paging from "../../components/Paging";
import TableTest from "../../components/TableTest";
import Pagination from "react-js-pagination";

function ItemBoard() {
    const [itemList, setItemList] = React.useState([]); // post
    const [page, setPage] = React.useState(1); // page Data
    let total = 0;

    // paging handler
    const handlePageChange = (page) => {
        setPage(page);
    };

    // use Effect
    useEffect(() => {
    const getData = async () => {
        try {
            const item = await axios.get(`/item/v1/findAllPage`, {
                params: {page}
            });
            setItemList(item.data);
            console.log(itemList);
        } catch (err) {
            console.log(err);
        };
    }
        getData();
    }, [page])//use eff

    /*
        페이징 total count 를 위한 형변환.
     */
    total = itemList.totalElements;
    total *= 1;

    /*
        검색
     */
    // drop down
    const [select, setSelect] = React.useState(''); // 조건 타입 : title, writer
    const onChangeSelect = (e) => {
        setSelect(e.target.value);
        // console.log(e.target.value);
        // console.log(select);
    }
    /*
        검색 키워드
     */
    const [keyWord, setKeyWord] = React.useState(""); // 조건 검색 키워드
    const onChangeKeyWord = (e) => {
        setKeyWord(e.target.value);
    }
    /*
        버튼 클릭 이벤트.
     */
    const onClickBtn = () => {
        if(select === '' || select === 'none'){
            window.alert("검색 타입을 선택하지 않았습니다.");
            return;
        } else if(keyWord == "") {
            window.alert("검색어가 입력되지 않았습니다.");
            return;
        } else if(keyWord == " ") {
            window.alert("공백은 허용되지 않습니다.");
            return;
        }

        const getSearch = async () => {
            try {
                const searchResult = await axios.get('/item/v1/findTitleOrWriter', {
                    params: {
                        keyType: select,
                        keyWord: keyWord
                    }
                });
                setItemList(searchResult.data);
                console.log("검색 : " + itemList);
            }catch (e) {
                console.log(e);
            };
        }//get search
        getSearch();
    }//btn

    /*
        각 컴포넌트 별 이벤트 및 핸들링
     */
    const onKeyPress = (e) => {
        if(e.key == 'Enter') {
            onClickBtn();
        }
    } // enter press

    return (
        <div>
            <div align="center" style={{padding : 100, paddingRight: 330, paddingLeft: 330}}>
                <div>
                    <h1>모집 게시판</h1>
                </div>

                {/*테이블*/}
                <div>
                    <table className="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th scope="col">게시글 번호</th>
                            <th scope="col">제목</th>
                            <th scope="col">아이디</th>
                            <th scope="col">모집 상태</th>
                            <th scope="col">작성 날짜</th>
                            <th scope="col">조회수</th>
                        </tr>
                        </thead>
                        <tbody>
                        {itemList.content && itemList.content.map(data => (
                            <tr key={v4()}>
                                <td>
                                    {data.id}
                                </td>
                                <td>
                                    <Link to={`/itemDetail/${data.id}`}> {data.title} </Link>
                                </td>
                                <td>
                                    {data.writer}
                                </td>

                                {/* 모집 상태 IF 조건문 */}
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

                <div align="right" style={{paddingBottom:50}}>
                    <button className="btn btn-warning" style={{fontWeight: "bold", color: "white", backgroundColor: "#217Af0", width: 100}}>
                        <Link to={"/itemWrite"} style={{color: "white"}}>
                            글작성
                        </Link>
                    </button>
                </div>

                <div align="center">
                    <select value={select} onChange={onChangeSelect} className="size num1" style={{width:120}}>
                        <option value='none'>== 선택 ==</option>
                        <option value='title'>제목</option>
                        <option value='writer'>작성자</option>
                    </select>

                    <input id="keyWord" type="text" placeholder="작성자 혹은 제목을 입력." onChange={onChangeKeyWord} onKeyPress={onKeyPress} className="size" style={{width:350}}/>
                    <button onClick={onClickBtn} className="btn btn-warning" style={{fontWeight: "bold", color: "white", backgroundColor: "#217Af0", width: 100}}>
                        검색
                    </button>
                </div>

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
    )//return
}// func

export default ItemBoard;