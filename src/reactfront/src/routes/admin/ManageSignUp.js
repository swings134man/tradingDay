import {useRef} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";


function ManageSignUp() {
    const idRef = useRef(null);
    const pwdRef = useRef(null);
    const navigate = useNavigate();
    function signUpOnClick() {

        const idChkVal = idRef.current.value;
        const pwdChkVal = pwdRef.current.value;

        // --> 비밀번호는 특수문자 하나, 8글자 이상 16자 이하로 설정함.
        const pwdValiForm = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;

        if(idChkVal.search(/\W|\s/g) > -1 ) {
            idRef.current.focus();
            alert("아이디에는 공백이나 특수문자가 들어갈수없어요.");
            return;
        } else if (!idChkVal) {
            alert("아이디를 입력하세요");
            return;
        } else if(!pwdChkVal) {
            alert("비밀번호를 입력하세요");
            return;
        } else if (!pwdValiForm.test(pwdChkVal)) {
            alert("비밀번호는 특수문자 포함 영문 숫자 조합으로,\n8글자 이상, 16자 이하로 설정해야합니다");
            pwdRef.current.focus();
            return;
        }
        //아이디 중복체크
        idDupliChk(idChkVal);

        axios.post(`/member/v1/savemanage`, {
            memberId: idChkVal,
            pwd: pwdChkVal
        },
        {
            headers : {
                AUTHORIZATION:"Bearer "+localStorage.getItem("auth_token")
            }
        }).then(function (res) {
            if(res.status === 403) {
                navigate('/member/signin');
            }
            console.log(res)
            alert("매니저 계정이 생성 되었습니다.")
        }).catch(function (err){
            console.log(err)
            alert("매니저 계정 생성중 에러가 발생했습니다. 관리자에게 문의 하세요")
        })
    } // 버튼 온클릭

    function idDupliChk(idChkVal) {
        axios({
            url: `/member/v1/chkdupliId?memberId=${idChkVal}`,
            method: 'get'
        }).then(function (res) {
            if(res.status === 403) {
                navigate('/member/signin');
            }
            if(res.data === 1) {
                alert("이미 사용중인 아이디입니다")
            }
        }).catch(function (err) {
            console.log(err)
            alert("아이디 중복체크 중에 에러가 발생했습니다.");
        });
    }
    return (
    <div>
        <div className="d-flex  justify-content-center">
            <h1>매니저 계정 생성</h1>
        </div>
        <hr/>

        <div className="d-flex  justify-content-center" style={{paddingBottom: 7}}>
            <input type="text" placeholder="아이디를 입력하세요" ref={idRef}/>
        </div>
        <div className="d-flex  justify-content-center" style={{paddingBottom: 7}}>
            <input type="password" placeholder="비밀번호를 입력하세요" ref={pwdRef}/>
        </div>

        <div className="d-flex  justify-content-center">
            <button type="button" className="btn btn-warning"
                onClick={ () => {
                    signUpOnClick()
                }}
                style={{backgroundColor: "#217Af0",
                        color: "white",
                        width: 178.5}}>매니저 계정 생성</button>
        </div>
    </div>
    )
}

export default ManageSignUp;