import './ChatRoomList.css';
import React from 'react';
import { useEffect, useState, useRef,  } from "react";

const ChatRoomList = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredChatRooms, setFilteredChatRooms] = useState([]);

  const chatRooms = [
    { id: 1, name: 'Room 1' },
    { id: 2, name: 'Room 2' },
    { id: 3, name: '徵室友' },
    { id: 4, name: 'Chat Room 4' }
  ];

  const handleSearch = (event) => {
    const searchTerm = event.target.value;
    setSearchTerm(searchTerm);

    const filteredRooms = chatRooms.filter((room) =>
      room.name.toLowerCase().includes(searchTerm.toLowerCase())
    );
    setFilteredChatRooms(filteredRooms);
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
              <li key={chatRoom.id}>{chatRoom.name}</li>
            ))
          : chatRooms.map((chatRoom) => (
              <li key={chatRoom.id}>{chatRoom.name}</li>
            ))}
      </ul>
    </div>
  );
};

export default ChatRoomList;






