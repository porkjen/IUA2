import './emptyResult.css';
import React, { useEffect, useState } from 'react';
import back from './img/back.png';
import { Link } from 'react-router-dom';
import { Courseinfo } from './components/Style';

function EmptyResult() {
  const [CourseData, setCourseData] = useState([]);
  const [waiting, setWaiting] = useState(false);

  useEffect(() => {
    setWaiting(true);
    fetch('/recommend_course_emptyhall', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({})
    })
      .then(response => {
        if (response.ok) {
          return response.json();
        } else {
          throw new Error('獲取資料失敗');
        }
      })
      .then(data => {
        console.log("Got it!");
        console.log(data);
        setCourseData(data);
      })
      .catch(error => {
        console.error("獲取資料失敗", error);
      })
      .finally(() => {
        setWaiting(false);
      });
  }, []);

  return (
    <div className="EmptyResult">
      <div className='EmptyResult_bg'>
        <div>
          <Link to='/recommend'>
            <img src={back} alt="回上一頁" className="CourseRate_backicon" />
          </Link>
        </div>
        {waiting &&
          <div className='remainLoadingText'>
            <div class="remainPreloader">
              正在抓取資料
              <div class="remainDot1"></div>
              <div class="remainDot2"></div>
              <div class="remainDot3"></div>
            </div>
          </div>
        }
        {!waiting && (
          <div className="EmptyResult_title">
            <label className="titleText">根據空堂所推薦課程</label>
          </div>
        )}
        {!waiting && (
          <div className='EmptyResultLable'>
            <div className="EmptyResult">
            <div className="er_scrollableContainer">
              {CourseData && CourseData.map((item) => (
                <div >
                <Courseinfo>
                  {item.name}&emsp;{item.category}<br/>{item.field}
                  </Courseinfo>
                </div>
              ))}
            </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default EmptyResult;