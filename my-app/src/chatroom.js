import './chatroom.css';
import React from "react";
import { useParams } from "react-router-dom";
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

class ChatRoom extends React.Component {
   constructor(props) {
     super(props);

     this.state = {
       messages: [],
       newMessage: '',
       from:'',
       connected: false,
       userName: localStorage.getItem('userName'),
       apiRoom: localStorage.getItem('nowRoom'),
       apiRoomName: localStorage.getItem('nowRoomName'),
     };

     this.stompClient = null;
   }

   componentDidMount() {
     this.initializeStompClient();
   }

   initializeStompClient = () => {
     const socket = new SockJS('/chatroom');
     this.stompClient = Stomp.over(socket);

     const { userName, apiRoom } = this.state;
     console.log("Initializing WebSocket connection..."+userName);
     this.stompClient.connect({ user: userName }, (frame) => {
     console.log("WebSocket connection established:", frame);
       this.setState({ connected: true });
       console.log('Connected: ' + frame);
       //this.stompClient.subscribe(`/topic/${apiRoom}`
       this.stompClient.subscribe(`/topic/${apiRoom}`, (messageOutput) => {
         this.showMessageOutput(JSON.parse(messageOutput.body));
       });
        /*
       this.stompClient.subscribe('/user/subscribe', (messageOutput) => {
         this.showMessageOutput(JSON.parse(messageOutput.body));
       });
       */
     }, (error) => {
          console.error("WebSocket connection error:", error);
     });
   };

   sendMessage = () => {
     const { userName, apiRoom, newMessage } = this.state;

     if (newMessage !== '') {
       //this.stompClient.send(`/app/${apiRoom}`, {}, JSON.stringify({ from: userName, text: newMessage }));
       console.log("sN"+userName)
       this.stompClient.send(`/app/${apiRoom}`, {}, JSON.stringify({ from: userName, text: newMessage }));
       this.setState({ newMessage: '' });
     }
   };
    showMessageOutput = (messageOutput) => {
      console.log("Received message:", messageOutput);

      if (messageOutput && messageOutput.message && messageOutput.message.text) {
        const { messages } = this.state;
        this.state.from = messageOutput.message.from; // 檢查 messageOutput.message.from
        console.log("check"+this.state.from);
        const updatedMessages = [...messages, messageOutput.message.text];
        this.setState({ messages: updatedMessages });
      } else {
        console.log("Invalid message format:", messageOutput);
      }
    };
/*
   showMessageOutput = (messageOutput) => {
     const { messages } = this.state;
     const updatedMessages = [...messages, messageOutput.message.text];

     this.setState({ messages: updatedMessages });
   };
*/
   handleMessageChange = (event) => {
     this.setState({ newMessage: event.target.value });
   };

   render() {
     const { messages, newMessage, connected } = this.state;
     console.log("this.state.userName in render:", this.state.userName);
     const Message = ({ content, isSent }) => (
       <div className={`message ${isSent ? 'sent' : 'received'}`}>
         {content}
       </div>
     );

     return (
     <div className="Chatroom">
       <div className="chatroom-header">
         <h1>{this.state.apiRoomName}
         <span className="user-online">{this.state.userName} is online</span>
         </h1>
       </div>
       <div className="chatroom-messages" >
         <div className="message-list" >
           <Message content="Hi!" isSent={false} />
           <Message content="嗨嗨" isSent={false} />
           {messages.map((message, index) => {
               console.log("message:", message);
               console.log("from:", this.state.from);
               console.log("this.state.userName:", this.state.userName);
               return (
                 <Message
                   key={index}
                   content={message}
                   isSent={this.state.from === this.state.userName}
                 />
               );
             })}
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
           <button type="submit" onClick={this.sendMessage} disabled={!connected}>Send</button>
         </div>
       </div>
     </div>
     );
   }
 }
export default ChatRoom;