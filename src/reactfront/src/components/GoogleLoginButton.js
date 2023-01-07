import { GoogleLogin } from '@react-oauth/google';
import { GoogleOAuthProvider } from '@react-oauth/google';
import styled from 'styled-components';
import {useEffect, useState} from "react";
import axios from "axios";
// import {Helmet, HelmetProvider} from "react-helmet-async";



const GoogleLoginButton = () => {
    const clientId = '215366700441-tdhcgm0hhnedpoaogcnc4riisha0j6dh.apps.googleusercontent.com';



    return (
        <div>
            <GoogleOAuthProvider clientId={clientId}>
                <GoogleLogin
                    width={300}
                    onSuccess={(credentialResponse) => {
                        console.log(credentialResponse.credential);
                        console.log(credentialResponse)

                        // axios({
                        //         url: '/login/oauth2/code/google',
                        //         method: 'get',
                        //         data: {
                        //             AUTHORIZATION: credentialResponse
                        //         }
                        //         }).then(function (res) {
                        //             console.log(res)
                        //         });
                    }}
                    onError={() => {
                        console.log('Login Failed');
                    }}
                />
            </GoogleOAuthProvider>
        </div>
    );
};



// const Container = styled.div`
//     display: flex;
//     flex-flow: column wrap;
// `



export default GoogleLoginButton;
