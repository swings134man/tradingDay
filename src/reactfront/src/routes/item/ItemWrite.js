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

        // console.log('제목 : ' + titleRef.current.value);
        // console.log('내용 : ' + contentRef.current.value);
        // console.log('이미지 : ' + images);

        //분기 처리
        if(titleVal === '') {
            window.alert('제목을 입력하지 않으셨습니다.');
            return;
        }else if(select === '' || select === 'none') {
            window.alert('제품 상태가 선택되지 않았습니다.');
            return;
        }else if(contentVal === '') {
            window.alert('내용을 입력하지 않았습니다.');
            return;
        }

        // axios 통신
        // TODO : writer 추후 세션값 || 로그인 ID 기반 작성
        // axios.post("/item/v1/savePost", {
        //     title: titleVal,
        //     content: contentVal,
        //     writer: "iu",
        //     type: select
        // })
        //     .then(function (response) {
        //         // response
        //         console.log('res : ' + response.status); // 200
        //         window.alert("게시글이 작성되었습니다!");
        //         navigate('/item/itemBoard');
        //     }).catch(function (error) {
        //         // 오류발생시 실행
        //         console.log('error message : '+ error);
        //         window.alert("게시글 작성에 실패했습니다. 잠시 후 다시 시도해주세요.");
        // }); // axios

        // image test
        const frm = new FormData();
        const data = {
            title: titleVal,
            content: contentVal,
            writer: "iu",
            type: select};
        frm.append("dto", new Blob([JSON.stringify(data)],  {type: "application/json"}));
        // frm.append("files", imageList.values());
        imageList.forEach((file) => {
            // 파일 데이터 저장
            frm.append('files', file);
        });


    //application/json  multipart/form-data
        axios.post("/item/v1/savePost/images2", frm, {
            headers: {
                "Content-Type": `multipart/form-data`,
            }})
            .then(function (response) {
                // response
                console.log('res : ' + response.status); // 200
                window.alert("게시글이 작성되었습니다!");
                navigate('/item/itemBoard');
            }).catch(function (error) {
            // 오류발생시 실행
            console.log('error message : '+ error);
            window.alert("게시글 작성에 실패했습니다. 잠시 후 다시 시도해주세요.");
        }); // axios
    }// onClick




    /*
        return 함수.
     */
    return(
        <div>
            <div align="center" style={{padding : 100}}>
                <div >
                    <h1>상품 등록</h1>
                </div>
                    <table className="table table-striped table-bordered table-hover">
                        <tbody>
                        <tr>
                            {/*TODO : td 작성자 부분 삭제 혹은 로그인 기반 데이터 입력*/}
                            <th scope="row" >작성자</th>
                            <td colSpan="6">iu</td>
                        </tr>
                        <tr>
                            <th scope="row" >제목</th>
                                <td colSpan="3" width="25%">
                                    <input ref={titleRef} type="text" placeholder="제목을 입력하세요"/>
                                </td>
                            <th scope="row" width="6%">제품 상태</th>
                                <td>
                                    <select value={select} onChange={onChangeSelect}>
                                        <option value='none'>== 선택 ==</option>
                                        <option value='신품'>신품</option>
                                        <option value='중고'>중고</option>
                                    </select>
                                </td>
                        </tr>
                        <tr>
                            <th scope="row">이미지 업로드</th>
                            <td colSpan="5">
                                <input type="file" multiple="multiple" onChange={onAddImages} />
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">내용</th>
                                <td colSpan="6" className="view_text">
                                    <textarea ref={contentRef} style={{width: 800, height: 300}} placeholder="내용을 입력하세요. (1000자)"></textarea>
                                </td>
                        </tr>
                        </tbody>
                    </table>
                    <div align="right">
                        <button onClick={onClick} className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100}} >
                            상품등록
                        </button>
                    </div>
            </div>

        </div>
    )
}//func

export default ItemWrite;