import React, { useEffect } from 'react';

const MoodChat = () => {
  useEffect(() => {
    const script = document.createElement('script');
    script.src = "https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1";
    script.async = true;
    document.body.appendChild(script);
    return () => {
      document.body.removeChild(script);
    }
  }, []);

  return (
    <div><df-messenger
    chat-icon="https://patchwiki.biligame.com/images/bsywy/0/04/gv3sp5mp2gh2caxsk9nkoh6vax0hgo3.png"
    intent="WELCOME"
    chat-title="心情樹洞"
    agent-id="c054175b-675f-49ae-93e2-5671f742914a"
    language-code="en"
  ></df-messenger></div>
    
  );
};

export default MoodChat;
