import './homePage.css';
import React from 'react';
import back from './img/back.png';
import creditImg from "./img/credit.png";
import graduateImg from "./img/information.png";
import courseSelectImg from "./img/selectcourse.png";
import socialImg from "./img/social.png";
import chatroomImg from "./img/chatroom.png";
import treeImg from "./img/tree.png";
import noti from "./img/notifications.png"
import { loginUser } from './cookie';
import {useState, useEffect} from "react";
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';

const HomePage=()=> {
    const userInfo = loginUser();
    const [data, setData] = useState("");
    const [newMessage, setNewMessage] = useState(true);
    function NotificationText({title, message}){
        return(
            <div>
                <li className='noti_title'><strong>{title}</strong></li>
                <li className='noti_message'>{message}</li>
                <div className='line'></div>
            </div>
        );
    }
    
    function HomePage(){
        const [showNotification, setShowNotification] = useState(false);
        

        useEffect(() => {
            if (!data) {
                fetch(`/get_all_notifications?studentID=${userInfo}`, {
                    method: 'GET',
                    headers: {'Content-Type': 'application/json'},
                    })
                .then(response => response.json())
                .then(data => {
                  setData(data);
                  console.log(data);
                })
                .catch(error => {
                  console.error('Error:', error);
                });
            }
          }, [data]); // 添加依賴項data

        const handleBtn = (e) =>{
            if(showNotification){
                setShowNotification(false);
                setNewMessage(false);
            }
            else
                setShowNotification(true);
        }

        /*
        
        {data.map(item => (
                         <NotificationText title={item.title} message={item.message}/>
                        ))}
        */

        
        return(
            <div className="home_bg">
                <div>
                    <Link to='/SignIn'>
                        <img src={back} alt="回上一頁" className="home_backicon"/>
                    </Link>
                </div>
                <div className='notificationImg'>
                    <button className='notiBtn' onClick={handleBtn}>
                        {newMessage && <div className='red_dot'></div>}
                        <img src={noti} className='pic'/>
                    </button>
                </div>
                {showNotification &&
                    <div className='notification_menu'>
                        <ul className=''>
                        {data.map((item,index) => (
                         <NotificationText key={index} title={item.title} message={item.message}/>
                        ))}
                        <NotificationText title={"租屋版"} message={"有符合你要的租屋!"}/>
                        </ul>
                    </div>

                }
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
                            <Link to='/NTOULink'>
                                <button className="graduateButton">
                                    <img src={graduateImg} className='pic'/>
                                </button>
                            </Link>
                        </div>
                    </div>
                    <div class="flex">
                        <div className="courseSelectBtn">
                            <Link to='/CourseSelection'>
                                <button className="courseSelectButton">
                                    <img src={courseSelectImg} className='pic'/>
                                </button>
                            </Link>
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
                            <Link to="/ChatRoomList">
                                <button className="chatRoomButton">
                                    <img src={chatroomImg} className='pic'/>
                                </button>
                            </Link>
                        </div>
                        <div className="othersBtn">
                            <Link to="/moodChatGPT">
                                <button className="othersButton">
                                    <img src={treeImg} className='pic'/>
                                </button>
                            </Link>
                            
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