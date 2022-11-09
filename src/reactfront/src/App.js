import React, {useState, useEffect, useMemo} from 'react';
import QnaBoard from "./routes/QnaBoard";
import {
    BrowserRouter as Router,
    Route, Routes
} from "react-router-dom";
import Home from "./routes/Home";
import QnaDetail from "./routes/QnaDetail";
import Footer from "./components/Footer";
import NavigationBar from "./components/NavigationBar";
import Header from "./components/Header";

function App() {
  return (
      <Router>
          <NavigationBar />

          <Routes>
                  <Route path="/qnaBoard" element={<QnaBoard />} />
                  <Route path="/" element={<Home />} />
                  <Route path="/qnaDetail/:qnaId" element={<QnaDetail />} />
          </Routes>
          <Footer />
      </Router>
  )
}

export default App;