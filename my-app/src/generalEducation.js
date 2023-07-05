import './generalEducaton.css';
import React from 'react';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';
import back from './img/back.png';

const GeneralEducaton=()=>{
    function GeneralEducaton(){
        return(
            <div className='GeneralEducation'>
                <div className='g_education'>
                    <div>
                        <Link to='/Credit'>
                            <img src={back} alt="回上一頁" className="g_backicon"/>
                        </Link>
                    </div>
                    <div className="g_title">
                        <label className="titleText">通識領域</label>
                    </div>
                    <div className="selectDiv">
                        <select className="selectMust">
                            <option>人格</option>
                            <option>民主</option>
                            <option>自然</option>
                            <option>歷史</option>
                            <option>全球</option>
                            <option>經典</option>
                            <option>美學</option>
                            <option>科技</option>
                        </select>
                    </div>
                    <div className='g_Lable'>
                        <label></label>
                    </div>
                </div>
            </div>
            
        );
    }
    return (    
        <Routes>
            <Route path="/" element={<GeneralEducaton />}/>
        </Routes>
    
    );
}

export default GeneralEducaton;