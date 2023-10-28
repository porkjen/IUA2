import './historyResult.css';
import React, { useEffect, useState } from 'react';
import back from './img/back.png';
import { Link } from 'react-router-dom';
import { Courseinfo } from './components/Style';

function HistoryResult() {
    const [CourseData, setCourseData] = useState([]);
    return (
        <div className="HistoryResult">
        <div className='HistoryResult_bg'>
            <div>
            <Link to='/history'>
                <img src={back} alt="回上一頁" className="HistoryResult_backicon" />
            </Link>
            </div>
            <div className="HistoryResult_title">
            <label className="titleText">歷史推薦結果</label>
            </div>
            <div className='HistoryResultLable'>
                <div className="HistoryResult">
                <div className="er_scrollableContainer">
                    {CourseData.map((item) => (
                    <div >
                    <Courseinfo>
                        課名：{item.name}<br />
                        領域：{item.field}<br />
                        類別：{item.category}<br />
                        上課時間：{item.timeList.join(', ')}
                    </Courseinfo>
                    </div>
                    ))} 
                </div>
                </div>
            </div>
        </div>
        </div>
    );
}

export default HistoryResult;