import React, { useEffect } from 'react';

const Rules = () => {
  useEffect(() => {
    const script = document.createElement('script');
    script.src = "https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1";
    script.async = true;

    const timerId = setTimeout(() => {
      document.body.appendChild(script);
    }, 100); 

    return () => {
      clearTimeout(timerId);
      //document.body.removeChild(script);
    };
  }, []);

  return (
    <div>
      <df-messenger
        chat-icon="https://memeprod.ap-south-1.linodeobjects.com/user-template/3e617727f38aa7267881a27bf05dbcf0.png"
        intent="WELCOME"
        chat-title="IUA選課機器人"
        agent-id="f80229ab-fe38-47e3-bac6-3a7072070b6b"
        language-code="zh-tw"
        ></df-messenger>
    </div>
  );
};

export default Rules;