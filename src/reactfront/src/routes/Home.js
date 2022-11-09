import React, {useState} from "react";
import {useEffect} from "react";
import {useParams} from "react-router-dom";
import Button from "../Button";
import BodyContent from "../components/BodyContent";
import Footer from "../components/Footer";
import Header from "../components/Header";
import NavigationBar from "../components/NavigationBar";


function Home() {
    return (
        <div>
            <Header />
            <NavigationBar />
            <BodyContent />
            <h1>나는 인덱스</h1>
            <hr/>
            <Button text={"once"}/>
            <hr/>
            <Footer />
        </div>
    )
}

export default Home;
