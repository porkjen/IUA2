import './moodChatGPT.css';
import React from "react";
import back from './img/back.png';
import {Back}  from './components/Style.js';
import { Link } from 'react-router-dom';

class MoodChatGPT extends React.Component {
  state = {
    messages: [],
    newMessage: ''
  };

  handleMessageChange = (event) => {
    this.setState({ newMessage: event.target.value });
  };

  handleSubmit = async (event) => {
    event.preventDefault();
    const { messages, newMessage } = this.state;
  
    if (newMessage.trim() !== '') {
      const messageObject = {
        content: newMessage,
        isSent: true,
      };
  
      try {
        const response = await fetch('/chatGPT/chat', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(messageObject),
        });
  
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
  
        // Clear the input field and add the user's message to the chat
        const updatedMessages = [...messages, messageObject];
        this.setState({ messages: updatedMessages, newMessage: '' });
      } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
        // Handle the error, e.g., show an error message to the user
      }
    }
  };
  

  render() {
    const { messages, newMessage } = this.state;
    const Message = ({ content, isSent }) => {
      return (
        <div className={`message ${isSent ? 'sent' : 'received'}`}>
          {content}
        </div>
      );
    };
    
    return (
      <div className="Chatroom">
        <div className="chatroom-header">
          <Link to='/homePage'>
              <Back src={back} alt="回上一頁" />
          </Link>
          <h1>心情樹洞</h1>
        </div>
        <div className="chatroom-messages" >
         <div className="message-list" >
            <Message content="Hi!" isSent={false} />
            <Message content="嗨嗨" isSent={false} />
            <Message content="嗨嗨" isSent={false} />
            <Message content="嗨嗨" isSent={false} />
            {messages.map((message, index) => (
              <div key={index} className='sent'>{message}</div>
            ))}
         </div>
       </div>
       <div className="chatroom-input">
         <div className="enter">
           <input
             type="text"
             id="text"
             placeholder='輸入想說的話...'
             value={newMessage}
             onChange={this.handleMessageChange}
           />
           <button type="submit" onClick={this.handleSubmit} >Send</button>
         </div>
       </div>
      </div>
    );
  }
}

export default MoodChatGPT;