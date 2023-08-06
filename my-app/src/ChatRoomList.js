import './ChatRoomList.css';
import React from 'react';
import { useEffect, useState, useRef,  } from "react";
import { Link } from 'react-router-dom';

const ChatRoomList = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredChatRooms, setFilteredChatRooms] = useState([]);

  const chatRooms = [
    { id: 'R1', name: 'room1' },
    { id: 'R2', name: 'room2' },
    { id: 'R3', name: 'room3' }
];
  // var stompClient = null;
  // var userName = null;
  // var socket = new SockJS('/chatroom');
  //           stompClient = Stomp.over(socket);

  //           userName = document.getElementById('from').value;
  //           stompClient.connect({user:userName}, function(frame) {
  //               setConnected(true);
  //               console.log('Connected: ' + frame);

  //               stompClient.subscribe('/topic/'+ chatRoom.name, function(messageOutput) {
  //                   showMessageOutput(JSON.parse(messageOutput.body));
  //               });

  //               // 私人
  //               stompClient.subscribe('/user/subscribe', function(messageOutput) {
  //                   showMessageOutput(JSON.parse(messageOutput.body));
  //               });
  //           });
  
  const handleSearch = (event) => {
    const searchTerm = event.target.value;
    setSearchTerm(searchTerm);

    const filteredRooms = chatRooms.filter((room) =>
      room.name.toLowerCase().includes(searchTerm.toLowerCase())
    );
    setFilteredChatRooms(filteredRooms);
  };

  const linkStyle = {
    color: 'black', // 设置链接文本颜色为黑色
    textDecoration: 'none' // 去除下划线
  };

  return (
    <div className='ChatRoomList'>
      <input
        type="text"
        placeholder="Search chat rooms"
        value={searchTerm}
        onChange={handleSearch}
      />
      <ul>
      {searchTerm
          ? filteredChatRooms.map((chatRoom) => (
            <Link to={`/chatroom/${chatRoom.id}`} style={linkStyle}>
              <li key={chatRoom.id}>
                <span className="link-text">{chatRoom.name}</span>
              </li>
            </Link>
          ))
          : chatRooms.map((chatRoom) => (
            <Link to={`/chatroom/${chatRoom.id}`} style={linkStyle}>
              <li key={chatRoom.id}>
                <span className="link-text">{chatRoom.name}</span>
              </li>
            </Link>
          ))}
      </ul>
    </div>
  );
};

export default ChatRoomList;


