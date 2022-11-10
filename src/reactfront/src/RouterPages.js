import NavigationBar from "./components/NavigationBar";
import {BrowserRouter as Router ,Route, Routes} from "react-router-dom";
import QnaBoard from "./routes/qna/QnaBoard";
import Home from "./routes/Home";
import QnaDetail from "./routes/qna/QnaDetail";
import React from "react";
// import {
//     BrowserRouter as Router,
//     Route, Routes
// } from "react-router-dom";
// import { BrowserRouter as Router } from "react-router-dom";


function RouterPages () {
    return (
        <Router>
            <NavigationBar />
            <Routes>
                <Route path="/qnaBoard" element={<QnaBoard />} />
                <Route path="/" element={<Home />} />
                <Route path="/qnaDetail/:qnaId" element={<QnaDetail />} />
            </Routes>
        </Router>
    )
}

export default RouterPages;