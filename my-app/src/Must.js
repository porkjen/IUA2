import './Must.css';
import React from 'react';
import back from './img/back.png';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';

const Must=()=>{
    function Must() {
        return (
            <div className="Must">    
                <div className='must_bg'>
                    <div>
                        <Link to='/Search'>
                            <img src={back} alt="回上一頁" className="must_backicon"/>
                        </Link>
                    </div>
                    <div className="must_title">
                        <label className="titleText">必選修課程</label>
                    </div>
                    <div className='mustLable'>
                        <label></label>
                    </div>
                    

                </div>
            </div>
        );
      }
  
      return ( 
          <Routes>
              <Route path="/" element={<Must />} />
          </Routes>
       
    );
}

export default Must;