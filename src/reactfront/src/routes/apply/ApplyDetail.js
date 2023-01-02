import React, {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import Loader from "../../components/Loader";


function ApplyDetail() {

    const [data, setData] = React.useState([]); //data

    const {applyId} = useParams();
    const {itemBoard} = useParams();

    const navigate = useNavigate();

    useEffect(() => {
            const getData = async () => {
                try {
                    const item = await axios.get('/apply/v1/applyDetail', {
                        params: {
                            applyId: applyId
                        },
                        headers: {
                            AUTHORIZATION: "Bearer " + localStorage.getItem("auth_token")
                        }
                    });
                    setData(item.data);
                    console.log(item.data);
                }catch (err){
                    if(err.response.status === 403) {
                        navigate("/member/signin");
                    }
                }
            }//const
            getData();
    },[]);

    // loading
    const [loading, setLoading] = useState(false);
    if(loading){
        return <Loader type='spin' color='blue' message={"메일을 발송 중입니다."}/>;
    }


    // 거절 버튼 click
    const onClickReject = () => {
        const email = data.writerEmail;
        const id = data.writer;

        const con = window.confirm("해당 지원을 거절하시겠습니까?\n 다시 수락하실 수 없습니다.");
        if(con === false) {
            return;
        }

        setLoading(true);

        axios.post('/apply/v1/applyReplyReject', {
            itemBoard: itemBoard,
            writerEmail: email,
            memberId: id,
            applyId: applyId
        },{
            headers: {
                AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
            }
        }).then(function (res) {
            if(res.data === false) {
                window.alert("거절 처리 도중 오류가 발생했습니다.");
                setLoading(false);
                return;
            }

            if(res.status === 200) {
                window.alert("해당 지원자에게 거절 이메일을 발송 했습니다!");
                setLoading(false);
            }
        }).catch(function (err){
            if(err.response.status === 403) {
                navigate("/member/signin");
            }
            window.alert("거절 처리 도중 오류가 발생했습니다. "+ err);
            setLoading(false);
        })


    }




    // padding: 5px 1px 2px 3px
    return(
        <div>
            <div align="center" style={{ padding : 50 , paddingRight: 60, paddingLeft: 60, paddingTop: 100, paddingBottom: 60}}>
                <h1>지원서 상세: {data.title}</h1>
                <div align="left">
                    <table className="table table-striped table-bordered table-hover">
                        <tbody>
                        <tr>
                            <th scope="row">지원서 번호</th>
                            <td>{data.applyId}</td>
                            <th scope="row">작성일</th>
                            <td colSpan='4'>{data.createdDate}</td>
                        </tr>
                        <tr>
                            <th scope="row">지원자 아이디</th>
                            <td >{data.writer}</td>
                            <th scope='row'>지원자 분야</th>
                            <td scope="row">{data.type}</td>
                            <th scope="row">지원자 레벨</th>
                            <td>{data.level}</td>
                        </tr>
                        <tr>
                            <th scope='row'>지원자 이메일</th>
                            <td colSpan="3">{data.writerEmail}</td>
                            <th scope='row'>지원서 상태</th>
                            {/*<td colSpan="5">{data.applyStatus}</td>*/}
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
                        </tr>
                        <tr>
                            <th scope="row">제목</th>
                            <td colSpan="5">
                                {data.title}
                            </td>
                        </tr>
                        <tr>
                            <td colSpan="6" height="300">
                                {data.content}
                            </td>
                        </tr>
                        </tbody>
                    </table>

                </div>

                {/* 상태확인 */}
                {
                    data.applyStatus === 'none'
                        ?
                        <div align="right">
                            <button className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}} >
                                <Link to={`/applyAccept/${data.applyId}/${data.title}/${data.writer}/${data.writerEmail}/${data.itemBoard}`} style={{color: "white"}}>
                                    수락하기
                                </Link>
                            </button>
                            <button  onClick={onClickReject} className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}}>
                                거절하기
                            </button>
                        </div>
                        :
                        <div align="center">
                            <button className="btn btn-warning"
                                    style={{backgroundColor: "#217Af0", width: 300, color: "white"}}
                                    disabled={true}>
                                해당 지원은 이미 답변하셨습니다!
                            </button>
                        </div>
                }


                {/*  center  */}
            </div>
            {/*  전체area  */}
        </div>
    ) //return
}

export default ApplyDetail;