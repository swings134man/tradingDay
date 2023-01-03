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
import QnaPwdChk from "./routes/qna/QnaPwdChk";
import ItemBoardUpdate from "./routes/item/ItemBoardUpdate";
import ApplyWrite from "./routes/item/ApplyWrite";
import ApplyBoardList from "./routes/apply/ApplyBoardList";
import ApplyDetail from "./routes/apply/ApplyDetail";
import ApplyAccept from "./routes/apply/ApplyAccept";
import Mypage from "./routes/member/MyPage";
import MyPage from "./routes/member/MyPage";
import PersonInfoModi from "./routes/member/PersonInfoModi";
import NoteWrite from "./components/NoteWrite";

function RouterPages () {
    return (
        <div>
        <Router>
        <NavigationBar />

        <Routes>
            <Route path="/" element={<Home />} />
            {/*member 관련 route*/}
            <Route path="/member/signin" element={<SignIn />}/>
            <Route path="/login" element={<SignIn />}/>
            <Route path="/member/signup" element={<SignUp />}/>
            <Route path="/logout" element={<Home />} />
            <Route path="/member/mypage" element={<MyPage/>} />
            <Route path="/member/personalinfomodi" element={<PersonInfoModi/>} />

            {/*qna관련 route*/}
            <Route path="/qnaboard" element={<QnaBoard />} />
            <Route path="/qnadetail/:qnaId" element={<QnaDetail />} />
            <Route path="qnaupdate/:qnaId/:title/:writer/:content/:createdDate" element={<QnaUpdate />} />
            <Route path="/qnawrite" element={<QnaWrite />} />
            <Route path="/qnapwdchk/:qnaId" element={<QnaPwdChk />} />

            {/* item route */}
            <Route path="/item/itemBoard" element={<ItemBoard/>}/>
            <Route path="/itemDetail/:id" element={<ItemDetail />} />
            <Route path="/itemWrite" element={<ItemWrite />} />
            <Route path="/itemBoardUpdate/:id/:title/:writer/:content/:createdDate" element={<ItemBoardUpdate />} />

            {/*  Apply 지원  */}
            <Route path="/applyWrite/:id/:writer" element={<ApplyWrite />} />
            <Route path="/applyBoardList/:memberId" element={<ApplyBoardList/>} />
            <Route path="/applyDetail/:applyId/:itemBoard" element={<ApplyDetail/>}/>
            <Route path="/applyAccept/:applyId/:title/:writer/:writerEmail/:itemBoard" element={<ApplyAccept/>}/>

            {/*  쪽지관련 Route  */}
            <Route path="/noteWrite/:receiveId" element={<NoteWrite/>}/>
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