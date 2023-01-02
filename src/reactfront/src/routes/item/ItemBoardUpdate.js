import {useParams} from "react-router-dom";
import {useEffect, useState, useRef} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import React from "react";

function ItemBoardUpdate() {
    const {id, title, writer, content, createdDate} = useParams(); //param
    const titleRef = useRef(null);
    const contentRef = useRef(null);
    const navigate = useNavigate();
    let date = "";

    // 셀렉박스
    const[select, setSelect] = React.useState('');
    const onChangeSelect = (e) => {
        setSelect(e.target.value);
    }

    const onClickUpdate = () => {
        const titleVal = titleRef.current.value;
        const contentVal = contentRef.current.value;
        const idVal = id;

        const inputPattern = /^\s+|\s+$/g;
        if(titleVal.replace(inputPattern, '' ) === "" ){
            alert('제목엔 공백만 입력할수없어요.');
            return;
        } else if(contentVal.replace(inputPattern, '' ) === "") {
            alert('내용엔 공백만 입력할수없어요.')
            return;
        } else if(select === '' || select === 'none') {
            window.alert('모집상태가 선택되지 않았습니다!');
            return;
        }

        axios.put('/item/v1/updatePost', {
                id: idVal,
                title: titleVal,
                content: contentVal,
                type: select
        },{
                headers: {
                    AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
                }
            }).then(function (res) {
                window.alert('게시글 수정 완료');
                navigate('/itemDetail/'+idVal);
            }).catch(function (err){
                console.log(err);
                if(err.response.status === 403) {
                    navigate("/member/signin");
                }
                window.alert('게시글 수정중 문제가 발생했습니다.');
            })
    }




    return (
        <div>
            {/*<div align="center" style={{padding : 100, paddingRight: 330, paddingLeft: 330, }}>*/}
            <div align="center" style={{padding : 100}}>
                <div >
                    <h1>모집 글 수정</h1>
                </div>
                    <table className="table table-striped table-bordered table-hover">
                        <tbody>
                        <tr>
                            <th scope="row">글 번호</th>
                            <td colSpan="4">{id}</td>

                        </tr>
                        <tr>
                            <th scope="row">작성일</th>
                            <td colSpan="4">{date = createdDate.substring(0, 10)}</td>
                        </tr>
                        <tr>
                            <th scope="row">작성자</th>
                            <td colSpan="4" >{writer}</td>

                        </tr>
                        <tr>
                            <th scope="row">제목</th>
                            <td colSpan="0" style={{width:500}}>
                                <input type="text" id="title" name="title" defaultValue={title} ref={titleRef} style={{width: 500}} />
                            </td>

                            <th scope="row">모집상태</th>
                            <td>
                                <select value={select} onChange={onChangeSelect}>
                                    <option value='none'>== 선택 ==</option>
                                    <option value='모집중'>모집중</option>
                                    <option value='모집완료'>모집완료</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">내용</th>
                            <td colSpan="4" className="view_text">
                                <textarea style={{width: 500, height: 200}} ref={contentRef}  title="내용"  name="contents" defaultValue={content}></textarea>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div align="right">
                        <button className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100}} onClick={() => navigate(-1)}> 수정 취소 </button>
                        <button className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100}} onClick={onClickUpdate}> 수정하기 </button>
                    </div>
            </div>
        </div>
    )
}

export default ItemBoardUpdate;