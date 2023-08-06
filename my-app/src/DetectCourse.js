<<<<<<< HEAD
import './DetectCourse.css';
import React from 'react';
import logo from './img/IUAlogo.png';
import {Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleDCText, ArticleText, ArticlePostTimeRating, ArticleContainer, ArticleFoodContainer, ArticleAuthor, ArticlePostTime, ArticlePostRating, ArticleBody}  from './components/ArticleStyle.js';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';
import {useEffect,useState} from "react";

const DetectCourse=()=> {


    function DetectCourse_all({course_number,course_name}){
        return(
        <ArticleContainer>
            <ArticleText>
                <ArticleAuthor>{course_number}</ArticleAuthor>
                <ArticleBody>{course_name}</ArticleBody>
            </ArticleText>
        </ArticleContainer>
        );
    }


    function DetectCourse() {
        const [isCourseNum, setIsCourseNum] = useState("");
        const [isCourseName, setIsCourseName] = useState("");

        const handleNumChange = (event) => {
            setIsCourseNum(event.target.value);
          };

          const handleNameChange = (event) => {
            setIsCourseName(event.target.value);
          };

        const handleCourseDetectNumSubmit = (e) => {
            e.preventDefault();
            //const student_id = loginUser();
            const savedFormData = {
                            studentID: "00957025",
                            courseName:"",
                            courseNumber:isCourseNum,
                            semester:"111"
                          };
                          fetch(`/add_detect_course`, {
                                method: 'POST',
                                headers: {
                                  'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(savedFormData)
                              })
                              .then(response => response.status)
                              .catch(error => {
                                console.error(error);
                              });
                              
                           //Form submission happens here
          }

          const handleCourseDetectNameSubmit = (e) => {
            e.preventDefault();
            //const student_id = loginUser();
            const savedFormData = {
                            studentID: "00957025",
                            courseName:isCourseName,
                            courseNumber:"",
                            semester:"111"
                          };
                          fetch(`/add_detect_course`, {
                                method: 'POST',
                                headers: {
                                  'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(savedFormData)
                              })
                              .then(response => response.status)
                              .catch(error => {
                                console.error(error);
                              });
                              
                           //Form submission happens here
          }
    
    

      return (
        <div className="DetectCourse"> 
            <div className="DetectCourse_bg">
                <Title className='DetectCourse_title'>偵測課程</Title>
                <div className='DetectCourse_search_position'>
                    <input type='text' className='DetectCourse_search_number' value={isCourseNum} onChange={handleNumChange} placeholder="請輸入課號"></input>
                    <button className='DetectCourse_searchBtn' onClick={handleCourseDetectNumSubmit}>加入課程</button><br/>
                    <input type='text' className='DetectCourse_search_name' value={isCourseName} onChange={handleNameChange} placeholder="請輸入課名" ></input>
                    <button className='DetectCourse_searchBtn' onClick={handleCourseDetectNameSubmit} >加入課程</button>
                </div>
                <ArticleList>
                    <ArticleContainer>
                        <ArticleDCText>
                            <ArticleAuthor>&emsp;V19951230</ArticleAuthor>
                            <ArticleBody>軟體工程</ArticleBody>
                            <button className='DetectCourse_delete'>刪除</button>
                        </ArticleDCText>
                        <ArticleDCText>
                            <ArticleAuthor>&emsp;V19970901</ArticleAuthor>
                            <ArticleBody>物聯網技術與應用</ArticleBody>
                            <button className='DetectCourse_delete'>刪除</button>
                        </ArticleDCText>
                    </ArticleContainer>
                </ArticleList>
                
            </div>
        </div>
      );
    }

    return (

           
                <Routes>
                    <Route path="/" element={<DetectCourse />} />
                </Routes>
             
    );
}

export default DetectCourse;
=======
import './DetectCourse.css';
import React from 'react';
import logo from './img/IUAlogo.png';
import {Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleDCText, ArticleText, ArticlePostTimeRating, ArticleContainer, ArticleFoodContainer, ArticleAuthor, ArticlePostTime, ArticlePostRating, ArticleBody}  from './components/ArticleStyle.js';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';
import {useEffect,useState} from "react";

const DetectCourse=()=> {


    function DetectCourse_all({course_number,course_name}){
        return(
        <ArticleContainer>
            <ArticleText>
                <ArticleAuthor>{course_number}</ArticleAuthor>
                <ArticleBody>{course_name}</ArticleBody>
            </ArticleText>
        </ArticleContainer>
        );
    }


    function DetectCourse() {
        const [isCourseNum, setIsCourseNum] = useState("");
        const [isCourseName, setIsCourseName] = useState("");

        const handleNumChange = (event) => {
            setIsCourseNum(event.target.value);
          };

          const handleNameChange = (event) => {
            setIsCourseName(event.target.value);
          };

        const handleCourseDetectNumSubmit = (e) => {
            e.preventDefault();
            //const student_id = loginUser();
            const savedFormData = {
                            studentID: "00957025",
                            courseName:"",
                            courseNumber:isCourseNum,
                            semester:"111"
                          };
                          fetch(`/add_detect_course`, {
                                method: 'POST',
                                headers: {
                                  'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(savedFormData)
                              })
                              .then(response => response.status)
                              .catch(error => {
                                console.error(error);
                              });
                              
                           //Form submission happens here
          }

          const handleCourseDetectNameSubmit = (e) => {
            e.preventDefault();
            //const student_id = loginUser();
            const savedFormData = {
                            studentID: "00957025",
                            courseName:isCourseName,
                            courseNumber:"",
                            semester:"111"
                          };
                          fetch(`/add_detect_course`, {
                                method: 'POST',
                                headers: {
                                  'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(savedFormData)
                              })
                              .then(response => response.status)
                              .catch(error => {
                                console.error(error);
                              });
                              
                           //Form submission happens here
          }
    
    

      return (
        <div className="DetectCourse"> 
            <div className="DetectCourse_bg">
                <Title className='DetectCourse_title'>偵測課程</Title>
                <div className='DetectCourse_search_position'>
                    <input type='text' className='DetectCourse_search_number' value={isCourseNum} onChange={handleNumChange} placeholder="請輸入課號"></input>
                    <button className='DetectCourse_searchBtn' onClick={handleCourseDetectNumSubmit}>加入課程</button><br/>
                    <input type='text' className='DetectCourse_search_name' value={isCourseName} onChange={handleNameChange} placeholder="請輸入課名" ></input>
                    <button className='DetectCourse_searchBtn' onClick={handleCourseDetectNameSubmit} >加入課程</button>
                </div>
                <ArticleList>
                    <ArticleContainer>
                        <ArticleDCText>
                            <ArticleAuthor>&emsp;V19951230</ArticleAuthor>
                            <ArticleBody>軟體工程</ArticleBody>
                            <button className='DetectCourse_delete'>刪除</button>
                        </ArticleDCText>
                        <ArticleDCText>
                            <ArticleAuthor>&emsp;V19970901</ArticleAuthor>
                            <ArticleBody>物聯網技術與應用</ArticleBody>
                            <button className='DetectCourse_delete'>刪除</button>
                        </ArticleDCText>
                    </ArticleContainer>
                </ArticleList>
                
            </div>
        </div>
      );
    }

    return (

           
                <Routes>
                    <Route path="/" element={<DetectCourse />} />
                </Routes>
             
    );
}

export default DetectCourse;
>>>>>>> 6874f83394979ee2f1ff6b017bc09983dd45a104
