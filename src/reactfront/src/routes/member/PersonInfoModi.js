import { useNavigate } from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";



function PersonInfoModi( confirm ) {
    console.log(confirm)
    const [pwdConfirm, setPwdConfirm] = useState();
    const navigate = useNavigate();

    // useEffect(() => {
        if(confirm === true) {
            setPwdConfirm(true);
            console.log('pwdConfirm',pwdConfirm)
        }
       if(confirm === true) {
           const getData = async () => {
               const data = await axios.get(`/member/v1/findbymemberid`, {
                   memberId: localStorage.getItem('memberId')
               }).then(function (res) {
                   console.log(res);
               }).catch(function (err) {
                   console.log(err)
               })
           }
       }

    //    }
    // }, [pwdConfirm]);










    return(
        <div>
            <table className="table table-striped table-bordered " >
                <tbody style={{width: 2000}} >
                <tr>
                    <th  colSpan={3}>회원아이디</th>
                    <td></td>
                </tr>
                <tr>
                    <th scope="row">비밀번호</th>
                    <td>
                        <input type="password" maxLength={16} placeholder="변경시에만 입력해주세요."/>
                    </td>
                    <td >
                        <b>비밀번호 확인</b>
                    </td>
                    <td>
                        <input type="password" maxLength={16} placeholder="비밀번호 확인"/>
                    </td>
                </tr>
                <tr>
                    <th colSpan={3}>이름</th>
                    <td colSpan="2" ></td>

                </tr>
                <tr>
                    <th scope="row">핸드폰번호</th>
                    <td colSpan="3">
                        <input type="text" id="title" name="title" style={{width: 500}} />
                    </td>
                </tr>
                <tr>
                    <th scope="row">주소</th>

                </tr>
                </tbody>
            </table>
        </div>
    )
}


export default PersonInfoModi;