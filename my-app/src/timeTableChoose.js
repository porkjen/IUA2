import './timeTableChoose.css';
import React, { useEffect, useState } from 'react';
import back from './img/back.png';
import duck from './img/duck.PNG';
import { BrowserRouter as Router, Link } from 'react-router-dom';
import { Routes, Route, useLocation } from 'react-router-dom';
import { Mustinfo, MustDetailBtn } from './components/Style';
import Modal from 'react-modal';

const TimeTableChoose = () =>{
    function TimeTableChoose(){
        return(
            <div className="timeTableChoose_bg">
                <Link to='/courseSelection'>
                    <img src={back} alt="回上一頁" className="ttc_backicon" />
                </Link>
                <div className='ttcBtnPos'>
                    <Link to='/timeTable'>
                        <button className='ttcBtn'>我的課表</button>
                    </Link>
                    <Link to='/preTimeTable'>
                        <button className='ttcBtn'>預選課表</button>
                    </Link>
                </div>
                <div><img src={duck} className='ttc_duckpic' /></div>
            </div>
        );
    }
    return (
        <Routes>
          <Route path="/" element={<TimeTableChoose />} />
        </Routes>
      );
}

export default TimeTableChoose;