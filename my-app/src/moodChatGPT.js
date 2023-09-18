import './moodChatGPT.css';
import React from "react";
import back from './img/back.png';
import {Back} from './components/Style.js';
import { Link } from 'react-router-dom';
//test
class MoodChatGPT extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      messages: [],
      newMessage: '',
      isTyping: false,
    };

    this.messageListRef = React.createRef();
  }

  scrollToBottom = () => {
    // Scroll to the bottom of the message list
    if (this.messageListRef.current) {
      this.messageListRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  };

  componentDidUpdate(prevProps, prevState) {
    // Check if new messages have been added
    if (prevState.messages.length !== this.state.messages.length) {
      this.scrollToBottom(); // Scroll to the bottom when new messages arrive
    }
  }

  handleMessageChange = (event) => {
    this.setState({ newMessage: event.target.value });
  };

  handleSubmit = async (event) => {
    event.preventDefault();
    const { messages, newMessage } = this.state;

    if (newMessage.trim() !== '') {
      const userMessage = {
        content: newMessage,
        isSent: true,
      };

      const typingMessage = {
        content: '正在輸入中...',
        isSent: false,
      };

      const updatedMessages = [...messages, userMessage, typingMessage];
      this.setState({ messages: updatedMessages, newMessage: '', isTyping: true }, () => {
        this.scrollToBottom();
      });

      try {
        const response = await fetch('/chatGPT/chat', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(userMessage),
        });

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        const responseData = await response.json();

        const updatedMessagesWithResponse = [...updatedMessages];
        updatedMessagesWithResponse.pop();
        updatedMessagesWithResponse.push(responseData);

        this.setState({ messages: updatedMessagesWithResponse, isTyping: false }, () => {
          this.scrollToBottom();
        });
      } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
      }
    }
  };

  render() {
    const { messages, newMessage, isTyping } = this.state;

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
            {messages.map((message, index) => (
              <div
                key={index}
                className={`message ${message.isSent ? 'sent' : 'received'}`}
                style={{ maxWidth: '70%' }}
              >
                {message.isSent ? (
                  message.content
                ) : (
                  <div className="received">
                    {(index === messages.length - 1 && isTyping) ? '正在輸入中...' :
                      (message.choices && message.choices.length > 0
                        ? message.choices[0].message.content
                        : '')}
                  </div>
                )}
              </div>
            ))}
            <div ref={this.messageListRef} />
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
