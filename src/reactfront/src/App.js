import React, {useState, useEffect, useMemo} from 'react';
import QnaBoard from "./routes/QnaBoard";
import {
    BrowserRouter as Router,
    Route, Routes
} from "react-router-dom";
import Home from "./routes/Home";
import QnaDetail from "./routes/QnaDetail";

function App() {
  return (
      <Router>
          <Routes>
              <Route path="/qnaBoard" element={<QnaBoard />} />
              <Route path="/" element={<Home />} />
              <Route path="/qnaDetail/:qnaId" element={<QnaDetail />} />
          </Routes>
      </Router>
  )
}

export default App;