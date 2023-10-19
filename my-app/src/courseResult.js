import './emptyResult.css';
import React, { useEffect, useState } from 'react';
import back from './img/back.png';
import { Link } from 'react-router-dom';
import { Routes, Route, useLocation } from 'react-router-dom';
import { Courseinfo } from './components/Style';

function CourseResult() {
    const location = useLocation();
    const cResult = location.state?.cResult || []; // 獲取查詢結果

  return (
    <div className="EmptyResult">
      <div className='EmptyResult_bg'>
        <div>
          <Link to='/courseRate'>
            <img src={back} alt="回上一頁" className="CourseRate_backicon" />
          </Link>
        </div>
        <div className="EmptyResult_title">
        <label className="titleText">已修過課程喜好推薦結果</label>
        </div>
        <div className='EmptyResultLable'>
        <div className="EmptyResult">
        <div className="er_scrollableContainer">
            {cResult && cResult.map((item) => (
            <div >
                <Courseinfo>
                課名：{item.name}<br />
                領域：{item.field}<br />
                類別：{item.category}
                </Courseinfo>
            </div>
            ))}
        </div>
        </div>
        </div>
      </div>
    </div>
  );
}

export default CourseResult;

