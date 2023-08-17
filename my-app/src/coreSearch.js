import './coreSearch.css';
import React, { useState } from 'react';
import back from './img/back.png';
import duck from './img/duck.PNG';
import { BrowserRouter as Router, Link } from 'react-router-dom';
import { Routes, Route } from 'react-router-dom';
import { useNavigate, useLocation } from 'react-router-dom';

const CoreSearch = () => {
  function SearchComponent() {
    const navigate = useNavigate();
    const location = useLocation(); // 獲取路由位置
    const [selectedGrade, setSelectedGrade] = useState('2');

    const handleSubmit = (e) => {
      e.preventDefault();
      const queryParams = new URLSearchParams({
        grade: selectedGrade
      });
    
      const url = '/core_elective?' + queryParams.toString();
    
      fetch(url, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        },
      })
        .then(response => response.json())
        .then(data => {
          // 在導航之前將查詢結果設置在路由狀態中
          navigate('/Core', { state: { CResult: data } });
        })
        .catch(error =>
          console.error(error)
        );
    }

    return (
      <div className="coreSearch">
        <div className='search_bg'>
          <div>
            <Link to='/Credit'>
              <img src={back} alt="回上一頁" className="search_backicon" />
            </Link>
          </div>
          <div className="search_title">
            <label className="titleText">核心課程查詢</label>
          </div>
          <div className="selectDiv">
            <select
              className="selectGrade"
              value={selectedGrade}
              onChange={(e) => setSelectedGrade(e.target.value)}
            >
              {/* <option value="1">大一</option> */}
              <option value="2">大二</option>
              <option value="3">大三</option>
              {/* <option value="4">大四</option> */}
            </select>
          </div>
          <div className='searchDiv'>
            <button className="search_btn"  onClick={handleSubmit}>查詢</button>
          </div>
          <div><img src={duck} className='duckpic' /></div>
        </div>
      </div>
    );
  }

  return (
    <Routes>
      <Route path="/" element={<SearchComponent />} />
    </Routes>
  );
}

export default CoreSearch;
