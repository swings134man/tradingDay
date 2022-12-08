import React, {useEffect} from "react";
import {Link, useParams} from "react-router-dom";
import Pagination from "react-js-pagination";
import {v4} from 'uuid';
import axios from "axios";

function ApplyBoardList() {

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
                        memberId: memberId,
                        pageable: page
                    }
                });
                setApplyList(list.data);
                console.log(list.data);
            } catch (err) {
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





    return (
        <div>
            <div align="center" style={{padding : 100, paddingRight: 330, paddingLeft: 330}}>
                <div>
                    <h1>지원서 목록</h1>
                </div>

                {/*테이블*/}
                <div>
                    <table className="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th scope="col">지원서 번호</th>
                            <th scope="col">제목</th>
                            <th scope="col">지원자 아이디</th>
                            <th scope="col">지원 날짜</th>
                        </tr>
                        </thead>
                        <tbody>
                        {applyList.content && applyList.content.map(data => (
                            <tr key={v4()}>
                                <td>
                                    {data.applyId}
                                </td>
                                <td>
                                    <Link to={`/applyDetail/${data.applyId}`}> {data.title} </Link>
                                </td>
                                <td>
                                    {data.writer}
                                </td>
                                <td>
                                    {data.createdDate}
                                </td>
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
}


export default ApplyBoardList;