import './homePage.css';
import React from 'react';
import back from './img/back.png';
import creditImg from "./img/credit.png";
import graduateImg from "./img/graduate.png";
import courseSelectImg from "./img/selectcourse.png";
import socialImg from "./img/social.png";
import chatroomImg from "./img/chatroom.png";
import calendarImg from "./img/calendar.png";
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';

const HomePage=()=> {
    function HomePage(){
        return(
            <div className="home_bg">
                <div>
                    <Link to='/SignIn'>
                        <img src={back} alt="回上一頁" className="home_backicon"/>
                    </Link>
                </div>
                <div className="allBtn">
                    <div class="flex">
                        <div className="creditBtn">
                            <Link to='/Credit'>
                                <button className="creditButton" >
                                    <img src={creditImg} className='pic'/>
                                </button>
                            </Link>
                        </div>
                        <div className="graduateBtn">
                            <button className="graduateButton">
                                <img src={graduateImg} className='pic'/>
                            </button>
                        </div>
                    </div>
                    <div class="flex">
                        <div className="courseSelectBtn">
                            <button className="courseSelectButton">
                                <img src={courseSelectImg} className='pic'/>
                            </button>
                        </div>
                        <div className="socialBtn">
                            <Link to="/choose">
                                <button className="socialButton">
                                    <img src={socialImg} className='pic'/>
                                </button>
                            </Link>
                        </div>
                    </div>
                    <div class="flex">
                        <div className="chatRoomBtn">
                            <button className="chatRoomButton">
                                <img src={chatroomImg} className='pic'/>
                            </button>
                        </div>
                        <div className="othersBtn">
                            <button className="othersButton">
                                <img src={calendarImg} className='pic'/>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
    return (    
        <Routes>
            <Route path="/" element={<HomePage />}/>
        </Routes>

    );
}
export default HomePage;