import './history.css';
import {Back}  from './components/Style.js';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';
import back from './img/back.png';
import duck from './img/duck.PNG';


function History() {
    return (
    <div className="History"> 
        <Link to='/recommend'>
            <Back src={back} alt="回上一頁" />
        </Link>
        <div className="history_title">
            <label className="titleText">請選擇想查詢的歷史紀錄</label>
        </div>
        <div className="selectDiv">
            <select
              className="selectHistory"
            //   value={selectedType}
            //   onChange={(e) => setSelectedType(e.target.value)}
            >
              <option value="必修">空堂選修</option>
              <option value="選修">空堂通識</option>
              <option value="選修">空堂體育</option>
              <option value="選修">喜好選修</option>
            </select>
          </div>
        <div className='searchDiv'>
            <button className="search_btn"  >查詢</button>
        </div>
        <div><img src={duck} className='duckpic' /></div>
        
    </div>
    );
}


export default History;
