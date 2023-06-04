import './Graduation.css';
import React from 'react';
import back from './img/back.png';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';

const Graduation=()=>{
    function Graduation() {
        return (
            <div className="Graduation">    
                <div className='g_bg'>
                    <div>
                        <Link to='/Credit'>
                            <img src={back} alt="回上一頁" className="g_backicon"/>
                        </Link>
                    </div>
                    <div className="g_title">
                        <label className="titleText">畢業門檻</label>
                    </div>
                    <div className='gLable'>
                        <label></label>
                    </div>
                </div>
            </div>
        );
      }
  
      return ( 
          <Routes>
              <Route path="/" element={<Graduation />} />
          </Routes>
       
    );
}

export default Graduation;