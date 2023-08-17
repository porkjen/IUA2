import './Core.css';
import React from 'react';
import orangefox from './img/orangefox.PNG';
import whitefox from './img/whitefox.PNG';
import back from './img/back.png';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes, Route, useLocation } from 'react-router-dom';
import { Coreinfo } from './components/Style';

const Core=()=>{
    function Core() {
        const location = useLocation();
        const CResult = location.state?.CResult || []; // 獲取查詢結果
        return (
            <div className="Core">    
                <div className='core_bg'>
                    <div>
                        <Link to='/coreSearch'>
                            <img src={back} alt="回上一頁" className="core_backicon"/>
                        </Link>
                    </div>
                    <div className="core_title">
                        <label className="titleText">核心選修</label>
                    </div>
                    <div className='coreLable'>
                        <div className="c_scrollableContainer">
                            {CResult.map((item) => (
                                <Coreinfo key={item.id}>
                                {item.name}<br />{item.number}&emsp;{item.teacher}<br />上課時間: {item.time}
                                </Coreinfo>
                            ))}
                        </div>  
                    </div>
                    <div className="core_pic">
                      {/* <div className="orangefox">
                            <img src={orangefox} alt="IUA" />
                        </div> */}
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