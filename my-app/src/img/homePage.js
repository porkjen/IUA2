import './homePage.css';
import React from 'react';

function HomePage(){
        return(
            <div className="allBtn">
                <div className="creditBtn">
                    <button className="creditButton">
                        <span className="text-credit">學分</span>
                    </button>
                </div>
                <div className="graduateBtn">
                    <button className="graduateButton">
                        <span className="text-graduate">升學</span>
                    </button>
                </div>
                <div className="othersBtn">
                    <button className="othersButton">
                        <span className="text-others">其他</span>
                    </button>
                </div>
                <div className="socialBtn">
                    <button className="socialButton">
                        <span className="text-social">社群</span>
                    </button>
                </div>
                <div className="chatRoomBtn">
                    <button className="chatRoomButton">
                        <span className="text-chatRoom">聊天室</span>
                    </button>
                </div>
                <div className="courseSelectBtn">
                    <button className="courseSelectButton">
                        <span className="text-courseSelect">選課</span>
                    </button>
                </div>
                
            
            </div>

        );
}
export default HomePage;