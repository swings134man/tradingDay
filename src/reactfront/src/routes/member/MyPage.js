import {useEffect, useState} from "react";
import PersonInfoModi from "./PersonInfoModi";
import PersonPwdChk from "./PersonPwdChk";
import {render} from "react-dom";
import ApplyBoardList from "../apply/ApplyBoardList";
import Note from "../../components/Note";
import NoteBoardList from "../note/NoteBoardList";
// import {mypageCss} from



function MyPage() {

    const [quarter, setQuarter] = useState("apply");



    const pageRender = (page) => {
        let pageId = page.target.getAttribute("data-id")

        if(pageId === "apply") {
            setQuarter("apply");
        }
        if( pageId === "myPage") {
            setQuarter("myPage");
        }
        if(pageId === "note") {
            setQuarter("note");
        }
    }

    return (
        <div className="d-flex justify-content-center " style={{ paddingTop:100, height: '100vh'}}>
            <div className="border-end" id="sidebar-wrapper" >
                <div className="sidebar-heading border-bottom bg-light" align="center">마이페이지</div>
                <div className="list-group list-group-flush">
                    <a className="list-group-item list-group-item-action list-group-item-light p-3"
                       data-id={"apply"}
                       onClick={pageRender}
                       href="#!">지원서
                    </a>
                    <a className="list-group-item list-group-item-action list-group-item-light p-3"
                       href="#!"
                       data-id={"myPage"}
                       onClick={pageRender}>개인정보수정
                    </a>
                    <a className="list-group-item list-group-item-action list-group-item-light p-3"
                       href="#!"
                       data-id={"note"}
                       onClick={pageRender}>쪽지함
                    </a>
                </div>
            </div>

            <div >
                <div style={{width: '100vh'}}>
                    {quarter === "myPage" ? <PersonPwdChk /> : null }
                </div>
                <div style={{width: '100vh'}}>
                    {quarter === "apply"  ? <ApplyBoardList/> : null}
                </div>
                <div style={{width: '100vh'}}>
                    {quarter === "note"  ? <NoteBoardList/> : null}
                </div>
            </div>
        </div>
   )
}


export default MyPage;