
import style from "../../css/signInStyle.css";
import {Link, useNavigate} from "react-router-dom";
import {useEffect, useRef, useState} from "react";
import axios from "axios";
import { userNavigate } from "react-router-dom";
import {Container} from "react-bootstrap";
import styled from "styled-components";
import googleLoginImage from "../../img/googleSignInLight.png";
import kakaoLoginImg from "../../img/kakaoLoginImg.png";
import GoogleButton from "react-google-button";

function SignIn() {

    const idRef = useRef(null);
    const pwdRef = useRef(null);
    let activated;

    function searchActivated(memberId) {
        axios.get("/member/v1/searchactivated", {
            params: {
                memberId : memberId
            }
        }).then(function (res) {
            console.log(res.data)
            activated = res.data;
        }).catch(function (err) {
            console.log(err)
            console.log('계정활성여부 조회 중 에러가 발생했습니다');
        });
    }

    function loginProcess() {

        const idVal = idRef.current.value;
        const pwdVal = pwdRef.current.value;

        if (idVal == "") {
            alert("아이디를 입력하세요");
            idRef.current.focus();
            return;
        } else if (pwdVal == "") {
            alert("비밀번호를 입력하세요");
            pwdRef.current.focus();
            return;
        }
        // 계정 활성 여부 조회
        searchActivated(idVal);
        // 로그인 처리
        axios.post('/member/v1/login', {
                memberId : idVal,
                pwd : pwdVal
            },
            {withCredentials:true}
        ).then(function (res) {
            console.log(res);
            //"ROLE_USER"
            localStorage.setItem("userRole", res.data.authorities[0].authority)
            // auth_token은 로컬스토리지
            localStorage.setItem('auth_token', res.headers.auth_token);
            localStorage.setItem('memberId', res.data.username);
            window.location.assign("/");
        }).catch(function (err){
            console.log(err);
            if(activated === false) {
                alert('계정이 비활성 상태 입니다.');
                return;
            }
            alert('아이디와 비밀번호를 확인하세요');
        })
    }

    const loginOnClick = (e) =>  {
        e.preventDefault();
        loginProcess();
    } // login onClick

    function googleLoginLocation() {
        // google location
        window.location.href="http://localhost:8080/oauth2/authorization/google";
    }
    function kakaoLoginLocation() {
        //kakao location
        window.location.href="http://localhost:8080/oauth2/authorization/kakao";
    }

    return(
        <div>
            <div id="con">
                <div id="login">
                    <div id="login_form">
                        <form onSubmit={loginOnClick}>
                            <h3 className="login" style={
                                {letterSpacing: -1, paddingBottom: 10}}>Happy Trade ~</h3>

                                <GoogleButton onClick={googleLoginLocation} style={{width:300}}/>
                                <div  style={{width:300, paddingTop: 10}}>
                                {/*kakaoLogin button*/}
                                    <button
                                        type="button"
                                        onClick={kakaoLoginLocation}
                                        style={{width:300,
                                                borderStyle:"none",
                                                height:50,
                                                backgroundImage: "url(" + `${kakaoLoginImg}` + ")", backgroundSize: 300
                                        }}/>
                                </div>
                            <hr />
                                <label>
                                    <p style={{textAlign: "left", fontSize: 12, color: "#666"}}>id</p>
                                    <input type="text" placeholder="아이디를 입력" className="size" ref={idRef}/>
                                    <p></p>
                                </label>
                            <br />
                                <label>
                                    <p style={{textAlign: "left", fontSize: 12, color: "#666"}}>Password </p>
                                    <input
                                        type="password" placeholder="비밀번호를 입력" className="size" ref={pwdRef}/>
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