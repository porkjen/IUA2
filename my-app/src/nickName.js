import './nickName.css';
import React from 'react';
import Signlogo from './img/blue_logo.PNG';
import cat1 from './img/SignIn1.png';
import cat2 from './img/SignIn2.PNG';
import cat3 from './img/SignIn3.PNG';
import cat4 from './img/SignIn4.PNG';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route,useLocation, useNavigate } from 'react-router-dom';
import {useState} from "react";
import { loginUser } from './cookie';
import { useCookies } from 'react-cookie';
import { getAuthToken } from './utils';

const NickName=()=> {
    function NickName() {
      let navigate = useNavigate();
      const [nickName_id, setNickName_id] = useState("");
      const userInfo = loginUser();
      const token = getAuthToken();
      const handleNickName_idChange = event => {
        setNickName_id(event.target.value);
      };
      const handleSubmit = (e) => {
        e.preventDefault();
        const student_id = loginUser();
        const formData = {
                        "studentID": userInfo,
                        "nickname": nickName_id
                      };
                      fetch('/nickname', {
                            method: 'POST',
                            headers: {
                              'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(formData)
                          })
                          .then(response => response.text())
                          .then(data => 
                            console.log(data)
                          )
                          .catch(error => 
                            console.error(error)
                          );
                          navigate("/homePage"); 
      }
      return (
        <div className="NickName">    
            <div className='NickName_bg'>
                
                <div className="NickName_title">
                    <div className="NickName_title-img">
                        <img src={Signlogo} alt="IUA" />
                    </div>
                </div>
                <br/>
                <form className="NickName_submitForm" onSubmit={handleSubmit}>
                    <div className="NickName_label_loc">
                        <div  className="hint_label">
                            <label className="first_label">填寫你想要被稱呼的暱稱吧!</label><br/>
                        </div>
                        <input type="text" name="nickName_id" 
                        onChange={handleNickName_idChange}
                        value={nickName_id}/>
                    </div>
                    <br/>
                        <div className="NickName_submitButton_place">
                        <button type="submit" className="NickName_submitButton" >
                            <span className="button_text">確認</span>
                        </button>
                    </div>

                </form>
                <div className="NickName_img1">
                    <img src={cat1} alt="IUA" />
                </div>
                <div className="NickName_img2">
                    <img src={cat2} alt="IUA" />
                </div>
                <div className="NickName_img3">
                    <img src={cat3} alt="IUA" />
                </div>
                <div className="NickName_img4">
                    <img src={cat4} alt="IUA" />
                </div>

            </div>
        </div>
      );
    }

    return (
           
                <Routes>
                    <Route path="/" element={<NickName />} />
                </Routes>
             
    );
}

export default NickName;
