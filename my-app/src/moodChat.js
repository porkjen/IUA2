import React, { useEffect } from 'react';

const MoodChat = () => {
  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1';
    script.async = true;
    document.body.appendChild(script);

    return () => {
      // Clean up by removing the script when the component unmounts

    };
  }, []);

  return (
    <df-messenger
      chat-icon="https://memeprod.ap-south-1.linodeobjects.com/user-template/3e617727f38aa7267881a27bf05dbcf0.png"
      intent="WELCOME"
      chat-title="IUA"
      agent-id="55ff5cba-f530-499c-9a71-049a913d9975"
      language-code="zh-tw"
    ></df-messenger>
  );
};

export default MoodChat;