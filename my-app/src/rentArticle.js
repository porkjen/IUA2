//import './changeClass.css';
import React from 'react';
import dog from './img/dog.png';
import {ArticleDetailPage, ArticleDetailPosition, ArticleDetailAuthor, ArticleDetailTitle, ArticleDetailPostDate, ArticleDetailText, ArticleDetailSavedBtn, ArticleDetailContactdBtn, ArticleDetailComment, ArticleDetailPostCommentPosition, ArticleDetailCommentImg, ArticleDetailPostComment, ArticleDetailPostBtn}  from './components/ArticleDetailStyle.js';
import{Page, Pagebg, CommentList, CommentText, CommentContainer, CommentAuthor, CommentBody, CommentTimeRating, CommentRating} from './components/CommentStyle.js';
import { Routes ,Route,useLocation } from 'react-router-dom';
import {useEffect,useState} from "react";

const RentArticle=()=> {

    const location = useLocation();
    const [data, setData] = useState(null);
    const { studentID, postId } = location.state;

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

    function ArticleTitleinfo({ author, title, post_time }) {
        return (
            <div>
                <ArticleDetailAuthor>{author}</ArticleDetailAuthor>
                <ArticleDetailTitle>{title}</ArticleDetailTitle>
                <ArticleDetailPostDate>{post_time}</ArticleDetailPostDate>
            </div>
        );
      }

      function ArticleDetailInfo({ address, area, car, floor, gender, money, people, power, water, style, rent_date, note  }) {
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


    function RentArticle() {

        const formData = {
            studentID:  studentID,
            postId : postId,
          };

        const [RComment, setRComment] = useState("");
        const handleRCommentChange = event => {
            setRComment(event.target.value);
        };

        const commentData = {

        };

        useEffect(() => {
            if (!data) {
                fetch('/rent_full_post', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(formData)})
                .then(response => response.json())
                .then(data => setData(data))
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
            <ArticleDetailPosition>
                <ArticleTitleinfo author={data.name} title={data.title} post_time={data.post_time}></ArticleTitleinfo>
                <hr></hr>
                <ArticleDetailInfo  address={data.address} area={data.area} car={data.car} floor={data.floor} gender={data.gender} money={data.money} people={data.people} power={data.power} water={data.water} style={data.style} rent_date={data.rent_date} note={data.note} >拜託跟我換課，我請你吃雞排</ArticleDetailInfo>
                <hr></hr>
                <ArticleDetailContactdBtn>聯絡</ArticleDetailContactdBtn>
                <ArticleDetailSavedBtn>收藏</ArticleDetailSavedBtn>
                <hr></hr>
            </ArticleDetailPosition>
        </ArticleDetailPage>
      );
    }

    return (
           
                <Routes>
                    <Route path="/" element={<RentArticle />} />
                </Routes>
             
    );
}

export default RentArticle;
