import style from "./signInStyle.css";
import {Link} from "react-router-dom";

function SignIn() {
    return(
        <div>

            <div id="con">
                <div id="login">
                    <div id="login_form">
                        <form>
                            <h3 className="login" style={{letterSpacing: -1}}>Happy Trade ~</h3>

                            <p>
                                <input type="submit" value="나는 소셜 버튼인디 ?" className="btn" style={{backgroundColor: "#217Af0"}} />
                            </p>

                            <hr />
                                <label>
                                    <p style={{textAlign: "left", fontSize: 12, color: "#666"}}>Username</p>
                                    <input type="text" placeholder="아이디를 입력" className="size"/>
                                        <p></p>
                                </label>
                            <br />
                                <label>
                                    <p style={{textAlign: "left", fontSize: 12, color: "#666"}}>Password </p>
                                    <input type="text" placeholder="비밀번호를 입력" className="size" />
                                </label>
                            <hr/>
                                <p>
                                    <input type="submit" value="Sign in" className="btn" style={{backgroundColor: "#217Af0"}}/>
                                </p>
                        </form>

                            <p className="find">
                                <span><a href="">아이디 찾기</a></span>
                                <span><a href="">비밀번호 찾기</a></span>

                                <span>
                                    <a href="/member/signup" >
                                        {/*<Link to="/member/signup">*/}
                                            회원가입
                                        {/*</Link>*/}
                                    </a>
                                </span>

                            </p>
                    </div>
                </div>
            </div>

        </div>
    )
}

export default SignIn;