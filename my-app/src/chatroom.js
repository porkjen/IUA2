import './chatroom.css';
import React from "react";
import {useEffect,useState} from "react";
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import back from './img/back.png';
import {Back}  from './components/Style.js';
import { Link } from 'react-router-dom';

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
       poster: localStorage.getItem('poster'),
       inputName: '', // 新增的姓名輸入狀態
       messageData: [],
     };

     this.stompClient = null;
     this.messageListRef = React.createRef();
   }

   componentDidMount() {
    this.fetchData();
    this.fetchMessageData();
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

      fetchData() {
          const { apiRoom } = this.state;
          const queryParams = new URLSearchParams({
            where: apiRoom
          });

          const url = '/updateRoom?' + queryParams.toString();

          const data = {
             "where": apiRoom
          };

          fetch(url, {
            method: 'POST',
             headers: {
               'Content-Type': 'application/json'
             },
                body: JSON.stringify(data)
             }).then(response => response.json())
               .then(data => {
                  console.log("success!!!")
               }).catch(error =>
                  console.error(error)
               );
      }

      fetchMessageData() {
        const { apiRoom } = this.state;
        const queryParams = new URLSearchParams({
          where: apiRoom 
        });
    
        const url = '/loadChatRecord?' + queryParams.toString();
    
        const formData = {
          "where": apiRoom
        };
    
        fetch(url, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(formData)
        })
          .then(response => response.json())
          .then(data => {
            console.log(data);
            this.setState({ messageData: data });
          })
          .catch(error => {
            console.error(error);
          });
      }

   sendMessage = () => {
     const { userName, apiRoom, newMessage } = this.state;

     if (newMessage !== '') {
       //this.stompClient.send(`/app/${apiRoom}`, {}, JSON.stringify({ from: userName, text: newMessage }));
       console.log("I want to say1 : "+newMessage);
       console.log("I want to say2 : "+apiRoom);
       //const myData = localStorage.getItem('nowRoom');
       this.stompClient.send(`/app/${apiRoom}`, {}, JSON.stringify({ from: userName, text: newMessage}));
       this.setState({ newMessage: '' });
     }
   };
    showMessageOutput = (messageOutput) => {
      console.log("Received message:", messageOutput);

      if (messageOutput && messageOutput.message && messageOutput.message.text) {
        const { messages, userName } = this.state;
        //this.state.from = messageOutput.message.from; // 檢查 messageOutput.message.from
        const newMessage = {
          text: messageOutput.message.text,
          isSentByCurrentUser: messageOutput.message.from === userName
        };
        //console.log("check1"+messageOutput.message.from);
        //console.log("check2"+this.state.from);
        const updatedMessages = [...messages, newMessage];
        //console.log("check3"+updatedMessages);
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
   handleNameChange = (event) => {
     this.setState({ inputName: event.target.value });
   };

   handleNameSubmit = () => {
     this.setState({ userName: this.state.inputName });
   };

   handleMessageChange = (event) => {
     this.setState({ newMessage: event.target.value });
   };

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
   

   render() {
    const { messages, newMessage, connected, userName, apiRoomName, inputName, messageData, poster } = this.state;
    console.log("this.state.userName in render:", this.state.userName);
    const isPosterSameAsUserName = poster === userName;
    const Message = ({ content, isSent }) => (
      <div className={`message ${isSent ? 'sent' : 'received'}`}>
        {content}
      </div>
    );

     return (
     <div className="Chatroom">
       <div className="chatroom-header">
          <Link to='/ChatRoomList'>
              <Back src={back} alt="回上一頁" />
          </Link>
         <h1>{this.state.apiRoomName}
         {isPosterSameAsUserName && <button type="confirmed">已確認</button>}
         </h1>
       </div>
       <div className="chatroom-messages" >
         <div className="message-list" >
           {messageData.map((message, index) => {
               console.log("message:", message);
               return (
                 <Message
                   key={index}
                   content={message.text}
                   isSent={message.from === userName}
                 />
               );
             })}
           {messages.map((message, index) => {
               console.log("message:", message);
               return (
                 <Message
                   key={index}
                   content={message.text}
                   isSent={message.isSentByCurrentUser}
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
