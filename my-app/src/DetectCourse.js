import './DetectCourse.css';
import React, { useEffect, useState } from 'react';
import back from './img/back.png';
import { Back } from './components/Style.js';
import logo from './img/IUAlogo.png';
import { Page, Pagebg, Title, ArticleList, ArticleText, ArticleContainer } from './components/ArticleStyle.js';
import { PreText ,PreSearchBody, PreSearchList} from './components/Style.js';
import { BrowserRouter as Router, Link } from 'react-router-dom';
import { Routes, Route } from 'react-router-dom';
import { loginUser } from './cookie';
import { getAuthToken } from './utils';

const DetectCourse = () => {
  const userInfo = loginUser();
  const token = getAuthToken();
  const [startDetect, setStartDetect] = useState(false);
  

  function DetectCourse_all({ list }) {
    const handleCourseDetectDelSubmit = (classNum) => {
      console.log(classNum);
      fetch(`/delete_detect_course?studentID=${userInfo}&number=${classNum}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      })
        .then((response) => response.status)
        .catch((error) => {
          console.error(error);
        });
    };

    return (
      <PreSearchList>
                <ArticleContainer>
          {list.map((item, index) => (
            <PreSearchBody key={index}>
              <PreText>
                {item.number}&nbsp;<br />
                <button className='detectedDelBtn' onClick={() => handleCourseDetectDelSubmit(item.number)}>刪除</button>
              </PreText>
            </PreSearchBody>
          ))}
       </ArticleContainer>
            </PreSearchList>
    );
  }

  function DetectCourse() {
    const [isCourseNum, setIsCourseNum] = useState('');
    const [isCourseName, setIsCourseName] = useState('');
    const [data, setData] = useState([]);

    const handleNumChange = (event) => {
      setIsCourseNum(event.target.value);
    };

    const handleNameChange = (event) => {
      setIsCourseName(event.target.value);
    };

    const handleCourseDetectNumSubmit = (e) => {
      e.preventDefault();
      //setStartDetect(true);
      const savedFormData = {
        studentID: userInfo,
        courseName: '',
        courseNumber: isCourseNum,
      };
      fetch(`/add_detect_course`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(savedFormData),
      })
      .then((response) => {
        console.log(response.status);
        if(response.status===200)
          setStartDetect(true);
      })
        .catch((error) => {
          console.error(error);
        });
    };

    const handleCourseDetectNameSubmit = (e) => {
      e.preventDefault();
      const savedFormData = {
        studentID: userInfo,
        courseName: isCourseName,
        courseNumber: '',
      };
      fetch(`/add_detect_course`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(savedFormData),
      })
        .then((response) => {
          console.log(response.status);
          if(response.status===200)
            setStartDetect(true);
        })
        .catch((error) => {
          console.error(error);
        });
    };

    if(startDetect){
      const FormData = {
        studentID: userInfo,
      };
      fetch(`/detect_course`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(FormData),
      })
        .then((response) => response.status)
        .catch((error) => {
          console.error(error);
        });
        setStartDetect(false);
    }

    useEffect(() => {
      if (data) {
        fetch(`/load_detect_course?studentID=${userInfo}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        })
          .then((response) => response.json())
          .then((data) => {
            console.log(data);
            setData(data);
          })
          .catch((error) => {
            console.error('Error:', error);
          });
      }
    }, [userInfo]);

    return (
      <div className='DetectCourse'>
        <div className='DetectCourse_bg'>
          <Link to='/CourseSelection'>
            <Back src={back} alt='回上一頁' />
          </Link>
          <Title className='DetectCourse_title'>偵測課程</Title>
          <div className='DetectCourse_search_position'>
            <input type='text' className='DetectCourse_search_number' value={isCourseNum} onChange={handleNumChange} placeholder='請輸入課號'></input>
            <button className='DetectCourse_searchBtn' onClick={handleCourseDetectNumSubmit}>加入課程</button><br />
            <input type='text' className='DetectCourse_search_name' value={isCourseName} onChange={handleNameChange} placeholder='請輸入課名'></input>
            <button className='DetectCourse_searchBtn' onClick={handleCourseDetectNameSubmit}>加入課程</button>
          </div>

            {data.length > 0 && <DetectCourse_all list={data} />}

        </div>
      </div>
    );
  }

  return (
    <Routes>
      <Route path='/' element={<DetectCourse />} />
    </Routes>
  );
};

export default DetectCourse;
