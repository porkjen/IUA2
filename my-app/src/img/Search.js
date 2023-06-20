import './Search.css'
import React from 'react';
import back from './img/back.png';
import duck from './img/duck.PNG';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';

const Search=()=>{
    function Search() {
        return (
            <div className="Search">    
                <div className='search_bg'>
                    <div>
                        <Link to='/Credit'>
                            <img src={back} alt="回上一頁" className="search_backicon"/>
                        </Link>
                    </div>
                    <div className="search_title">
                        <label className="titleText">必選修課程查詢</label>
                    </div>
                    <div className="selectDiv">
                        <select className="selectMust">
                            <option>必修</option>
                            <option>選修</option>
                        </select>
                    </div>
                    <div className="selectDiv2">
                        <select className="selectGrade">
                            <option>大一</option>
                            <option>大二</option>
                            <option>大三</option>
                            <option>大四</option>
                        </select>
                    </div>
                    <div className='searchDiv'>
                        <Link to='/Must'>
                            <button className="search_btn">查詢</button>
                        </Link>
                    </div>
                    <div><img src={duck} className='duckpic'/></div>
                </div>
            </div>
        );
      }
  
      return ( 
          <Routes>
              <Route path="/" element={<Search />} />
          </Routes>
       
    );
}

export default Search;