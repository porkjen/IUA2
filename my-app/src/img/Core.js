import './Core.css';
import React from 'react';
import orangefox from './img/orangefox.PNG';
import whitefox from './img/whitefox.PNG';
import back from './img/back.png';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';

const Core=()=>{
    function Core() {
        return (
            <div className="Core">    
                <div className='core_bg'>
                    <div>
                        <Link to='/Credit'>
                            <img src={back} alt="回上一頁" className="core_backicon"/>
                        </Link>
                    </div>
                    <div className="core_title">
                        <label className="titleText">核心選修</label>
                    </div>
                    <div className='coreLable'>
                        <label></label>
                    </div>
                    <div className="core_pic">
                      <div className="orangefox">
                            <img src={orangefox} alt="IUA" />
                        </div>
                        <div className="whitefox">
                            <img src={whitefox} alt="IUA" />
                        </div>  
                    </div>
                </div>
            </div>
        );
      }
  
      return ( 
          <Routes>
              <Route path="/" element={<Core />} />
          </Routes>
       
    );
}

export default Core;