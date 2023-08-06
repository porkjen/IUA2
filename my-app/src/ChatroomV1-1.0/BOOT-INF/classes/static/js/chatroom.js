const chatRoomList = document.getElementById('chatRoomList');

// 假設這是從後端獲取的聊天室數據
const chatRooms = [
    { id: 'R1', name: 'room1' },
    { id: 'R2', name: 'room2' },
    { id: 'R3', name: 'room3' }
];

// 創建聊天室列表
function createChatRoomList() {
    chatRoomList.innerHTML = ''; // 清空列表容器

    chatRooms.forEach((chatRoom) => {
        const chatRoomElement = document.createElement('div');
        chatRoomElement.classList.add('chat-room');
        chatRoomElement.textContent = chatRoom.name;
        chatRoomElement.addEventListener('click', () => {
            console.log('進入聊天室:', chatRoom.name);
            localStorage.setItem('nowRoom', chatRoom.name);

            var socket = new SockJS('/chatroom');
            stompClient = Stomp.over(socket);

            userName = document.getElementById('from').value;
            stompClient.connect({user:userName}, function(frame) {
                setConnected(true);
                console.log('Connected: ' + frame);

                stompClient.subscribe('/topic/'+ chatRoom.name, function(messageOutput) {
                    showMessageOutput(JSON.parse(messageOutput.body));
                });

                // 私人
                stompClient.subscribe('/user/subscribe', function(messageOutput) {
                    showMessageOutput(JSON.parse(messageOutput.body));
                });
            });
            var localStorageData = localStorage.getItem('nowRoom');

            fetch('/localstorage', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(localStorageData)
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                })
                .catch(error => {
                    console.error('Error:', error);
                });

            var url='/'+ chatRoom.name;
            window.location.href = url;
        });
        chatRoomList.appendChild(chatRoomElement);
    });
}

function add() {
    var newRoom = {
        id: 'R' + (chatRooms.length + 1),
        name: 'room'+ (chatRooms.length + 1)
    };

    chatRooms.push(newRoom);
    createChatRoomList();
}

createChatRoomList();