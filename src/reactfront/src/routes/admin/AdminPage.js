import PersonPwdChk from "../member/PersonPwdChk";
import ApplyBoardList from "../apply/ApplyBoardList";
import NoteBoardList from "../note/NoteBoardList";
import ManageSignUp from "./ManageSignUp";
import {useState} from "react";


function AdminPage() {

    const [quarter, setQuarter] = useState("manageSignUp");

    const pageRender = (page) => {
        let pageId = page.target.getAttribute("data-id");
        if(pageId === "manageSignUp") {
            setQuarter("manageSignUp");
        }
        if(pageId === "note") {
            setQuarter("note");
        }
    }

    return (
        <div className="d-flex justify-content-center " style={{ paddingTop:100, height: '100vh'}}>
            <div className="border-end" id="sidebar-wrapper" >
                <div className="sidebar-heading border-bottom bg-light" align="center">
                    <p>관리자 페이지</p>
                </div>
                <div className="list-group list-group-flush">
                    <a className="list-group-item list-group-item-action list-group-item-light p-3"
                       data-id={"manageSignUp"}
                       onClick={pageRender}
                       href="#!">매니저 계정 생성
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
                    {quarter === "manageSignUp" ? <ManageSignUp /> : null}
                </div>
                <div style={{width: '100vh'}}>
                    {quarter === "note"  ? <NoteBoardList/> : null}
                </div>
            </div>
        </div>
    )
}

export default AdminPage;