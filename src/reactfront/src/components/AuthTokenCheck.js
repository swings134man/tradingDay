import {useNavigate} from "react-router-dom";




// export const setAuthTokenCookie = (name: string, value: string, option?: any) => {
//     return cookies.set(name, value, {...option});
// }
//
// export const getAuthTokenCookie = (name:string) => {
//     return cookies.get(name);
// }

import axios from "axios";

export const authTokenCheck = (localStorageToken: string) => {
    let val;
    if(localStorageToken == null) {
        val = null;
    } else {
        val = "로그인된 유저";
    }
    return val;
}


