import React, {useState} from "react";
import Button from "../components/Button";
import BodyContent from "../components/BodyContent";
import Header from "../components/Header";
import NavigationBar from "../components/NavigationBar";


function Home() {
    return (
        <div>
            <Header />
            <NavigationBar />
            <BodyContent />
        </div>
    )
}

export default Home;
