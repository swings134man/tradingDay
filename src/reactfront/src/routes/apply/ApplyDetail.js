import React, {useEffect} from "react";
import {Link, useParams} from "react-router-dom";
import axios from "axios";


function ApplyDetail() {

    const [data, setData] = React.useState([]); //data

    const {applyId} = useParams();

    useEffect(() => {
            const getData = async () => {
                const item = await axios.get('/apply/v1/applyDetail', {
                    params: {
                        applyId: applyId
                    }
                });
                setData(item.data);
                console.log(item.data);
            }
            getData();
    },[]);






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
                            <th scope="row">지원자</th>
                            <td >{data.writer}</td>
                            <th scope='row'>지원자 분야</th>
                            <td scope="row">{data.type}</td>
                            <th scope="row">지원자 레벨</th>
                            <td>{data.level}</td>
                        </tr>
                        <tr>
                            <th scope='row'>지원자 이메일</th>
                            <td colSpan="5">{data.writerEmail}</td>
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

                <div align="right">
                    <button className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}} >
                        <Link to={`/applyAccept/${data.applyId}/${data.title}/${data.writer}/${data.writerEmail}`} style={{color: "white"}}>
                            수락하기
                        </Link>
                    </button>
                    <button  className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100, color: "white"}}>
                        거절하기
                    </button>
                </div>
                {/*  center  */}
            </div>
            {/*  전체area  */}
        </div>
    ) //return
}

export default ApplyDetail;