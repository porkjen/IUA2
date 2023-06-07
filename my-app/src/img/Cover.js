import './Cover.css';
import React from 'react';
import logo from './img/IUAlogo.png';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';

const Cover=()=> {
    function Cover() {
      return (
        <div className="Cover"> 
            <div className="Cover_bg">
                <div className='Cover_signIn'>
                    <div className="Cover_title">
                        <div className="Cover_title-text">
                            <h1>IUA</h1>
                        </div>
                        <div className="Cover_title-img">
                            <img src={logo} alt="IUA" />
                        </div>
                    </div>
                    <br/>
                    <Link to='/SignIn'>
                        <button className="Cover_welcomeBtn"><span>Welcome</span></button>
                    </Link>
                
                </div>
            </div>
        </div>
      );
    }

    return (

           
                <Routes>
                    <Route path="/" element={<Cover />} />
                </Routes>
             
    );
}

export default Cover;
