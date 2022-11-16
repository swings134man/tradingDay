import {useRef} from "react";
import {useNavigate} from "react-router-dom";


function QnaWrite() {

    const titleRef = useRef(null);
    const contentRef = useRef(null);
    const navigate = useNavigate();

    function onSubmit(e) {

        e.preventDefault();

        const titleVal = titleRef.current.value;
        const contentVal = contentRef.current.value;

        const uri = `http://localhost:8080/qna/v1/qna`;
        const encoded = encodeURI(uri);
        fetch(encoded, {
            method: "PUT",
            headers: {
                "Content-type": "application/json",
            },
            body: JSON.stringify({
                title: titleVal,
                content: contentVal,
                writer: "xodlf5363"
            }),
        }).then(res => {
            if(res.ok) {
                navigate('/qna/qnaBoard');
            } else {
                alert("문의 작성에 실패했습니다. 잠시 후 다시 시도해주세요");
            }
        })
    }

    return (
        <div>
            <div align="center" style={{padding : 100}}>
                <div >
                    <h1>나는 qna등록 페이지</h1>
                </div>
                <form onSubmit={onSubmit}>
                    <table className="table table-striped table-bordered table-hover">

                        <tbody>
                        <tr>
                            <th scope="row">작성자</th>
                            <td >xodlf5363</td>
                        </tr>
                        <tr>
                            <th scope="row" >제목</th>
                            <td colSpan="3">
                                <input type="text"ref={titleRef} placeholder="제목을 입력하세요"/>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">내용</th>
                            <td colSpan="4" className="view_text">
                                <textarea ref={contentRef} style={{width: 500, height: 200}} placeholder="내용을 입력하세요"></textarea>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div align="right">
                        <button className="btn btn-warning" style={{backgroundColor: "#217Af0", width: 100}} >문의등록</button>
                    </div>
                </form>
            </div>

        </div>
    )
}

export default QnaWrite;