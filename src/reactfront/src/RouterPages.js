import NavigationBar from "./components/NavigationBar";
import {BrowserRouter as Router ,Route, Routes} from "react-router-dom";
import QnaBoard from "./routes/qna/QnaBoard";
import Home from "./routes/Home";
import QnaDetail from "./routes/qna/QnaDetail";
import QnaUpdate from "./routes/qna/QnaUpdate";
import React from "react";
import QnaWrite from "./routes/qna/QnaWrite";
import Footer from "./components/Footer";
import ItemBoard from "./routes/item/ItemBoard";
import ItemDetail from "./routes/item/ItemDetail";
import SignIn from "./routes/member/SignIn";
import SignUp from "./routes/member/SignUp";
import ItemWrite from "./routes/item/ItemWrite";

function RouterPages () {
    return (
        <div>
        <Router>
        <NavigationBar />
s
        <Routes>
            <Route path="/" element={<Home />} />
            {/*member 관련 route*/}
            <Route path="/member/signin" element={<SignIn />}/>
            <Route path="/member/signup" element={<SignUp />}/>


            {/*qna관련 route*/}
            <Route path="qna/qnaBoard" element={<QnaBoard />} />
            <Route path="/qnaDetail/:qnaId" element={<QnaDetail />} />
            <Route path="qnaUpdate/:qnaId/:title/:writer/:content/:createdDate" element={<QnaUpdate />} />
            <Route path="/qnaWrite" element={<QnaWrite />} />

            {/* item route */}
            <Route path="/item/itemBoard" element={<ItemBoard/>}/>
            <Route path="/itemDetail/:id" element={<ItemDetail />} />
            <Route path="/itemWrite" element={<ItemWrite />} />
        </Routes>

        {/*<Routes>*/}
        {/*    <Route path="/" element={<Home />} />*/}
        {/*</Routes>*/}
        <Footer />
    </Router>
</div>
    )
}

export default RouterPages;