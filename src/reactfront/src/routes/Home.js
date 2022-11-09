import React, {useState} from "react";
import {useEffect} from "react";
import {useParams} from "react-router-dom";
import Button from "../Button";


function Detail() {
    return (
        <div>
            <h1>나는 인덱스</h1>
            <hr/>
            <Button text={"once"}/>
            <hr/>
        </div>
    )
}

export default Detail;
