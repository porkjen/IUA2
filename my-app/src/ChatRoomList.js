// import './ChatRoomList.css';
// import React from 'react';
// import { useEffect, useState, useRef,  } from "react";
// import { Link } from 'react-router-dom';
// import SockJS from 'sockjs-client';
// import { Stomp } from '@stomp/stompjs';

// const ChatRoomList = () => {
//   const [searchTerm, setSearchTerm] = useState('');
//   const [filteredChatRooms, setFilteredChatRooms] = useState([]);

//   const chatRooms = [
//     { id: 'R1', name: 'room1' },
//     { id: 'R2', name: 'room2' },
//     { id: 'R3', name: '徵室友' },
//     { id: 'R4', name: 'room4' },
// ];
//   var stompClient = null;
//   var socket = new SockJS('/chatroom');
//             stompClient = Stomp.over(socket);
//             console.log("Connected!!");
//             // userName = document.getElementById('from').value;
//             // stompClient.connect({user:userName}, function(frame) {
//                 // setConnected(true);
                

//                 // stompClient.subscribe('/topic/'+ chatRoom.name, function(messageOutput) {
//                 //     showMessageOutput(JSON.parse(messageOutput.body));
//                 // });

//                 // // 私人
//                 // stompClient.subscribe('/user/subscribe', function(messageOutput) {
//                 //     showMessageOutput(JSON.parse(messageOutput.body));
//                 // });
//             // });
  
//   const handleSearch = (event) => {
//     const searchTerm = event.target.value;
//     setSearchTerm(searchTerm);

//     const filteredRooms = chatRooms.filter((room) =>
//       room.name.toLowerCase().includes(searchTerm.toLowerCase())
//     );
//     setFilteredChatRooms(filteredRooms);
//   };

//   const linkStyle = {
//     color: 'black', 
//     textDecoration: 'none' // 去除底線
//   };

//   return (
//     <div className='ChatRoomList'>
//       <input
//         type="text"
//         placeholder="Search chat rooms"
//         value={searchTerm}
//         onChange={handleSearch}
//       />
//       <ul>
//       {searchTerm
//           ? filteredChatRooms.map((chatRoom) => (
//             <Link to={`/chatroom/${chatRoom.id}`} style={linkStyle}  key={chatRoom.id}>
//               <li>
//                 <span className="link-text">{chatRoom.name}</span>
//               </li>
//             </Link>
//           ))
//           : chatRooms.map((chatRoom) => (
//             <Link to={`/chatroom/${chatRoom.id}`} style={linkStyle}  key={chatRoom.id}>
//               <li>
//                 <span className="link-text">{chatRoom.name}</span>
//               </li>
//             </Link>
//           ))}
//       </ul>
//     </div>
//   );
// };

// export default ChatRoomList;

import './ChatRoomList.css';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

const ChatRoomList = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredChatRooms, setFilteredChatRooms] = useState([]);
  const [connected, setConnected] = useState(false);
  const [stompClient, setStompClient] = useState(null);

  const chatRooms = [
    { id: 'R1', name: 'room1' },
    { id: 'R2', name: 'room2' },
    { id: 'R3', name: '徵室友' },
    { id: 'R4', name: 'room4' },
  ];

  useEffect(() => {
    const socket = new SockJS('/chatroom');
    const client = Stomp.over(socket);

    setStompClient(client);

    return () => {
      if (client) {
        client.disconnect();
      }
    };
  }, []);

  useEffect(() => {
    if (stompClient && connected) {
      const subscription = stompClient.subscribe('/user/subscribe', function(messageOutput) {
        showMessageOutput(JSON.parse(messageOutput.body));
      });

      return () => {
        if (subscription) {
          subscription.unsubscribe();
        }
      };
    }
  }, [stompClient, connected]);

  const handleSearch = (event) => {
    const searchTerm = event.target.value;
    setSearchTerm(searchTerm);

    const filteredRooms = chatRooms.filter((room) =>
      room.name.toLowerCase().includes(searchTerm.toLowerCase())
    );
    setFilteredChatRooms(filteredRooms);
  };

  const linkStyle = {
    color: 'black',
    textDecoration: 'none', // Remove underline
  };

  const showMessageOutput = (message) => {
    // Handle incoming message
    console.log('Received message:', message);
  };

  const connectWebSocket = () => {
    if (stompClient) {
      const userName = document.getElementById('from').value;
      stompClient.connect({ user: userName }, function(frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
      });
    }
  };

  return (
    <div className="ChatRoomList">
      <input
        type="text"
        placeholder="Search chat rooms"
        value={searchTerm}
        onChange={handleSearch}
      />
      <ul>
        {searchTerm
          ? filteredChatRooms.map((chatRoom) => (
              <Link to={`/chatroom/${chatRoom.id}`} style={linkStyle} key={chatRoom.id} onClick={connectWebSocket}>
                <li>
                  <span className="link-text">{chatRoom.name}</span>
                </li>
              </Link>
            ))
          : chatRooms.map((chatRoom) => (
              <Link to={`/chatroom/${chatRoom.id}`} style={linkStyle} key={chatRoom.id} onClick={connectWebSocket}>
                <li>
                  <span className="link-text">{chatRoom.name}</span>
                </li>
              </Link>
            ))}
      </ul>
    </div>
  );
};

export default ChatRoomList;
