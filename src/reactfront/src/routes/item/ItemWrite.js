import React, {useState,useEffect} from "react";
import {useRef} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";

// TODO : 2022.11.17 기준 -> 이미지 인풋(List) 테이블 만들기(테이블 삽입 제목 아래에 tr 하나더 삽입.), post Test 필요. ONLY DTO Test 완료.
function ItemWrite() {



    /*
        input Box Ref
     */
    const titleRef = useRef(null);
    const contentRef = useRef(null);
    const navigate = useNavigate(); //navigate

    const [images, setImages] = useState([]);
    const imageList = [];
    let now = null;

    const onAddImages = (e) => {
        // setImages(e.target.files[0]);
        now = e.target.files;
        console.log("길이는 : " + e.target.files.length);
        for(let i=0; i <= e.target.files.length; i++){
            imageList.push(now[i]);
        }

        // console.log("리스트 ㅡ응 : " + now.length);
        // for (let i = 0; i < now.length; i +=1) {
        //     // imageList.push(now[i]);
        //     setImages(now[i]);
        //     console.log("리스트 ㅡ응 : " + images[i]);
        // }
    }

    /*
        select Box
     */
    const[select, setSelect] = React.useState('');
    const onChangeSelect = (e) => {
        setSelect(e.target.value);
    }

    /*
        post 통신 - 글작성
     */
    const onClick = () => {
        const titleVal = titleRef.current.value;
        const contentVal = contentRef.current.value;

        //분기 처리
        if(titleVal === '') {
            window.alert('제목을 입력하지 않으셨습니다.');
            return;
        }else if(select === '' || select === 'none') {
            window.alert('모집 상태가 선택되지 않았습니다.');
            return;
        }else if(contentVal === '') {
            window.alert('내용을 입력하지 않았습니다.');
            return;
        }else if(select === '모집완료') {
            const confirm = window.confirm('현재 모집 상태가 모집완료입니다. 계속 진행하시겠습니까?');
            if(confirm === false) {
                return;
            }
        }

        // axios 통신
        // TODO : writer 추후 세션값 || 로그인 ID 기반 작성
        const frm = new FormData();
        const data = {
            title: titleVal,
            content: contentVal,
            writer: localStorage.getItem("memberId"),
            type: select};
        frm.append("dto", new Blob([JSON.stringify(data)],  {type: "application/json"}));
        // frm.append("files", imageList.values());
        imageList.forEach((file) => {
            // 파일 데이터 저장
            frm.append('files', file);
        });

        //application/json , multipart/form-data
        axios.post("/item/v1/savePost/images", frm, {
            headers: {
                "Content-Type": `multipart/form-data`,
                AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
            }})
            .then(function (response) {
                // response
                console.log('res : ' + response.status); // 200
                window.alert("게시글이 작성되었습니다!");
                navigate('/item/itemBoard');
            }).catch(function (error) {
            // 오류발생시 실행
            console.log('error message : '+ error);
            if(error.response.status === 403) {
                navigate("/member/signin");
            }
            window.alert("게시글 작성에 실패했습니다. 잠시 후 다시 시도해주세요.");
        }); // axios
    }// onClick

    /*
        onClick
     */
    const onClickCancle = () => {
        const cancleConfirm = window.confirm("작성중이던 모든 내용이 사라집니다.");
        if(!cancleConfirm) {
            return;
        }
        navigate(-1);
    }


    /*
        return 함수.
     */
    return(
        <div>
            <div align="center" style={{padding : 100}}>
                <div >
                    <h1>모집공고 등록</h1>
                </div>
                    <table className="table table-striped table-bordered table-hover">
                        <tbody>
                        <tr>
                            <th scope="row" >작성자</th>
                            <td colSpan="6">{localStorage.getItem("memberId")}</td>
                        </tr>
                        <tr>
                            <th scope="row" >제목</th>
                                <td colSpan="3" width="25%">
                                    <input ref={titleRef} type="text" placeholder="제목을 입력하세요"/>
                                </td>
                            <th scope="row" width="6%">모집 상태</th>
                                <td>
                                    <select value={select} onChange={onChangeSelect}>
                                        <option value='none'>== 선택 ==</option>
                                        <option value='모집중'>모집중</option>
                                        <option value='모집완료'>모집완료</option>
                                    </select>
                                </td>
                        </tr>
                        {/*<tr>*/}
                        {/*    <th scope="row">이미지 업로드</th>*/}
                        {/*    <td colSpan="5">*/}
                        {/*        <input type="file" multiple="multiple" onChange={onAddImages} />*/}
                        {/*    </td>*/}
                        {/*</tr>*/}
                        <tr>
                            <th scope="row">내용</th>
                                <td colSpan="6" className="view_text">
                                    <textarea ref={contentRef} style={{width: 800, height: 300}} placeholder="내용을 입력하세요. (1000자)"></textarea>
                                </td>
                        </tr>
                        </tbody>
                    </table>
                    <div align="right">
                        <button onClick={onClickCancle} className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100}}>작성 취소</button>
                        <button onClick={onClick} className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100}} >
                            게시글 등록
                        </button>
                    </div>
            </div>
        </div>
    )
}//func

export default ItemWrite;