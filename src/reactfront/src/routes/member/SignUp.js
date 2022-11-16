import style from "./signUpStyle.css";

function SignUp() {
    function onSubmit(e) {

        e.preventDefault();
    }


    return (
        <div>
            <div id="con">
                <div id="login">
                    <div id="login_form">
                        <form onSubmit={onSubmit}>

                            <h3 className="login" style={{letterSpacing: -1}}>Welcome Trade :)</h3>
                            <br/>
                            <label>
                                <p style={{textAlign: "left", fontSize: 12, color:"#666"}}>user name</p>
                                <input type="text" placeholder="아이디" className="size"/>
                                <p></p>
                            </label>

                            <br />

                            <label>
                                <p style={{textAlign: "left", fontSize: 12, color:"#666"}}>Password </p>
                                <input type="password" placeholder="비밀번호" className="size"/>
                        </label>
                            <br />
                            <label>
                                <p style={{textAlign: "left", fontSize: 12, color:"#666"}}></p>
                                <input type="password" placeholder="비밀번호 확인" className="size"/>
                            </label>

                            <br />
                            <label>
                                <p style={{textAlign: "left", fontSize: 12, color:"#666", paddingTop: 15}}>Mobile Phone</p>
                                <select className="size num1">
                                    <option defaultValue="1" >SKT</option>
                                    <option defaultValue="2" >KT</option>
                                    <option defaultValue="3" >LG</option>
                                </select>
                                <input placeholder="-빼고 숫자만 입력" className="size num2" />
                            </label>

                            <br/>

                            <label>
                                <br />
                                <p style={{textAlign: "left", fontSize: 12, color:"#666", paddingTop: -20}}>E-mail</p>
                                <input type="text" placeholder="이메일" className="size" style={{paddingTop: -20}}/>
                            </label>
                            <br />
                            <label >
                                <br />
                                <div>

                                    <p style={{textAlign: "left", fontSize: 12, color:"#666"}}>주소
                                        &nbsp;<button className="addrBtn" >검색</button>
                                    </p>

                                    <input type="text" placeholder="주소" className="size" style={{}}/>
                                </div>
                                <br />
                                <div style={{marginTop: -8, paddingBottom: -100}} >
                                    <input type="text" placeholder="상세주소" className="size" style={{}}/>
                                </div>
                                <br />
                                <br />
                            </label>
                        </form>
                        <form onSubmit={onSubmit}>
                            <p style={{marginTop: -15}}>
                                <input type="submit" value="가입!" className="btn" style={{backgroundColor: "#217Af0"}}/>
                            </p>
                        </form>

                        <hr/>
                        <p className="find">
                            <span><a href="/member/signin">로그인 페이지로 이동</a></span>
                        </p>
                    </div>

                </div>
            </div>
        </div>
                )}
                export default SignUp;