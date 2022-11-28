import style from "../../css/signInStyle.css";
import {Link} from "react-router-dom";
import {useRef} from "react";

function SignIn() {
    const idRef = useRef(null);
    const pwdRef = useRef(null);

    const loginOnClick = (e) =>  {
        e.preventDefault();
    }




    return(
        <div>

            <div id="con">
                <div id="login">r
                    <div id="login_form">
                        <form onSubmit={loginOnClick}>
                            <h3 className="login" style={{letterSpacing: -1}}>Happy Trade ~</h3>
                            <p>
                                <input type="submit" value="나는 소셜 버튼인디 ?" className="btn" style={{backgroundColor: "#217Af0"}} />
                            </p>

                            <hr />
                                <label>
                                    <p style={{textAlign: "left", fontSize: 12, color: "#666"}}>id</p>
                                    <input type="text" placeholder="아이디를 입력" className="size" ref={idRef}/>
                                        <p></p>
                                </label>
                            <br />
                                <label>
                                    <p style={{textAlign: "left", fontSize: 12, color: "#666"}}>Password </p>
                                    <input type="text" placeholder="비밀번호를 입력" className="size" ref={pwdRef}/>
                                </label>
                            <hr/>
                                <p>
                                    <input type="submit" value="Sign in" className="btn" style={{backgroundColor: "#217Af0"}}/>
                                </p>
                        </form>

                            <p className="find">
                                <span><a href="#" onClick={() => alert("기능 추가 예정 입니다")}>아이디 찾기</a></span>
                                <span><a href="#" onClick={() => alert("기능 추가 예정 입니다")}>비밀번호 찾기</a></span>
                                <span>
                                    <a href="/member/signup">회원가입 </a>
                                </span>
                            </p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default SignIn;