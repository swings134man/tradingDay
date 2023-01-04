import React from "react";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect} from "react";
import axios from "axios";



function NoteDetail() {

    const [data, setData] = React.useState([]); //data
    const navigate = useNavigate();

    const {noteNo} = useParams(); // 쪽지 번호
    const {memberId} = useParams(); // 앞에서 받아온 아이디

    useEffect(() => {
        const getData = async () => {
            try {
                const item = await axios.get('/note/v1/auth/noteDetail', {
                    params: {
                        noteNo: noteNo
                    },
                    headers: {
                        AUTHORIZATION: "Bearer " + localStorage.getItem("auth_token")
                    }
                });
                setData(item.data);
            }catch (err){
                if(err.response.status === 403) {
                    navigate("/member/signin");
                }
            }
        }//const
        getData();
    },[]);

    // TODO : localstoarge id와 === memberId 변수 비교 해서 사용자가 아니면 return


    /*
        각 버튼 이벤트
     */
    const onClickPre = () => {
        navigate("/noteBoardList/"+ memberId);
    }

    // 답장
    const onClickReply = () => {
        const receiveIdParam = data.senderMemberId;
        navigate("/noteWrite/" + receiveIdParam);
    }

    // 쪽지 삭제
    const onClickDel = () => {
        let confirm = window.confirm("쪽지를 삭제하시겠습니까?");
        if(!confirm) {
            return;
        }

        // TODO : DEL axios 구현
        axios.delete('/note/v1/auth/delete?data='+noteNo ,{
            headers: {
                AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
            }
        }).then(function (res) {
            if(res.status === 200) {
                window.alert("쪽지가 삭제되었습니다.");
                navigate("/noteBoardList/"+memberId);
                // window.location.reload();
            }
        }).catch(function (err) {
            if(err.response.status === 403) {
                navigate("/member/signin");
            }
            window.alert("삭제 도중 오류가 발생했습니다. " + err);
        });
    }

    return (
        <div>
            <div align="center" style={{padding: 100, paddingBottom: 20}}>
                <div align="left">
                    <button  onClick={onClickPre}  className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}}>
                        이전으로
                    </button>
                </div>

                <h3>받은 쪽지</h3>
                <table className="table table-striped table-bordered table-hover" style={{width: 1000}}>
                    <tbody>
                    <tr>
                        <th scope="col" width="10%">받는 사람</th>
                        <td>
                            {data.receiveMemberId}
                        </td>
                    </tr>
                    <tr>
                        <th scope="col" width="10%">보낸 사람</th>
                        <td>
                            {data.senderMemberId}
                        </td>
                    </tr>
                    <tr>
                        <th scope="col">보낸 날짜</th>
                        <td>
                            {data.createdDate}
                        </td>
                    </tr>
                    <tr>
                        <th scope="col" width="10%">제목</th>
                        <td>
                            {data.title}
                        </td>
                    </tr>
                    <tr>
                        <th scope="col" colSpan="2" width="10%">내용</th>
                    </tr>
                    <tr>
                        <td colSpan="2" className="view_text" height="300">
                            {data.content}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            {/*  버튼  */}
            <div align="center">
                <button  onClick={onClickDel} className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}}>
                    삭제
                </button>
                <button onClick={onClickReply} className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}} >
                    답장하기
                </button>
            </div>
        </div>
    )
}

export default NoteDetail;