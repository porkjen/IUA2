import React, { useEffect } from 'react';

const loadDialogflowMessengerScript = () => {
  const script = document.createElement('script');
  script.src = 'https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1';
  script.async = true;
  document.body.appendChild(script);

  return script;
};

const MoodChat = () => {
  useEffect(() => {
    // 載入 Dialogflow Messenger 腳本
    const dialogflowMessengerScript = loadDialogflowMessengerScript();

    return () => {
      // 在元件卸載時清除腳本
      document.body.removeChild(dialogflowMessengerScript);
    };
  }, []);

  return (
    <df-messenger
      chat-icon="https://patchwiki.biligame.com/images/bsywy/0/04/gv3sp5mp2gh2caxsk9nkoh6vax0hgo3.png"
      intent="WELCOME"
      chat-title="心情樹洞"
      agent-id="c054175b-675f-49ae-93e2-5671f742914a"
      language-code="en"
    ></df-messenger>
  );
};

export default MoodChat;
