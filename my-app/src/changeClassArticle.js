//import './changeClass.css';
import React from 'react';
import dog from './img/dog.png';
import {ArticleDetailPage, ArticleDetailPosition, ArticleDetailAuthor, ArticleDetailAuthorArea, ArticleDetailAuthorImg, 
  ArticleDetailTitle, ArticleDetailPostDate, ArticleDetailText, ButtonContainer, ArticleDetailNormalBtn, ArticleDetailSavedBtn, 
  ArticleDetailAlreadySavedBtn, ArticleDetailContactdBtn, ArticleDetailComment, ArticleDetailPostCommentPosition, ArticleDetailCommentImg,
   ArticleDetailPostComment, ArticleDetailPostBtn,ArticleDetailReportBtn, ArticleDetailAlreadyReportBtn}  from './components/ArticleDetailStyle.js';
import{Page, Pagebg, CommentList, CommentText, CommentContainer, CommentAuthor, CommentBody} from './components/CommentStyle.js';
import { Routes ,Route,Link } from 'react-router-dom';
import {useState, useLocation, useEffect} from "react";

//課程詳細文章
const ChangeClassArticle=()=> {

    const location = useLocation();
    const { studentID, postId } = location.state;
    const [isCreator, setIsCreator] = useState(false);
    const [data, setData] = useState(null);

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

      function ChangeClassArticleTitleinfo({ author, title, post_time }) {
        return (
            <div>
                <ArticleDetailAuthorArea>
                <ArticleDetailAuthorImg src={dog}></ArticleDetailAuthorImg>
                <ArticleDetailAuthor>{author}</ArticleDetailAuthor>
              </ArticleDetailAuthorArea>
                <ArticleDetailTitle>{title}</ArticleDetailTitle>
                <ArticleDetailPostDate>{post_time}</ArticleDetailPostDate>
            </div>
        );
      }

      function ChangeClassArticleDetailInfo({ address, area, car, floor, gender, money, people, power, water, style, rent_date, note  }) {
        return (
            <div>
                <div>地址: {address}</div>
                <div>地區: {area}</div>
                <div>車位: {car}</div>
                <div>樓層: {floor}</div>
                <div>性別: {gender}</div>
                <div>租金: {money}</div>
                <div>人數: {people}</div>
                <div>電費: {power}</div>
                <div>水費: {water}</div>
                <div>房型: {style}</div>
                <div>起租時間: {rent_date}</div>
                <div>內文: {note}</div>
            </div>
        );
      }


    function ChangeClassArticle() {
      const formData = {
        //studentID:  studentID,
        postId : postId,
      };

    useEffect(() => {
        if (!data) {
            fetch('/course_full_post', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(formData)})
            .then(response => response.json())
            .then(data => {
              if(data.studentID==studentID){
                console.log("same");
                setIsCreator(true);
              }
              else{
                setIsCreator(false);
              }
              setData(data);
              console.log(data);
            })
            .catch(error => {
              console.error('Error:', error);
            });
        }
      }, [data]); // 添加依賴項data

      if (!data) {
        return <div>Loading...</div>;
      }

      return (
        <ArticleDetailPage>
            <ArticleDetailAuthor>00957017</ArticleDetailAuthor>
            <ArticleDetailTitle>求換課</ArticleDetailTitle>
            <hr></hr>
            <ArticleDetailText>拜託跟我換課，我請你吃雞排</ArticleDetailText>
            <hr></hr>
            <ArticleDetailContactdBtn>聯絡</ArticleDetailContactdBtn>
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
