import './ChatRoomList.css';
import { Link } from 'react-router-dom';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import {useEffect,useState} from "react";
import back from './img/back.png';
import {Back}  from './components/Style.js';

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
  const updatedChatRoomId = localStorage.getItem('nowRoom');
  const updatedChatRoomName = localStorage.getItem('nowRoomName');

  // 從 localStorage 讀取新的聊天室資訊，但僅在頁面第一次加載時執行
    useEffect(() => {
      fetchData();
      if (updatedChatRoomId && updatedChatRoomName) {
        const newChatRoom = {
          id: updatedChatRoomId,
          name: updatedChatRoomName
        };

        const isDuplicateRoom = chatRooms.some(room => room.id === updatedChatRoomId);

        if (!isDuplicateRoom) {
          const updatedChatRooms = [...chatRooms, newChatRoom];
          setChatRooms(updatedChatRooms);
        }
      }
    }, []); // 空的依賴數組表示只在第一次加載時執行

   function fetchData() {
       // 执行您的fetch请求
       fetch('/system/namecast')
         .then(response => response.text()) // 解析 text 回應
         .then(data => {
           const userNameTemp = data; // 此處 data 將是 "White"
           setUserName(userNameTemp);
           localStorage.setItem('userName', userNameTemp);
           console.log(userNameTemp);
         })
         .catch(error => {
           console.error('Fetch error:', error);
         });

   }
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

  const handleDeleteRoom = (event, roomId) => {
    event.stopPropagation(); // 阻止事件冒泡
    const updatedChatRooms = chatRooms.filter((room) => room.id !== roomId);
    setChatRooms(updatedChatRooms);
    // 如果要刪除的是新建的聊天室，則從列表中移除它
    /*  if (roomId === updatedChatRoomId) {
        const updatedChatRooms = chatRooms.filter((room) => room.id !== roomId);
        setChatRooms(updatedChatRooms);
      } else {
        // 否則只是將它隱藏
        const updatedChatRooms = chatRooms.map((room) => {
          if (room.id === roomId) {
            return { ...room, hidden: true };
          }
          return room;
        });
        setChatRooms(updatedChatRooms);
      }*/
  };

return (
  <div className="ChatRoomList">
    <Link to='/homePage'>
        <Back src={back} alt="回上一頁" />
    </Link>
    <div className='div1'>
    <input
      type="text"
      placeholder="Search chat rooms"
      value={searchTerm}
      onChange={handleSearch}
    />
    <ul>
      {searchTerm
        ? filteredChatRooms.map((chatRoom) => (
            !chatRoom.hidden && (
              <div key={chatRoom.id}>
                <Link to={`/chatroom/${chatRoom.id}`} style={linkStyle}
                      key={chatRoom.id} onClick={() => {
                        localStorage.setItem('nowRoom', chatRoom.id);
                        // connectWebSocket();
                      }}>
                  <li>
                    <span className="link-text">{chatRoom.name}</span>
                  </li>
                </Link>
                <button onClick={(event) => handleDeleteRoom(event, chatRoom.id)}
                        style={{ marginLeft: '320px', border: 'none', backgroundColor:'#FF7575', borderRadius:'10px'}}>Delete</button>
                <hr style={{ margin: '8px 0' }} />
              </div>
            )
          ))
        : chatRooms.map((chatRoom) => (
            !chatRoom.hidden && (
              <div key={chatRoom.id}>
                <Link to={`/chatroom/${chatRoom.id}`} style={linkStyle}
                    key={chatRoom.id} onClick={() => {
                      localStorage.setItem('nowRoom', chatRoom.id);
                      localStorage.setItem('nowRoomName', chatRoom.name);
                      //localStorage.setItem('userName', 'White');
                      // connectWebSocket();
                    }}>
                  <li>
                    <span className="link-text">{chatRoom.name}</span>
                  </li>
                </Link>
                <button onClick={(event) => handleDeleteRoom(event, chatRoom.id)}
                        style={{ marginLeft: '320px', border: 'none', backgroundColor:'#FF7575', borderRadius:'10px'}}>Delete</button>
                <hr style={{ margin: '8px 0' }} />
              </div>
            )
          ))}
    </ul>
    </div>
  </div>
);

}

export default ChatRoomList;
