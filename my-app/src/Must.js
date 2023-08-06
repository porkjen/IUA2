import './Must.css';
import React from 'react';
import back from './img/back.png';
import { BrowserRouter as Router, Link } from 'react-router-dom';
import { Routes, Route, useLocation } from 'react-router-dom';
import { Mustinfo } from './components/Style';

const Must = () => {
  function MustComponent() {
    const location = useLocation();
    const RCResult = location.state?.RCResult || []; // 獲取查詢結果

    return (
      <div className="Must">
        <div className='must_bg'>
          <div>
            <Link to='/Search'>
              <img src={back} alt="回上一頁" className="must_backicon" />
            </Link>
          </div>
          <div className="must_title">
            <label className="titleText">課程結果</label>
          </div>
          <div className='mustLable'>
            <div className="scrollableContainer">
              {RCResult.map((item) => (
                <Mustinfo key={item.id}>
                  {item.cname}<br />{item.cnumber}&emsp;{item.cgrade}&emsp;{item.cteacher}&emsp;{item.ccredit}學分
                </Mustinfo>
              ))}
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <Routes>
      <Route path="/" element={<MustComponent />} />
    </Routes>
  );
}

export default Must;