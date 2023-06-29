import './favorite.css';
import React from 'react';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';
import bookmark from './img/bookmark.png';
import {Page, Pagebg, Title, PostArticleBtn, ArticleList, ArticleText, ArticleContainer, ArticleAuthor, ArticleBody}  from './components/ArticleStyle.js';



const Favorite=()=>{
    function Savedinfo({ author, title }) {
        return (
          <ArticleContainer>
            <ArticleText>
                <ArticleAuthor>{author}</ArticleAuthor>
                <ArticleBody>{title}</ArticleBody>
            </ArticleText>
          </ArticleContainer>
        );
      }

    function Favorite(){
        return(
            <div className='Favorite'>
                <div id='div1'>
                    <img src={bookmark} alt='收藏' className='favorite_icon'></img>
                    <h1>我的收藏</h1>
                </div>
                <ArticleList>
                    <Savedinfo author={"aa"} title={"天一香"}></Savedinfo>
                    <Savedinfo author={"bb"} title={"徵室友"}></Savedinfo>
                    <Savedinfo author={"cc"} title={"我要涼課"}>hiiiii</Savedinfo>
                </ArticleList>
            </div>
        );
    }

    return (    
        <Routes>
            <Route path="/" element={<Favorite />}/>
        </Routes>
    
    );
}

export default Favorite;