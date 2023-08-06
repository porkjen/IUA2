import './Search.css';
import React, { useState } from 'react';
import back from './img/back.png';
import duck from './img/duck.PNG';
import { BrowserRouter as Router, Link } from 'react-router-dom';
import { Routes, Route } from 'react-router-dom';
import { useNavigate, useLocation } from 'react-router-dom';

const Search = () => {
  function SearchComponent() {
    const navigate = useNavigate();
    const location = useLocation(); // 獲取路由位置
    const [selectedType, setSelectedType] = useState('必修');
    const [selectedGrade, setSelectedGrade] = useState('大一');

    const handleSubmit = (e) => {
      e.preventDefault();
      const queryParams = new URLSearchParams({
        category: selectedType,
        grade: selectedGrade
      });

      const url = '/course_search?' + queryParams.toString();

      const formData = {
        "category": selectedType,
        "grade": selectedGrade
      };

      fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
      })
        .then(response => response.json())
        .then(data => {
          // 在導航之前將查詢結果設置在路由狀態中
          navigate('/Must', { state: { RCResult: data } });
        })
        .catch(error =>
          console.error(error)
        );
    }

    return (
      <div className="Search">
        <div className='search_bg'>
          <div>
            <Link to='/Credit'>
              <img src={back} alt="回上一頁" className="search_backicon" />
            </Link>
          </div>
          <div className="search_title">
            <label className="titleText">必選修課程查詢</label>
          </div>
          <div className="selectDiv">
            <select
              className="selectMust"
              value={selectedType}
              onChange={(e) => setSelectedType(e.target.value)}
            >
              <option value="必修">必修</option>
              <option value="選修">選修</option>
            </select>
          </div>
          <div className="selectDiv2">
            <select
              className="selectGrade"
              value={selectedGrade}
              onChange={(e) => setSelectedGrade(e.target.value)}
            >
              <option value="大一">大一</option>
              <option value="大二">大二</option>
              <option value="大三">大三</option>
              <option value="大四">大四</option>
            </select>
          </div>
          <div className='searchDiv'>
            <button className="search_btn" onClick={handleSubmit} >查詢</button>
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

export default Search;
