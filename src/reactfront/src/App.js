import React, {useState, useEffect, useMemo} from 'react';
import Button from "./Button";
import TableTest from "./TableTest";


function App() {
    const [qnaList, setQnaList] = useState([]);




    const getData = async () => {
        const res = await fetch('http://localhost:3000/qna/v1/qnaList');
        const data = await res.json();
        console.log("나는 인덱스 0 번이예요",data[0]);
        console.log("나는 전체 데이터예요", data);


        setQnaList(data[0]);
        console.log(qnaList);
    };




  return (

    <div>
          <hr/>
          <Button text={"once"} />
          <hr/>
          <Button text={"many"}/>
          <hr/>
          <button onClick={getData}>게시판 정보 가져오기</button>
          <TableTest data={qnaList} />
          {/*<table border={1}>*/}
          {/*    <caption>게시판정보 조회하기</caption>*/}
          {/*    <thead>*/}
          {/*    <tr>*/}
          {/*        <th scope="col">고객번호</th>*/}
          {/*        <th scope="col">고객 ID</th>*/}
          {/*        <th scope="col">이름</th>*/}
          {/*        <th scope="col">내용</th>*/}
          {/*        <th scope="col">전화번호</th>*/}
          {/*    </tr>*/}
          {/*    </thead>*/}
          {/*    <tbody>*/}
          {/*    <tr>*/}
          {/*        <th scope="row"></th>*/}
          {/*        <td>{data.qnaId}</td>*/}
          {/*        <td>{data.writer}</td>*/}
          {/*        <td>{data.name}</td>*/}
          {/*        <td>{data.content}</td>*/}
          {/*        <td></td>*/}
          {/*        <td></td>*/}
          {/*        <td></td>*/}
          {/*        <td></td>*/}
          {/*    </tr>*/}
          {/*    </tbody>*/}
          {/*</table>*/}
      </div>
  )
}

export default App;