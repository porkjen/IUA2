import './remain.css';
import React from 'react';
import logo from './img/IUAlogo.png';
import bee from './img/bee.PNG';
import bear from './img/bear.PNG';
import {Title}  from './components/ArticleStyle.js';
import {RemainTitle}  from './components/Style.js';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';
import {useEffect,useState} from "react";

const Remain=()=> {
    function Remain() {

        const [data, setData] = useState(null);
        const formData = {
            studentID: "00957030",
          };
          useEffect(() => {
            if (!data) {
                fetch('/remained_credits', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(formData)
                  })
                    .then(response => response.json())
                    .then(data => {
                      setData(data);
                      console.log(data);
                    })
                    .catch(error => {
                      console.error(error);
                    });
            }
          }, [data]); // 添加依賴項data
          
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
                                    {data && <RemainTitle>必修&emsp;{data.required}學分</RemainTitle>}
                                    </div>
                                </div>
                                <div className='ie_Course'>
                                    <div className='RemainTitle_position'>
                                    {data && <RemainTitle>系內選修&emsp;{data.deptOptional}學分</RemainTitle>}
                                    </div>
                                </div>
                                <div className='oe_Course'>
                                    <div className='RemainTitle_position'>
                                    {data && <RemainTitle>系外選修&emsp;{data.optional}學分</RemainTitle>}
                                    </div>
                                </div>
                                <div className='g_Course'>
                                    <div className='RemainTitle_position'>
                                        {data && <RemainTitle>通識&emsp;{data.general}學分</RemainTitle>}
                                    </div>
                                </div>
                                <div className='core_Course'>
                                    <div className='RemainTitle_position'>
                                        {data && <RemainTitle>核心選修&emsp;{data.kernal}學分</RemainTitle>}
                                    </div>
                                </div>
                                <div className='pe_Course'>
                                    <div className='RemainTitle_position'>
                                        {data && <RemainTitle>體育&emsp;{data.pe}學分</RemainTitle>}
                                    </div>
                                </div>
                                <div className='eng_Course'>
                                    <div className='RemainTitle_position'>
                                        {data && <RemainTitle>門檻-英文&emsp;{data.eng}</RemainTitle>}
                                    </div>
                                </div>
                                <div className='swim_Course'>
                                    <div className='RemainTitle_position'>
                                        {data && <RemainTitle>門檻-游泳&emsp;{data.swimming}</RemainTitle>}
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