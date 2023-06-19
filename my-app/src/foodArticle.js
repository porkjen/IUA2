//import './changeClass.css';
import React from 'react';
import dog from './img/dog.png';
import Modal from "./components/Modal";
import {ArticleDetailPage, ArticleDetailPosition, ArticleDetailAuthor, ArticleDetailTitle, ArticleDetailPostDate, ArticleDetailText, ArticleDetailSavedBtn, ArticleDetailRatingdBtn, ArticleDetailComment, ArticleDetailPostCommentPosition, ArticleDetailCommentImg, ArticleDetailPostComment, ArticleDetailPostBtn}  from './components/ArticleDetailStyle.js';
import{Page, Pagebg, CommentList, CommentText, CommentContainer, CommentAuthor, CommentBody, CommentTimeRating, CommentRating} from './components/CommentStyle.js';
import { Routes ,Route,useLocation } from 'react-router-dom';
import {useEffect,useState} from "react";

const FoodArticle=()=> {

    const location = useLocation();
    const [data, setData] = useState(null);
    const [openModal, setOpenModal] = useState(false);
    const { studentID, postId } = location.state;

    function Commentinfo({ author, text, commentTime, commentRating }) {
        return (
          <CommentContainer>
            <CommentText>
                <CommentAuthor>{author}</CommentAuthor>
                <CommentBody>{text}</CommentBody>
                <CommentTimeRating>{commentRating + "顆星 " + commentTime}</CommentTimeRating>
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

      function ArticleDetailInfo({ address, rating, weekday_text, URL}) {
        return (
            <div>
                <div>地址: {address}</div>
                <div>營業時間: 
                    {weekday_text.map(item => (
                        <div>{item+"\n"}</div>))}</div>
                <div>評分: {rating}</div>
                <div>連結: {URL}</div>
            </div>
        );
      }

    function FoodDetailInfo(){
      const [FComment, setFComment] = useState("");
      const handleFCommentChange = event => {
          setFComment(event.target.value);
      };

      const handleCommentSubmit = (e) => {
        e.preventDefault();
        //const student_id = loginUser();
        const formData = {
                        postId:postId,
                        studentID: "00957025",
                        p_review : FComment,
                        p_rate : 4,
                      };
                      fetch('/food_review_add', {
                            method: 'POST',
                            headers: {
                              'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(formData)
                          })
                          .then(response => response.json())
                          .then(data => {
                            console.log(data);
                          })
                          .catch(error => {
                            console.error(error);
                          });
                          
                       //Form submission happens here
      }

      return (
        <ArticleDetailPage>
            <ArticleDetailPosition>
                <ArticleTitleinfo author={data.nickname} title={data.store} post_time={data.post_time}></ArticleTitleinfo>
                <hr></hr>
                <ArticleDetailInfo  address={data.address} rating={data.rating} weekday_text={data.weekday_text} URL={data.url}  ></ArticleDetailInfo>
                <hr></hr>
                <ArticleDetailRatingdBtn onClick={()=> setOpenModal(true)}>評分</ArticleDetailRatingdBtn>
                <ArticleDetailSavedBtn>收藏</ArticleDetailSavedBtn>
                <hr></hr>
                <ArticleDetailComment>
                    {data.review.map(item => (
                      <Commentinfo key={item.p_name} author={item.p_name} text={item.p_review} commentTime={item.p_time} commentRating={item.p_rate}></Commentinfo>
                    ))}
                </ArticleDetailComment>
            </ArticleDetailPosition>
            <ArticleDetailPostCommentPosition>
                <form onSubmit={handleCommentSubmit}>
                  <ArticleDetailCommentImg src={dog}/>
                  <ArticleDetailPostComment type='text' name = 'FComment' onChange={handleFCommentChange} value={FComment}></ArticleDetailPostComment>
                  <ArticleDetailPostBtn>送出</ArticleDetailPostBtn>
                </form>
            </ArticleDetailPostCommentPosition>
        </ArticleDetailPage>
      );
    }


    function FoodArticle() {

        const formData = {
            studentID:  studentID,
            postId : postId,
          };

        

        useEffect(() => {
            if (!data) {
                fetch('/food_full_post', {
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
            {openModal && <Modal closeModal={setOpenModal} type={"rating"} postId={postId}/>}
            {!openModal && < FoodDetailInfo/>}
        </ArticleDetailPage>
      );
    }

    return (
           
                <Routes>
                    <Route path="/" element={<FoodArticle />} />
                </Routes>
             
    );
}

export default FoodArticle;
