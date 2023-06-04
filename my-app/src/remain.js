import './remain.css';
import React from 'react';
import logo from './img/IUAlogo.png';
import bee from './img/bee.PNG';
import bear from './img/bear.PNG';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';

const Remain=()=> {
    function Remain() {
      return (
        <div className="Remain">    
            <div className='bg'>
                <div className="title">
                    <label className="titleText">剩餘學分查詢</label>
                </div>
                <div className='Course'>
                    <div className='g_Course'>
                        <label></label>
                    </div>
                    <div className='e_Course'>
                        <label></label>
                    </div>
                    <div className='c_Course'>
                        <label></label>
                    </div>
                </div>
                
                    <div className="bee">
                        <img src={bee} alt="IUA" />
                    </div>
                    <div className="bear">
                        <img src={bear} alt="IUA" />
                    </div>
                    

            </div>
            
        </div>
      );
    }

    return (

           
        <Routes>
            <Route path="/" element={<Remain />} />
        </Routes>
     
);
}

export default Remain;
