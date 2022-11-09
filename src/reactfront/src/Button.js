import PropTypes from 'prop-types';
import styles from "./Button.module.css";
import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";


function firstBtn({text}) {
    if (text === "once") {
        return (
            <button className={styles.btnFirst}>
                <Link to={`/qnaBoard`}>
                    문의 게시판으로 이동
                </Link>
            </button>
        )
    } else {
        return (
            <button className={styles.btnSecond}>여러명 가져오기</button>
        )
    }
}

firstBtn.propTypes = {
    text: PropTypes.string.isRequired
};

export default firstBtn;