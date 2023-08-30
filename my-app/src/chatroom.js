import './chatroom.css';
import React from "react";
import { useParams } from "react-router-dom";

class ChatRoom extends React.Component {
  state = {
    messages: [],
    newMessage: ''
  };

  handleMessageChange = (event) => {
    this.setState({ newMessage: event.target.value });
  };

  handleSubmit = (event) => {
    event.preventDefault();
    const { messages, newMessage } = this.state;
    if (newMessage.trim() !== '') {
      const updatedMessages = [...messages, newMessage];
      this.setState({ messages: updatedMessages, newMessage: '' });
    }
  };

  
  messageListRef = React.createRef();

  scrollToBottom = () => {
    if (this.messageListRef.current) {
      this.messageListRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  };

  componentDidUpdate(prevProps, prevState) {
    if (prevState.messages.length !== this.state.messages.length) {
      this.scrollToBottom();
    }
  }

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
          <h1>ChatRoom</h1>
        </div>

        <div className="chatroom-messages" >
          <div className="message-list" >
            <Message content="Hi!" isSent={false} />
            <Message content="嗨嗨" isSent={false} />
            {messages.map((message, index) => (
              <div key={index} className='sent'>{message}</div>
            ))}
            <div ref={this.messageListRef} />
          </div>
        </div>
        <div className="chatroom-input">
          <form onSubmit={this.handleSubmit}>
            <input
              type="text"
              placeholder='輸入想說的話...'
              value={newMessage}
              onChange={this.handleMessageChange}
            />
            <button type="submit">Send</button>
          </form>
        </div>
      </div>
    );
  }
}

export default ChatRoom;