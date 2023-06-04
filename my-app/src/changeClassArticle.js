//import './changeClass.css';
import React from 'react';
import {ArticleDetailPage, ArticleDetailAuthor, ArticleDetailTitle, ArticleDetailText, ArticleDetailSavedBtn, ArticleDetailComment}  from './components/ArticleDetailStyle.js';
import{Page, Pagebg, CommentList, CommentText, CommentContainer, CommentAuthor, CommentBody} from './components/CommentStyle.js';
import { Routes ,Route,Link } from 'react-router-dom';
import {useState} from "react";

const ChangeClassArticle=()=> {
    function Commentinfo({ author, text }) {
        return (
          <CommentContainer>
            <CommentText>
                <CommentAuthor>{author}</CommentAuthor>
                <CommentBody>{text}</CommentBody>
            </CommentText>
          </CommentContainer>
        );
      }


    function ChangeClassArticle() {
      /*const [nickName_id, setNickName_id] = useState("");
      const handleChange = event => {
        setNickName_id(event.target.nickName_id);
      };
      const handleSubmit = (event) => {
        event.preventDefault();
        alert(`The nickName you entered was: ${nickName_id}`)
      }*/
      return (
        <ArticleDetailPage>
            <ArticleDetailAuthor>00957017</ArticleDetailAuthor>
            <ArticleDetailTitle>求換課</ArticleDetailTitle>
            <hr></hr>
            <ArticleDetailText>拜託跟我換課，我請你吃雞排</ArticleDetailText>
            <hr></hr>
            <ArticleDetailSavedBtn>收藏</ArticleDetailSavedBtn>
            <hr></hr>
            <ArticleDetailComment>
                <Commentinfo author={"evelyn"} text={"I love you"}></Commentinfo>
                <Commentinfo author={"vvvvvv"} text={"I love you"}></Commentinfo>
            </ArticleDetailComment>
            
        </ArticleDetailPage>
        
        
      );
    }

    return (
           
                <Routes>
                    <Route path="/" element={<ChangeClassArticle />} />
                </Routes>
             
    );
}

export default ChangeClassArticle;
