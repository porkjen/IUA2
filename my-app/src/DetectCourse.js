import './DetectCourse.css';
import React from 'react';
import logo from './img/IUAlogo.png';
import {Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleDCText, ArticleText, ArticlePostTimeRating, ArticleContainer, ArticleFoodContainer, ArticleAuthor, ArticlePostTime, ArticlePostRating, ArticleBody}  from './components/ArticleStyle.js';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';

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
      return (
        <div className="DetectCourse"> 
            <div className="DetectCourse_bg">
                <Title className='DetectCourse_title'>偵測課程</Title>
                <div className='DetectCourse_search_position'>
                    <input type='text' className='DetectCourse_search' placeholder="請輸入課號或課名"></input>
                    <button className='DetectCourse_searchBtn'>加入課程</button>
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
