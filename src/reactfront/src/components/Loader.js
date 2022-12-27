import React from "react";
import ReactLoading from 'react-loading'

function Loader({type, color, message}) {

    return (
        <div class="contentWrap" style={{alignContent: "center"}}>
            <div style={{
                position: "fixed",
                top: "45%",
                left: "55%",
                transform: "translate(-50%, -50%)"
            }}>
                <h4>{message}</h4>
                {/*<h3>창을 닫지 말아주세요.</h3>*/}
                <ReactLoading
                    type={type}
                    color={color}
                    height={'40%'}
                    width={'40%'} />
            </div>
        </div>
    );

}

export default Loader;