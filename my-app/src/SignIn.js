import './SignIn.css';
import React from 'react';
import Signlogo from './img/blue_logo.PNG';
import signIn from './img/signIn.PNG';
import cat1 from './img/SignIn1.png';
import cat2 from './img/SignIn2.PNG';
import cat3 from './img/SignIn3.PNG';
import cat4 from './img/SignIn4.PNG';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';
import {useNavigate} from 'react-router-dom';
import {useState, useEffect} from "react";
import axios from 'axios';
import { useCookies } from 'react-cookie';
import { onLogin } from "./cookie.js";
import { setAuthToken,setFcmToken } from "./utils";
import { MessagePayload, onMessage } from "firebase/messaging";
import {requestToken, onMessageListener} from './firebase.js';

const SignIn=()=> {

    let navigate = useNavigate();
    const [cookies, setCookie] = useCookies(['token']);
    const [error, setError] = useState(false);
    const [setToken, setTokenFound] = useState('');
    const [notification, setNotification] = useState({title: '', body: ''});

    //註冊sw
    if ('serviceWorker' in navigator) {
      console.log('sw in navigator!');
      navigator.serviceWorker.register('/firebase-messaging-sw.js')
      .then(reg => {
        console.log('完成 SW 設定!', reg);
      })
      .catch(err => console.log('Error!', err));
    }
    //取得token
    requestToken(setTokenFound);
    //console.log('the token from fcm: ', setToken);

    //收到通知
    onMessageListener().then(payload => {
      setNotification({title: payload.notification.title, body: payload.notification.body})
      console.log(payload);
    }).catch(err => console.log('failed: ', err));

    //通知權限
    function requestPermission() {
      console.log('Requesting permission...');
      Notification.requestPermission().then((permission) => {
      if (permission === 'granted') {
          console.log('Notification permission granted.');
      } else {
          console.log('Unable to get permission to notify.');
        }
      });
    }


    function ErrorPassword(){
      return(
        <div className='signIn_error'>
          <div className='error_text'>密碼錯誤!請重試</div><br/>
          <Link to="/signIn">
            <button className='signIn_error_yes' onClick={setError(false)}>確定</button>
          </Link>
        </div>
      );
    }

    function SignIn() {
      const [student_id, setStudent_id] = useState("");
      const [password, setPassword] = useState("");
      const [waiting, setWaiting] = useState(false);
      const [responseStatus, setResponseStatus] = useState("");
      const navigate = useNavigate();
      const handleStudent_idChange = (event) => {
        setStudent_id(event.target.value);
      };

      const handlePasswordChange = (event) => {
              setPassword(event.target.value);
      };
      const handleSubmit = (e) => {
        e.preventDefault();
        //alert(`The name you entered was: ${student_id}`);
        setWaiting(true);
        const formData = {
                        studentID: student_id,
                        password: password,
                      };
                      fetch('/login', {
                            method: 'POST',
                            headers: {
                              'Content-Type': 'application/json',
                              'fcmToken': `${setToken}`,
                            },
                            body: JSON.stringify(formData)
                          })
                          .then(response => {
                            console.log(response.status);
                            if(response.status==200){
                              navigate("/homePage");
                            }
                            else if(response.status==400){
                              alert("輸入錯誤");
                              setError(true);
                            }
                            else if(response.status==201){
                              navigate('/nickName', {
                                state: {
                                  studentID: student_id,
                                }
                              });
                            }
                            console.log(response);
                            return response.json();
                          })
                          .then(data => {
                            onLogin(student_id);
                            console.log(data.token);//token
                            setFcmToken(setToken);
                            setAuthToken(data.token);
                          })
                          .catch(error => {
                            console.error(error);
                          });
        
         
      }
      return (
        
        <div className="SignIn">
          <requestPermission/>
            <div className='SignIn_bg'>
                <div className='SignIn_signIn'>
                    <br/>
                    {!waiting && !error &&
                    <div>
                      <div className="SignIn_title">
                        <div className="SignIn_title-img">
                            <img src={Signlogo} alt="IUA" />
                        </div>
                        <div className="SignIn_title-text">
                            <img src={signIn} alt="IUA" />
                        </div>
                     </div>
                      <form className="SignIn_submitForm" onSubmit={handleSubmit}>
                        <label>學號:</label><br/>
                        <input type="text" name="student_id" 
                        onChange={handleStudent_idChange}
                        value={student_id}/>
                        <br/>
                        <label>密碼:</label><br/>
                        <input type="password" name="password" 
                        onChange={handlePasswordChange}
                        value={password}/>
                        <br/>
                        <br/>
                        <div className="SignIn_submitButton_place">
                            <button type="submit" className="SignIn_submitButton" >
                                <span className="button_text">登入</span>
                            </button>
                        </div>
                      </form>
                    </div>

                      
                    }
                    {waiting && !error &&
                      <div className='LoadingText'>
                          <div class="preloader">
                            Loading
                            <div class="dot1"></div>
                            <div class="dot2"></div>
                            <div class="dot3"></div>
                          </div>
                    </div>
                    }
                    {error && 
                      <ErrorPassword/>
                    }

                    
                </div>
                    <div className="SignIn_img1">
                        <img src={cat1} alt="IUA" />
                    </div>
                    <div className="SignIn_img2">
                        <img src={cat2} alt="IUA" />
                    </div>
                    <div className="SignIn_img3">
                        <img src={cat3} alt="IUA" />
                    </div>
                    <div className="SignIn_img4">
                        <img src={cat4} alt="IUA" />
                    </div>

            </div>
        </div>
      );
    }

    return (

           
                <Routes>
                    <Route path="/" element={<SignIn />} />
                </Routes>
             
    );
}

export default SignIn;
