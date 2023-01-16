import {useRef} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

function MemberSecession() {
    const idRef = useRef(null);

    const logout = () => {
        axios.post('/logout')
            .then(function res(response){})
    }

    function deleteMember() {
        let memberId = idRef.current.value;
        console.log(memberId)
        if(memberId.search(/\W|\s/g) > -1) {
            idRef.current.focus();
            alert("아이디에는 공백이나 특수문자가 들어갈수없어요.");
        } else if(!memberId) {
            idRef.current.focus();
            alert("아이디를 입력하세요");
        } else if(memberId.length < 4 || memberId.length > 12) {
            idRef.current.focus();
            alert('아이디는 4글자이상 12자 이하로 입력해주세요');
        } else if(localStorage.getItem("memberId") !== memberId) {
            alert('아이디를 확인 후 다시 입력 해주세요.');
        } else {
            let deleteConfirm = window.confirm('아이디를 삭제하시겠어요 ?');
            if(deleteConfirm === true) {
                const url = "/member/v1/memberdelete"
                axios.delete(url, {
                headers : {
                    AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
                },
                data: {
                    memberId: memberId
                },
                withCredentials: true,
                }).then(function (res) {
                    console.log(res);

                    if(res.data === 1) {
                        localStorage.removeItem("auth_token");
                        localStorage.removeItem("memberId");
                        localStorage.removeItem("userRole");
                        window.location.assign("/");
                    } else {
                        alert("탈퇴중 문제가 생겼어요. 지속적인 문제 발생시 관리자에게 문의하세요")
                    }
                }).catch(function (err) {
                    console.log(err)
                    alert("탈퇴중 문제가 발생했어요 관리자에게 문의해주세요.")
                });
            }
        } // else
    } // function


    return (
        <div>
            <div className="d-flex justify-content-center" style={{paddingBottom: 10}}>
                <input type="text"
                       ref={idRef}
                       maxLength={12}
                       style={{width:300}}
                       placeholder="아이디를 입력하세요"
                />
            </div>
            <div className="d-flex justify-content-center">
                <div>
                    <button type="button"
                            className="btn"
                            style={{backgroundColor: "#217Af0"}}
                            onClick={ () => {
                                deleteMember()
                                logout()
                            } }> 탈퇴하기 </button>
                </div>
            </div>
        </div>
    )
}

export default MemberSecession;