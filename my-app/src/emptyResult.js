import './emptyResult.css';
import React, { useEffect, useState } from 'react';
import back from './img/back.png';
import { Link } from 'react-router-dom';
import { Courseinfo } from './components/Style';

function EmptyResult() {
  const [CourseData, setCourseData] = useState([]);
  const [PECourseData, setPECourseData] = useState([]);
  const [GCourseData, setGCourseData] = useState([]);
  const [isShown, setIsShown] = useState(true);
  const [isPEShown, setIsPEShown] = useState(false);
  const [isGeneralShown, setIsGeneralShown] = useState(false);
  const [selectedOption, setSelectedOption] = useState('');
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

  useEffect(() => {
    setWaiting(true);
    fetch('/recommend_course_emptyhall_PE', {
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
        setPECourseData(data);
      })
      .catch(error => {
        console.error("獲取資料失敗", error);
      })
      .finally(() => {
        setWaiting(false);
      });
  }, []);

  // useEffect(() => {
  //   setWaiting(true);
  //   fetch('/recommend_course_emptyhall_general', {
  //     method: 'POST',
  //     headers: { 'Content-Type': 'application/json' },
  //     body: JSON.stringify({})
  //   })
  //     .then(response => {
  //       if (response.ok) {
  //         return response.json();
  //       } else {
  //         throw new Error('獲取資料失敗');
  //       }
  //     })
  //     .then(data => {
  //       console.log("Got it!");
  //       console.log(data);
  //       setGCourseData(data);
  //     })
  //     .catch(error => {
  //       console.error("獲取資料失敗", error);
  //     })
  //     .finally(() => {
  //       setWaiting(false);
  //     });
  // }, []);

  const handleSelectChange = (event) => {
    const selectedValue = event.target.value;
    setSelectedOption(selectedValue);

    if (selectedValue === 'choose') {
      Handler();
    }

    else if (selectedValue === 'PE') {
      PEHandler();
    }
    else if (selectedValue === 'general') {
      GeneralHandler();
    }
    
  };

  const Handler = () => {
    setIsShown(true);
    setIsPEShown(false);
    setIsGeneralShown(false);
  };


  const PEHandler = () => {
    setIsShown(false);
    setIsPEShown(true);
    setIsGeneralShown(false);
  };

  const GeneralHandler = () => {
    setIsShown(false);
    setIsPEShown(false);
    setIsGeneralShown(true);
  };



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
          <div>
            <select className='selectType' onChange={handleSelectChange}> 
                <option value="choose">選修</option>
                <option value="general">通識</option>
                <option value="PE">體育</option>
            </select>
          </div>
        )}
        {!waiting && (
          <div className='EmptyResultLable'>
            <div className="EmptyResult">
            <div className="er_scrollableContainer">
              {isShown &&
                CourseData.map((item) => (
                  <div >
                  <Courseinfo>
                    課名：{item.name}<br />
                    領域：{item.field}<br />
                    類別：{item.category}<br />
                    上課時間：{item.timeList.join(', ')}
                    </Courseinfo>
                  </div>
                ))
              }
              {isPEShown &&
                PECourseData.map((item) => (
                  <div >
                  <Courseinfo>
                    課名：{item.name}<br />
                    老師：{item.teacher}<br />
                    類別：{item.category}<br />
                    上課時間：{item.timeList.join(', ')}
                    </Courseinfo>
                  </div>
                ))
              }
              {isGeneralShown &&
                GCourseData.map((item) => (
                  <div >
                  <Courseinfo>
                    課名：{item.name}<br />
                    老師：{item.teacher}<br />
                    類別：{item.category}<br />
                    上課時間：{item.timeList.join(', ')}
                    </Courseinfo>
                  </div>
                ))
              }
            </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default EmptyResult;