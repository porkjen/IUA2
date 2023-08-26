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
    const [selectedDepartment, setSelectedDepartment] = useState('資工');
    const [selectedType, setSelectedType] = useState('必修');
    const [selectedGrade, setSelectedGrade] = useState('大一');

    const handleSubmit = (e) => {
      e.preventDefault();
      const queryParams = new URLSearchParams({
        major: selectedDepartment,
        category: selectedType,
        grade: selectedGrade
      });

      const url = '/course_search?' + queryParams.toString();

      const formData = {
        "major": selectedDepartment,
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
              className="selectDepartment"
              value={selectedDepartment}
              onChange={(e) => setSelectedDepartment(e.target.value)}
            >
              <option value="商船">商船學系</option>
              <option value="航管">航運管理學系</option>
              <option value="運輸">運輸科學系</option>
              <option value="輪機">輪機工程學系</option>
              <option value="食科">食品科學系</option>
              <option value="養殖">水產養殖學系</option>
              <option value="生科">生命科學暨生物科技學系</option>
              <option value="環漁">環境生物與漁業科學學系</option>
              <option value="機械">機械與機電工程學系</option>
              <option value="系工">系統工程暨造船學系</option>
              <option value="河工">河海工程學系</option>
              <option value="電機">電機工程學系</option>
              <option value="資工">資訊工程學系</option>
              <option value="通訊">通訊與導航工程學系</option>
              <option value="光電">光電與材料科技學系</option>
            </select>
          </div>
          <div className="selectDiv2">
            <select
              className="selectMust"
              value={selectedType}
              onChange={(e) => setSelectedType(e.target.value)}
            >
              <option value="必修">必修</option>
              <option value="選修">選修</option>
            </select>
          </div>
          <div className="selectDiv3">
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