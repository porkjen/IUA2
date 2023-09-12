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
        const [modalIsOpen, setModalIsOpen] = useState(false); // 控制彈出視窗的狀態
        const location = useLocation();
        const CResult = location.state?.CResult || []; // 獲取查詢結果
        console.log(CResult);
        const openModal = () => {
                setModalIsOpen(true);
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
                                    <CoreDetailBtn  onClick={() => openModal()}>詳細資訊</CoreDetailBtn>
                                    <Modal
                                        isOpen={modalIsOpen}
                                        onRequestClose={closeModal}
                                        contentLabel="課程詳細資訊"
                                    >
                                        <div>
                                            <h2>{item.name}</h2>
                                            <p>課程代號：{item.number}</p>
                                            <p>選課類別：{item.category}</p>
                                            <p>課程學分：{item.ccredit}</p>
                                            <p>授課教師：{item.teacher}</p>
                                            <p>開課年班：{item.grade}</p>
                                            <p>上課時間：{item.time}</p>
                                            <p>上課地點：{item.room}</p>
                                            <p>人數上限：{item.people}</p>
                                            <p>教學目標：{item.cobjective}</p>
                                            <p>先修科目：{item.cprecourse}</p>
                                            <p>教材內容：{item.coutline}</p>
                                            <p>教學方式：{item.ctmethod}</p>
                                            <p>參考書目：{item.creference}</p>
                                            <p>教學進度：{item.csyllabus}</p>
                                            <p>評分方式:{item.cevaluation}</p>
                                        </div>
                                        <button onClick={closeModal}>關閉</button>
                                    </Modal>
                                </Coreinfo>
                            ))}
                        </div>  
                    </div>
                    <div className="core_pic">
                        <div className="whitefox">
                            <img src={whitefox} alt="IUA" />
                        </div>  
                    </div>
                </div>
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