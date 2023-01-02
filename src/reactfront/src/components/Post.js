import React, { useEffect, useState } from "react";
import DaumPostcode from "react-daum-postcode";
import '../css/post.css';

const Post = (props) => {

    const complete = (data) =>{
        let fullAddress = data.address;
        let extraAddress = '';

        if (data.addressType === 'R') {
            if (data.bname !== '') {
                extraAddress += data.bname;
            }
            if (data.buildingName !== '') {
                extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName);
            }
            fullAddress += (extraAddress !== '' ? ` (${extraAddress})` : '');
        }
        console.log(data)
        console.log(fullAddress)
        console.log(data.zonecode)

        props.setcompany({
            ...props.company,
            address:fullAddress,
        })
    }


    // const modalStyle = {
    //     position: "absolute",
    //     top: "8%",
    //     left: "0%",
    //     border: "1px solid #000000",
    // }
    const modalStyle = {
    //     .postmodal{
    //     background : rgba(0,0,0,0.25);
    //     position : fixed;
    //     left:0;
    //     top:0;
    //     height:100%;
    //     width:100%;
    // }
    }


    return (
        <div >
            <DaumPostcode
                className="postmodal"
                //style={modalStyle}
                autoClose
                onComplete={complete}
            />
        </div>
    );
};

export default Post;