import './Must.css';
import React, { useEffect, useState } from 'react';
import back from './img/back.png';
import { BrowserRouter as Router, Link } from 'react-router-dom';
import { Routes, Route, useLocation } from 'react-router-dom';
import { Mustinfo, DetailBtn } from './components/Style';
import Modal from 'react-modal'; 

const Must = () => {
  function MustComponent() {
    const [modalIsOpen, setModalIsOpen] = useState(false); // 控制彈出視窗的狀態
    const [selectedCourse, setSelectedCourse] = useState(null); // 存儲選中的課程
    const location = useLocation();
    const RCResult = location.state?.RCResult || []; // 獲取查詢結果

    const openModal = (courseNumber, courseMajor, cousrGrade) => {
      const queryParams = new URLSearchParams({
        number: courseNumber,
        department: courseMajor,
        grade: cousrGrade
      });

      const url = '/course_search_detail?' + queryParams.toString();

      const formData = {
        "number": courseNumber,
        "department": courseMajor,
        "grade": cousrGrade
      };
      fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData) 
      })
        .then(response => response.json())
        .then(data => {
          console.log(queryParams.toString());
          console.log(data);
          setSelectedCourse(data);
          setModalIsOpen(true);
        })
        .catch(error => {
          console.error(error);
        });
    };

    const closeModal = () => {
      setSelectedCourse(null);
      setModalIsOpen(false);
    };

    const linkStyle = {
      color: 'black',
      textDecoration: 'none',
    };

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
                    <DetailBtn onClick={() => openModal(item.cnumber, item.cmajor, item.cgrade)}>詳細資訊</DetailBtn>
                  </Mustinfo>
              ))}
            </div>
          </div>
        </div>
        <Modal
        isOpen={modalIsOpen}
        onRequestClose={closeModal}
        contentLabel="課程詳細資訊"
      >
        {selectedCourse && (
          <div>
            <h2>{selectedCourse.cname}</h2> 
            <p>課程代號: {selectedCourse.cnumber}</p>
            <p>授課教師: {selectedCourse.cteacher}</p>
            <p>評分方式:</p>
          </div>
        )}

        <button onClick={closeModal}>關閉</button>
      </Modal>
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