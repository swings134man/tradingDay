import React, {useState, useEffect, useRef} from 'react';
import TableTest from "../../components/TableTest";
import 'bootstrap/dist/css/bootstrap.css';
import {Link, useNavigate} from "react-router-dom";


import Pagination from "react-js-pagination";
import {authTokenCheck} from "../../components/AuthTokenCheck";

function QnaBoard() {
    const navi = useNavigate();
    const [qnaList, setQnaList] = useState([]);
    const [totalElements, setTotalElements] = useState(0);
    const [page, setPage] = useState(1);
    const [isCheckingBox, setIsCheckingBox] = useState(false)


    useEffect(() => {
        const chkVal = authTokenCheck(localStorage.getItem("auth_token"));
        if(chkVal == null) {
            navi("/member/signin");
        }
    }, []);

    const checkingCheckedBox = (e) => {
        if (e.target.checked) {
            setIsCheckingBox(true);
        } else if(!e.target.checked) {
            setIsCheckingBox(false);
        }
    }

    const handlePageChange = (page) => {
        setPage(page);
    };

    useEffect(() => {
        if(isCheckingBox) {
            const getData = async () => {
                const res = await fetch(`/qna/v1/qnaByWriter?page=${page}&writer=${localStorage.getItem("memberId")}`, {
                    headers: {
                        AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
                    }
                });
                if(res.status === 403) {
                    // 403 에러발생 --> 어스토큰 , 리프레시 토큰 둘다 만료거나 유효하지 않기 때문에 다시 로그인.
                    navi("/member/signin");
                }
                const data = await res.json();
                console.log("나는 부분 조회 data", data)
                setQnaList(data);
                setTotalElements(data.totalElements);
            }
            getData();
        } else {
            const getData = async () => {
                const res = await fetch(`/qna/v1/qnabylist?page=${page}`, {
                    headers: {
                        AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
                    }
                })
                if(res.status === 403) {
                    // 403 에러발생 --> 어스토큰 , 리프레시 토큰 둘다 만료거나 유효하지 않기 때문에 다시 로그인.
                    navi("/member/signin");
                }
                const data = await res.json();
                console.log("나는 전체 조회 data", data)
                setQnaList(data);
                setTotalElements(data.totalElements);
            }
            getData();
        }
    }, [isCheckingBox, page])

    return (

        <div>
            <div align="center" style={{padding : 100}}>
                <div>
                    <h1>문의 게시판</h1>
                </div>

                <TableTest data={qnaList} />
                <div align="right">
                    <button className="btn btn-warning" style={{fontWeight: "bold", color: "white", backgroundColor: "#217Af0", width: 100}}>
                        <Link to={"/qnawrite"} style={{color: "white"}}>
                        문의하기
                        </Link>
                    </button>
                </div>
                <div align="center" >
                    <input type="checkbox" style={{margin: 3, zoom: 1.5}} onClick={checkingCheckedBox}  />
                    <label style={{margin: 3 }}>체크박스 선택시 내가 작성한 글만 볼 수 있습니다.</label>
                </div>
                <Pagination
                    activePage={page} // 현재 페이지
                    itemsCountPerPage={10} // 한 페이지 당 보여줄 아이템 갯수
                    totalItemsCount={totalElements} // 총 아이템 갯수
                    pageRangeDisplayed={5} // paginator의 페이지 범위
                    prevPageText={"‹"} // "이전"을 나타낼 텍스트
                    nextPageText={"›"} // "다음"을 나타낼 텍스트
                    onChange={handlePageChange} // 페이지 변경을 핸들링하는 함수
                />
            </div>
        </div>
    )
}

export default QnaBoard;
