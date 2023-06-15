import './choose.css';
import React from 'react';
import logo from './img/IUAlogo.png';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route, useNavigate } from 'react-router-dom';

const Choose=()=> {

    let navigate = useNavigate();
    const toRent = (e) => {
        e.preventDefault();
         navigate("/rent")
      }

    const toChangeClass = (e) => {
        e.preventDefault();
         navigate("/changeClass")
      }

    const toFood = (e) => {
        e.preventDefault();
         navigate("/food")
      }

    function Choose() {
      return (
        <div className="Choose"> 
            <div className='ChoosePosition'>
                <button className='chooseToRent' onClick={toRent}>租屋版</button>
                <button className='chooseToChangeClass' onClick={toChangeClass}>換課版</button>
                <button className='chooseToFood' onClick={toFood}>美食版</button>
            </div>
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
