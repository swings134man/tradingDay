import React from "react";
import ReactLoading from 'react-loading'

function Loader({type, color, message}) {

    return (
        <div className="d-flex  justify-content-center">
         {/*<div class="contentWrap" style={{alignContent: "center"}}>*/}
            <div style={{
                position: "fixed",
                // top: "45%",
                // left: "55%",
                top: "50%",
                left: "50%",
                transform: "translate(-50%, -50%)"
            }}>

               <div className="d-flex  justify-content-center"> <h4>{message}</h4></div>
                {/*<h3>창을 닫지 말아주세요.</h3>*/}
                <div className="d-flex  justify-content-center">
                <ReactLoading
                    type={type}
                    color={color}
                    height={'40%'}
                    width={'40%'} />
                </div>
            </div>
        </div>
    );

}

export default Loader;