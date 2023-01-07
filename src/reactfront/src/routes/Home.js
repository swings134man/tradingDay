import React, {useState} from "react";
import Button from "../components/Button";
import BodyContent from "../components/BodyContent";
import Header from "../components/Header";
import NavigationBar from "../components/NavigationBar";
import {useLocation} from "react-router-dom";
import jwt_decode from "jwt-decode";


function Home() {

    const token = useLocation();
    console.log(token)
    if(token.search !== '') {
        if(token.search.substring(11) !== null || token.search.substring(11) !== '') {
            //토큰 디코드
            const decode_token = jwt_decode(token.search.substring(11));
            localStorage.setItem("userRole", decode_token.userRole)
            localStorage.setItem('auth_token', token.search.substring(11));
            console.log(token.search.substring(11));
            localStorage.setItem('memberId', decode_token.memberId);
            window.location.assign("/");
        }
    }



    // const socialEmail = emailData.search.substring(7);
    //console.log(token.search.substring(11))
    // if(token.search !== null) {
    //     console.log(token.search.substring(11))
    // }

    // const test = decodeURI(token.search.substring(11));
    // console.log(test);

    // if() {
    //
    // }

    //const socialEmail = emailData.search.substring(7);



    return (
        <div>
            <Header />
            <NavigationBar />
            <BodyContent />
        </div>
    )
}

export default Home;