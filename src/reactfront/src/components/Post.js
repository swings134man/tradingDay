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


    // const postCodeStyle = {
    //     display: 'block',
    //     position: 'relative',
    //     top: '0%',
    //     width: '450px',
    //     height: '700px',
    //     padding: '7px',
    // };
    const width = 595;
    const height = 700;
    const modalStyle = {
        position: "absolute",
        top: "8%",
        // left: "-178px",
        left: "0%",
        zIndex: "100",
        border: "1px solid #000000",
        overflow: "hidden"

    }

    return (
        <div >
            <DaumPostcode
                // className="postmodal"
                style={modalStyle}
                autoClose
                onComplete={complete}
                width={width}
                height={height}
            />
        </div>
    );
};

export default Post;