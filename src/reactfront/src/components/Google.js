// import React, { Component } from 'react';
// import { GoogleLogin } from 'react-google-login';
// import styled from 'styled-components';
// import {GoogleOAuthProvider} from "@react-oauth/google";
//
// const clientId = "215366700441-tdhcgm0hhnedpoaogcnc4riisha0j6dh.apps.googleusercontent.com"
//
// class Google extends Component {
//
//     constructor(props) {
//         super(props);
//         this.state = {
//             id: '',
//             name: '',
//             provider: '',
//         }
//     }
//     // Google Login
//     responseGoogle = (res) => {
//         console.log(res)
//     }
//
//     // Login Fail
//     responseFail = (err) => {
//         console.error(err);
//     }
//
//     render() {
//         return (
//             <Container>
//                 <GoogleLogin
//                     clientId={clientId}
//                     buttonText="Google"
//                     onSuccess={this.responseGoogle}
//                     onFailure={this.responseFail}
//                 />
//             </Container>
//         );
//     }
// }
//
// const Container = styled.div`
//     display: flex;
//     flex-flow: column wrap;
// `
//
//
//
// export default Google;