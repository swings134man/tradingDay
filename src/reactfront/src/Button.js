import PropTypes from 'prop-types';
import styles from "./Button.module.css";
import {useEffect, useState} from "react";


function firstBtn({ text}) {
    if (text==="once") {
        return <button className={styles.btnFirst} >한명만 가져오기</button>
    } else {
        return <button className={styles.btnSecond} >여러명 가져오기</button>
    }
}
firstBtn.propTypes = {
    text: PropTypes.string.isRequired
};

export default firstBtn;