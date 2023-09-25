import './Must.css';
import React, { useEffect, useState } from 'react';
import back from './img/back.png';
import { BrowserRouter as Router, Link } from 'react-router-dom';
import { Routes, Route, useLocation } from 'react-router-dom';
import { Mustinfo, MustDetailBtn } from './components/Style';
import Modal from 'react-modal';

const Must = () => {
  function MustComponent() {
    const [modalIsOpen, setModalIsOpen] = useState(false); // 控制彈出視窗的狀態
    const [selectedCourse, setSelectedCourse] = useState(null); // 存儲選中的課程
    const [data, setData] = useState([]);
    const location = useLocation();
    const RCResult = location.state?.RCResult || []; // 獲取查詢結果

    const openModal = (courseNumber, courseMajor, cousrGrade) => {
      const queryParams = new URLSearchParams({
        number: courseNumber,
        major: courseMajor,
        grade: cousrGrade
      });

      const url = '/course_search_detail?' + queryParams.toString();

      const formData = {
        "number": courseNumber,
        "major": courseMajor,
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
          setData(data);
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
                    {item.cname}<MustDetailBtn  onClick={() => openModal(item.cnumber, item.cmajor, item.cgrade)}>詳細資訊</MustDetailBtn>{item.cnumber}&emsp;{item.cgrade}&emsp;{item.cteacher}&emsp;{item.ccredit}學分
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
        {data.map((item) => (
          <div>
            <h2>{item.cname}</h2>
            <p>課程代號：{item.cnumber}</p>
             <p>選課類別：{item.ccategory}</p>
             <p>課程學分：{item.ccredit}</p>
             <p>授課教師：{item.cteacher}</p>
             <p>開課年班：{item.cgrade}</p>
             <p>上課時間：{item.ctime}</p>
             <p>上課地點：{item.clocation}</p>
             <p>人數上限：{item.cpeople}</p>
             <p>教學目標：<br/>{item.cobjective}</p>
             <p>先修科目：<br/>{item.cprecourse}</p>
             <p>教材內容：<br/>{item.coutline}</p>
             <p>教學方式：<br/>{item.ctmethod}</p>
             <p>參考書目：<br/>{item.creference}</p>
             <p>教學進度：<br/>{item.csyllabus}</p>
             <p>評分方式：<br/>{item.cevaluation}</p>
          </div>
        ))}
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