import './chatroom.css';
import React from "react";

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
        <div className="message-list">
          <Message content="Hi!" isSent={false} />
          <Message content="test" isSent={false} />
          {messages.map((message, index) => (
            <div key={index} className='sent'>{message}</div>
          ))}
        </div>
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
    );
  }
}

export default ChatRoom;