import React from 'react';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import Home from "./routes/Home";
import {BrowserRouter} from "react-router-dom";
import RouterPages from "./RouterPages";
import {CookiesProvider} from "react-cookie";
import {GoogleOAuthProvider} from "@react-oauth/google";
//import axios from "axios";

//axios.defaults.withCredentials = true;

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(

        <React.StrictMode>
            <GoogleOAuthProvider clientId='215366700441-tdhcgm0hhnedpoaogcnc4riisha0j6dh.apps.googleusercontent.com'>
                <CookiesProvider>
                    <RouterPages>
                        <Home/>
                    </RouterPages>
                </CookiesProvider>
            </GoogleOAuthProvider>
        </React.StrictMode>


);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
