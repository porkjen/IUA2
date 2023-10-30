import './courseRate.css';
import React, { useEffect, useState } from 'react';
import back from './img/back.png';
import { Link } from 'react-router-dom';
import { Courseinfo } from './components/Style';
import styled from 'styled-components';
import { useNavigate, useLocation } from 'react-router-dom';

const CourseRating = styled.div`
  .radioContainer {
    display: flex;
    flex-direction: row;
  }

  .radioOption {
    margin-left: 30px;
  }
`;

function CourseRate() {
  const [CourseData, setCourseData] = useState([]);
  const [waiting, setWaiting] = useState(false);
  const [selectedValues, setSelectedValues] = useState({});
  const navigate = useNavigate();
  const location = useLocation(); // 獲取路由位置

  useEffect(() => {
    setWaiting(true);
    fetch('/recommend_course_rating', {
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
        console.log(data.info);
        const initialSelectedValues = {};
        data.info.forEach(item => {
          initialSelectedValues[item.name] = 3;
        });
        setSelectedValues(initialSelectedValues);
        setCourseData(data.info);
      })
      .catch(error => {
        console.error("獲取資料失敗", error);
      })
      .finally(() => {
        setWaiting(false);
      });
  }, []);

  const handleRadioChange = (courseId, value) => {
    setSelectedValues(prevValues => ({
      ...prevValues,
      [courseId]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    
    console.log(JSON.stringify(selectedValues));
    fetch('/recommend_course_rating_result', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(selectedValues)
    })
      .then(response => response.json())
      .then(data => {
        // 處理返回的數據
        console.log(data);
        navigate('/courseResult', { state: { cResult: data } });
      })
      .catch(error => {
        console.error(error);
      });
  };

  return (
    <div className="CourseRate">
      <div className='CourseRate_bg'>
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
          <div className="CourseRate_title">
            <label className="titleText">已修過課程喜好程度評分</label>
          </div>
        )}
        {!waiting && (
          <div className='CourseRateLable'>
            <div className="CourseRate">
            <div className="cr_scrollableContainer">
              {CourseData && CourseData.map((item) => (
                <div >
                <Courseinfo>
                  {item.name}
                  <CourseRating>
                    <div className="radioContainer">
                      {[1, 2, 3, 4, 5].map((rating) => (
                        <label className="radioOption" key={rating}>
                          <input
                            type="radio"
                            name={`rating_${item.name}`}
                            value={rating}
                            checked={selectedValues[item.name] === rating}
                            onChange={() => handleRadioChange(item.name, rating)}
                          />
                          {rating}
                        </label>
                      ))}
                    </div>
                  </CourseRating>
                  </Courseinfo>
                </div>
              ))}
            </div>
            </div>
          </div>
        )}
        {!waiting && (
        <div className="finishDiv">
          <button className="finish_btn" onClick={handleSubmit}>送出</button>
        </div>
        )}
      </div>
    </div>
  );
}

export default CourseRate;


/*
<div className="cr_scrollableContainer">
                        {CourseData && CourseData.map((item) => (
                            <Courseinfo key={item.id}>
                                {item.name}
                                <CourseRating>
                                    <div className="radioContainer">
                                        <label className="radioOption">
                                            <input type="radio" name={`rating_${item.id}_1`} value="1" />
                                            1
                                        </label>
                                        <label className="radioOption">
                                            <input type="radio" name={`rating_${item.id}_2`} value="2" />
                                            2
                                        </label>
                                        <label className="radioOption">
                                            <input type="radio" name={`rating_${item.id}_3`} value="3" />
                                            3
                                        </label>
                                        <label className="radioOption">
                                            <input type="radio" name={`rating_${item.id}_4`} value="4" />
                                            4
                                        </label>
                                        <label className="radioOption">
                                            <input type="radio" name={`rating_${item.id}_5`} value="5" />
                                            5
                                        </label>
                                    </div>
                                </CourseRating>
                            </Courseinfo>
                        ))}
*/