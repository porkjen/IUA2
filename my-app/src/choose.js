import './choose.css';
import React from 'react';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route, useNavigate } from 'react-router-dom';
import bee1 from './img/bee1.png';
import bee2 from './img/bee2.png';
import bear from './img/bear.PNG';
import logo from './img/gray_logo.png';

const Choose=()=> {

    let navigate = useNavigate();
    const toRent = (e) => {
        e.preventDefault();
        navigate("/rent", {
          state: {
            fromSearch:false,},});
      }

    const toChangeClass = (e) => {
        e.preventDefault();
         navigate("/changeClass")
      }

    const toFood = (e) => {
        e.preventDefault();
        navigate("/food", {
          state: {
            fromSearch:false,},});
      }

    function Choose() {
      return (
        <div className="Choose"> 
            <div><img src={bee1} className='bee1Img'></img></div>
            <div><img src={bee2} className='bee2Img'></img></div>
            <div className='ChoosePosition'>
                <button className='chooseToRent' onClick={toRent}>租屋版</button>
                <button className='chooseToChangeClass' onClick={toChangeClass}>換課版</button>
                <button className='chooseToFood' onClick={toFood}>美食版</button>
            </div>
            <div><img src={bear} className='bearImg'></img></div>
        </div>
      );
    }

    return (

           
                <Routes>
                    <Route path="/" element={<Choose />} />
                </Routes>
             
    );
}

export default Choose;
