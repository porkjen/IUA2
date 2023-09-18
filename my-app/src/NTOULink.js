import './NTOULink.css';
import {Back}  from './components/Style.js';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';
import back from './img/back.png';

const NTOULink=()=> {

    function NTOULink() {
      return (
        <div className="NTOULink"> 
          <Link to='/HomePage'>
              <Back src={back} alt="回上一頁" />
          </Link>
            <div className='NTOUChoosePosition'>
            <a href="https://www.ntou.edu.tw/post">
                <button className='NTOUBtn'>海大最新消息</button>
            </a>
            <a href="http://traffic.ntou.edu.tw/ntous/">
                <button className='NTOUBtn' >海大交通資訊</button>
            </a>
            <a href="https://www.ntou.edu.tw/calendar">
                <button className='NTOUBtn'>海大行事曆</button>
            </a>
            <a href="https://www.ntou.edu.tw/calendar">
                <button className='NTOUBtn'>TronClass</button>
            </a>
            </div>
           
        </div>
      );
    }

    return (

           
                <Routes>
                    <Route path="/" element={<NTOULink />} />
                </Routes>
             
    );
}

export default NTOULink;
