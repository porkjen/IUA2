//import './changeClass.css';
import React from 'react';
import dog from './img/dog.png';
import {ArticleDetailPage, ArticleDetailPosition, ArticleDetailAuthor, ArticleDetailTitle, ArticleDetailPostDate, ArticleDetailText, ArticleDetailSavedBtn, ArticleDetailComment, ArticleDetailPostCommentPosition, ArticleDetailCommentImg, ArticleDetailPostComment}  from './components/ArticleDetailStyle.js';
import{Page, Pagebg, CommentList, CommentText, CommentContainer, CommentAuthor, CommentBody} from './components/CommentStyle.js';
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
                <ArticleDetailSavedBtn>收藏</ArticleDetailSavedBtn>
                <hr></hr>
                <ArticleDetailComment>
                    <Commentinfo author={"evelyn"} text={"已私訊"}></Commentinfo>
                    <Commentinfo author={"vvvvvv"} text={"想問"}></Commentinfo>
                    <Commentinfo author={"v123"} text={"會潮濕嗎"}></Commentinfo>
                    <Commentinfo author={"v"} text={"好餓"}></Commentinfo>
                </ArticleDetailComment>
            </ArticleDetailPosition>
            <ArticleDetailCommentImg src={dog}/>
            <ArticleDetailPostComment></ArticleDetailPostComment>
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
