import './remain.css';
import React from 'react';
import logo from './img/IUAlogo.png';
import bee from './img/bee.PNG';
import bear from './img/bear.PNG';
import {Title}  from './components/ArticleStyle.js';
import {RemainTitle}  from './components/Style.js';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';

const Remain=()=> {
    function Remain() {

        const formData = {
            studentID: "00957025",
          };
        fetch('/remained_credits', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(formData)})
            .then(response => response.json())
            .then(data => {
                    console.log(data);})
            .catch(error => {
                    console.error(error);});
                //Form submission happens here

      return (
        <div className="Remain">    
            <div className='Remain_bg'>
                <div className="Remain_title">
                    <Title>剩餘學分</Title>
                </div>
                <div className='Remain_Course'>
                    <div className='c_Course'>
                        <div className='RemainTitle_position'>
                            <RemainTitle>必修</RemainTitle>
                        </div>
                    </div>
                    <div className='ie_Course'>
                        <div className='RemainTitle_position'>
                            <RemainTitle>系內選修</RemainTitle>
                        </div>
                    </div>
                    <div className='oe_Course'>
                        <div className='RemainTitle_position'>
                            <RemainTitle>系外選修</RemainTitle>
                        </div>
                    </div>
                    <div className='g_Course'>
                        <div className='RemainTitle_position'>
                            <RemainTitle>通識</RemainTitle>
                        </div>
                    </div>
                    <div className='core_Course'>
                        <div className='RemainTitle_position'>
                            <RemainTitle>核心選修</RemainTitle>
                        </div>
                    </div>
                    <div className='pe_Course'>
                        <div className='RemainTitle_position'>
                            <RemainTitle>體育</RemainTitle>
                        </div>
                    </div>
                    <div className='eng_Course'>
                        <div className='RemainTitle_position'>
                            <RemainTitle>門檻-英文</RemainTitle>
                        </div>
                    </div>
                    <div className='swim_Course'>
                        <div className='RemainTitle_position'>
                            <RemainTitle>門檻-游泳</RemainTitle>
                        </div>
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
