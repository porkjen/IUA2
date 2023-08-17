import './ChatRoomList.css';
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

function ChatRoomList() {
  const [userName, setUserName] = useState('');
  const [connected, setConnected] = useState(false);
  const [chatRooms, setChatRooms] = useState([
    { id: 'R1', name: '缺體育' },
    { id: 'R2', name: '山海觀山缺一' },
    { id: 'R3', name: '徵室友' },
    { id: 'R4', name: '403、404換課' }
  ]);
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredChatRooms, setFilteredChatRooms] = useState(chatRooms);
/*
  const connectWebSocket = () => {
    const socket = new SockJS('/chatroom');
    const stompClient = Stomp.over(socket);
    stompClient.connect({ user: userName }, (frame) => {
      setConnected(true);
      console.log('Connected: ' + frame);
      // 訂閱相關主題
      stompClient.subscribe('/topic/messages', (messageOutput) => {
        this.showMessageOutput(JSON.parse(messageOutput.body));// 處理訊息
      });
      // 私人
      stompClient.subscribe('/user/subscribe', (messageOutput) => {
        this.showMessageOutput(JSON.parse(messageOutput.body));// 處理訊息
      });
    });
  };
*/
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
              <Link to={`/chatroom/${chatRoom.id}`} style={linkStyle}
                    key={chatRoom.id} onClick={() => {
                            localStorage.setItem('nowRoom', chatRoom.id);
                           // connectWebSocket();
                            }}>
                <li>
                  <span className="link-text">{chatRoom.name}</span>
                </li>
              </Link>
            ))
          : chatRooms.map((chatRoom) => (
              <Link to={`/chatroom/${chatRoom.id}`} style={linkStyle}
                                  key={chatRoom.id} onClick={() => {
                                          localStorage.setItem('nowRoom', chatRoom.id);
                                          localStorage.setItem('nowRoomName', chatRoom.name)
                                          localStorage.setItem('userName', 'White')
                                          //connectWebSocket();
                                          }}>
                              <li>
                  <span className="link-text">{chatRoom.name}</span>
                </li>
              </Link>
            ))}
      </ul>
    </div>
  );
}

export default ChatRoomList;
