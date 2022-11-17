import style from "./signUpStyle.css";
import {useState} from "react";
import Post from "./Post";
import AddressModal from "./AddressModal";



function SignUp() {

    const [enroll_company, setEnroll_company] = useState({
        address:'', });
    const [popup, setPopup] = useState(false);
    const handleInput = (e) => {
        setEnroll_company({
            ...enroll_company,
            [e.target.name]:e.target.value,
        })
    }
    const handleComplete = (data) => {
        setPopup(!popup);
    }
    function onSubmit(e) {
        e.preventDefault();
    }

    return (
        <div>
            <div id="con">

                <div id="login">
                    <div id="login_form">
                        <form onSubmit={onSubmit}>
                            {popup && <Post company={enroll_company} setcompany={setEnroll_company}></Post>}
                            <h3 className="login" style={{letterSpacing: -1}}>Welcome Trade :)</h3>
                            <br/>
                                <label>
                                    <p style={{textAlign: "left", fontSize: 12, color:"#666"}}>user name</p>
                                    <input type="text" placeholder="아이디" className="size"/>
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

                            <div >
                                {/*주소 검색 버튼*/}
                                {/*<button className="addrBtn"  onClick={handleComplete}>검색</button>*/}
                                {/*{popup && <Post company={enroll_company} setcompany={setEnroll_company}></Post>}*/}
                                {/*<AddressModal />*/}
                            </div>

                            <label >
                                <br />
                                    <div>
                                        <p style={{textAlign: "left", fontSize: 12, color:"#666"}}>주소
                                            &nbsp;
                                            <button className="addrBtn"  onClick={handleComplete}>검색</button>
                                            {/*<button className="addrBtn"  onClick={handleComplete}>검색</button>*/}
                                        </p>
                                        {/*{popup && <Post company={enroll_company} setcompany={setEnroll_company}></Post>}*/}
                                        <input type="text" placeholder="주소를 검색해주세요 click me..!" className="size" onClick={handleComplete} onChange={handleInput} value={enroll_company.address}/>

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