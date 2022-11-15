import React, {useState, useEffect} from "react";
import {Link, useParams} from "react-router-dom";
import { useNavigate } from "react-router-dom";
import axios from "axios";


function ItemDetail() {

    const [data, setData] = React.useState([]);
    const {id} = useParams();

    //use Effect
    useEffect(() => {
        const getData = async () => {
            // 파라미터 -> 보낼이름 : data

            const item = await axios.get('/item/v1/detailPost', {
                params:{
                    id: id
                }
            });
            setData(item.data);
            console.log(item);
        }
        getData();
    }, []);//use Effect




}//func

export default ItemDetail;