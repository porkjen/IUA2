import React, { useEffect } from 'react';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import back from './img/back.png';

const Rules = () => {
  useEffect(() => {
    const script = document.createElement('script');
    script.src = "https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1";
    script.async = true;

    const timerId = setTimeout(() => {
      document.body.appendChild(script);
    }, 100); 

    script.onerror = function() {
      console.error("腳本載入錯誤");
    };
    
    return () => {
      clearTimeout(timerId);
      //document.body.removeChild(script);
    };
  }, []);

  return (
    <><div>
      <Link to='/courseSelection'>
        <img src={back} alt="回上一頁" className="courseSelection_backicon" />
      </Link>
    </div><div>
        <df-messenger
          chat-icon="https://memeprod.ap-south-1.linodeobjects.com/user-template/3e617727f38aa7267881a27bf05dbcf0.png"
          intent="WELCOME"
          chat-title="IUA選課機器人"
          agent-id="f80229ab-fe38-47e3-bac6-3a7072070b6b"
          language-code="zh-tw"
        ></df-messenger>
      </div></>
  );
};

export default Rules;