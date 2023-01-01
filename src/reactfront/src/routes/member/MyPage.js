import {useEffect, useState} from "react";
import PersonInfoModi from "./PersonInfoModi";
import PersonPwdChk from "./PersonPwdChk";
import {render} from "react-dom";



function MyPage(props) {

    // false인 경우 마이 페이지 렌더링, true인 경우 지원서 페이지 렌더링 온클릭에 넣어서 분기
    const [quarter, setQuarter] = useState();
    //const [memberPwdChk, setMemberPwdChk] = useState(false);
    console.log('props', props.pwdChk)

    const memberPwdChk = () => {
        if(props.pwdChk) {
            setQuarter(false);
        }
    }


    useEffect(() => {

    }, [props.pwdChk])


    const pageRender = (page) => {
        let pageId = page.target.getAttribute("data-id")
        if(pageId === "mypage") {
            setQuarter(false)
        } else if( pageId === "apply") {
            setQuarter(true)
        }
    }

    return (
        <div className="d-flex" id="wrapper" style={{padding: 100}}>

            <div className="border-end bg-white" id="sidebar-wrapper" style={{marginLeft: 150}}>
                <div className="sidebar-heading border-bottom bg-light" align="center">마이페이지</div>
                <div className="list-group list-group-flush">
                    <a className="list-group-item list-group-item-action list-group-item-light p-3"
                       data-id={"apply"}
                       onClick={pageRender}
                       href="#!">지원서</a>

                    <a className="list-group-item list-group-item-action list-group-item-light p-3"
                       href="#!"
                       data-id={"mypage"}
                       onClick={pageRender}>개인정보수정</a>
                </div>
            </div>

            <div style={{paddingLeft: 400}}>
            {/*<div style={{margin: 0 "auto"}}>*/}
            {/*<div id="page-content-wrapper">*/}
            {/*    <div className="container-fluid">*/}
                <div>
                    {quarter === false ?
                        <PersonPwdChk /> : null
                    }

                    {/*{ props.pwdChk === true ?*/}
                    {/*    <PersonInfoModi /> : null }*/}

                </div>
            </div>
        </div>
   )
}


export default MyPage;