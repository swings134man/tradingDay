import NavigationBar from "./components/NavigationBar";
import {BrowserRouter as Router ,Route, Routes} from "react-router-dom";
import QnaBoard from "./routes/qna/QnaBoard";
import Home from "./routes/Home";
import QnaDetail from "./routes/qna/QnaDetail";
import React from "react";

function RouterPages () {
    return (
        <Router>
        <NavigationBar />

        <Routes>
            {/*qna관련 route*/}
            <Route path="qna/qnaBoard" element={<QnaBoard />} />
            <Route path="/qnaDetail/:qnaId" element={<QnaDetail />} />
        </Routes>

        <Routes>
            <Route path="/" element={<Home />} />
        </Routes>

    </Router>
    )
}

export default RouterPages;