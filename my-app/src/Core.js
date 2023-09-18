import './Core.css';
import React, { useEffect, useState } from 'react';
import orangefox from './img/orangefox.PNG';
import whitefox from './img/whitefox.PNG';
import back from './img/back.png';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes, Route, useLocation } from 'react-router-dom';
import { Coreinfo, CoreHardwareField, CoreSoftwareField, CoreDetailBtn} from './components/Style';
import Modal from 'react-modal';

const Core=()=>{
    function Core() {
        const location = useLocation();
        const CResult = location.state?.CResult || []; // 獲取查詢結果
        const [modalIsOpen, setModalIsOpen] = useState(false); // 控制彈出視窗的狀態
        const [data, setData] = useState([]);
        console.log(CResult);
        const openModal = (courseNumber, cousrGrade) => {
            const queryParams = new URLSearchParams({
              number: courseNumber,
              grade: cousrGrade
            });
      
            const url = '/core_elective_detail?' + queryParams.toString();
      
            const formData = {
              "number": courseNumber,
              "grade": cousrGrade
            };
      
            fetch(url, {
              method: 'GET',
              headers: {
                'Content-Type': 'application/json'
              }
            })
              .then(response => response.json())
              .then(data => {
                console.log(queryParams.toString());
                console.log(data);
                setData(data);
                setModalIsOpen(true);
              })
              .catch(error => {
                console.error(error);
              });
          };
      
          const closeModal = () => {
            setModalIsOpen(false);
          };
        return (
            <div className="Core">    
                <div className='core_bg'>
                    <div>
                        <Link to='/coreSearch'>
                            <img src={back} alt="回上一頁" className="core_backicon"/>
                        </Link>
                    </div>
                    <div className="core_title">
                        <label className="titleText">核心選修</label>
                    </div>
                    <div className='coreLable'>
                        <div className="c_scrollableContainer">
                            {CResult.map((item) => (
                                <Coreinfo key={item.id}>
                                    {item.field.includes("計算機") &&  <CoreHardwareField>{item.field}</CoreHardwareField>}
                                    {item.field.includes("軟體") &&  <CoreSoftwareField>{item.field}</CoreSoftwareField>}
                                    {item.name}<br />{item.number}&emsp;{item.teacher}<br />學期: {item.semester}<br />上課時間: {item.time}
                                    <CoreDetailBtn  onClick={() => openModal(item.number, item.grade)}>詳細資訊</CoreDetailBtn>
                                </Coreinfo>
                            ))}
                        </div>  
                    </div>
                    {!modalIsOpen && <div className="core_pic">
                        <div className="whitefox">
                            <img src={whitefox} alt="IUA" />
                        </div>  
                    </div>}
                </div>
                <Modal
                    isOpen={modalIsOpen}
                    onRequestClose={closeModal}
                    contentLabel="課程詳細資訊"
                >
                    {data && (
                    <div>
                        <h2>{data.name}</h2>
                        <p>開課學期：{data.semester}</p>
                        <p>課程代號：{data.number}</p>
                        <p>選課類別：{data.category}</p>
                        <p>課程學分：{data.ccredit}</p>
                        <p>授課教師：{data.teacher}</p>
                        <p>開課年班：{data.grade}</p>
                        <p>上課時間：{data.time}</p>
                        <p>上課地點：{data.room}</p>
                        <p>人數上限：{data.people}</p>
                        <p>教學目標：<br/>{data.cobjective}</p>
                        <p>先修科目：<br/>{data.cprecourse}</p>
                        <p>教材內容：<br/>{data.coutline}</p>
                        <p>教學方式：<br/>{data.ctmethod}</p>
                        <p>參考書目：<br/>{data.creference}</p>
                        <p>教學進度：<br/>{data.csyllabus}</p>
                        <p>評分方式：<br/>{data.cevaluation}</p>
                    </div>
                    )}
                    <button onClick={closeModal}>關閉</button>
                </Modal>
            </div>
        );
      }
  
      return ( 
          <Routes>
              <Route path="/" element={<Core />} />
          </Routes>
       
    );
}

export default Core;