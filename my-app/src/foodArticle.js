//import './changeClass.css';
import React from 'react';
import dog from './img/dog.png';
import banana from './img/banana.png';
import Modal from "./components/Modal";
import logo from './img/IUAlogo.png';
import star from './img/star.png';
import back from './img/back.png';
import {Back}  from './components/Style.js';
import {ArticleDetailPage, ArticleDetailPosition, ArticleDetailAuthorArea, ArticleDetailAuthorImg, ArticleDetailAuthor, 
  ArticleDetailStar, ArticleDetailTitle, ArticleDetailPostDate, ArticleDetailText, 
  ButtonContainer, ArticleDetailSavedBtn, ArticleDetailNormalBtn, ArticleDetailAlreadySavedBtn, ArticleDetailRatingdBtn, 
  ArticleDetailComment, ArticleDetailPostCommentPosition, ArticleDetailCommentImg, ArticleDetailPostComment, 
  ArticleDetailPostBtn, ArticleDetailContactdBtn, ArticleDetailCommentDeleteBtn,ArticleDetailReportdBtn, ArticleDetailAlreadyReportBtn}  from './components/ArticleDetailStyle.js';
import{Page, Pagebg, CommentList, CommentText, CommentContainer, CommentAuthor, CommentBody, CommentTimeRating, CommentRating, CommentAuthorBtn} from './components/CommentStyle.js';
import { Routes ,Route,useLocation, useNavigate,Link } from 'react-router-dom';
import {useEffect,useState} from "react";

const FoodArticle=()=> {

    let navigate = useNavigate();
    const location = useLocation();
    const [data, setData] = useState(null);
    const [openModal, setOpenModal] = useState(false);
    const { studentID, postId } = location.state;
    const [isCreator, setIsCreator] = useState(false);
    const [isReviewCreator, setIsReviewCreator] = useState(false);
    const [isMeComment, setIsMeComment] = useState(false);
    const [isMeRating, setIsMeRating] = useState(false);
    const [isFoodSaved, setIsFoodSaved] = useState(false);
    const [isFoodReport, setIsFoodReport] = useState(false);
    const [isAlreadyComment, setIsAlreadyComment] = useState(false);
    const [isAlreadyRating, setIsAlreadyRating] = useState(false);
    const [responseStatus, setResponseStatus] = useState(null);
    const [isAlreadySaved, setIsAlreadySaved] = useState(false);

    function RatingFood({ rating, commentStar }) {
      const stars = [];
      for (let i = 1; i <= rating; i++) {
        stars.push(<img key={i} className="rating_star" src={star} alt="star" />);
      }
      if(commentStar)
        return <div>{stars}</div>;
      else
        return <ArticleDetailStar>{stars}</ArticleDetailStar>;
    }

    function Commentinfo({ author, text, commentTime, commentRating, commentCreator }) {

      const handleCommentDeleteSubmit = (e) => {
        e.preventDefault();
        //const student_id = loginUser();
        const formData = {
                        postId:postId,
                        studentID: "00957025",
                      };
                      fetch('/food_review_delete', {
                            method: 'DELETE',
                            headers: {
                              'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(formData)
                          })
                          .then(response => response.status)
                          .then(data => {
                            console.log(data);
                          })
                          .catch(error => {
                            console.error(error);
                          });
                          window.location.reload();
                       //Form submission happens here
      }

      if(commentCreator===true){
        setIsMeComment(text);
        setIsMeRating(commentRating)
        return (
          <CommentContainer>
            <CommentText>
            <CommentAuthorBtn>
                <CommentAuthor>{author}</CommentAuthor>
                
                <ArticleDetailCommentDeleteBtn onClick={handleCommentDeleteSubmit}>刪除</ArticleDetailCommentDeleteBtn>
              </CommentAuthorBtn>
                <CommentBody>{text}</CommentBody>
                <CommentTimeRating><RatingFood rating={commentRating} commentStar={true}></RatingFood>{commentTime}</CommentTimeRating>
            </CommentText>
          </CommentContainer>
        );
      }
      else{
        return (
          <CommentContainer>
            <CommentText>
                <CommentAuthor>{author}</CommentAuthor>
                <CommentBody>{text}</CommentBody>
                <CommentTimeRating><RatingFood rating={commentRating} commentStar={true}></RatingFood>{commentTime}</CommentTimeRating>
            </CommentText>
          </CommentContainer>
        );
      }
        
    }

    function ArticleTitleinfo({ author, title, post_time }) {
        return (
            <div>
              <ArticleDetailAuthorArea>
                <ArticleDetailAuthorImg src={logo}></ArticleDetailAuthorImg>
                <ArticleDetailAuthor>{author}</ArticleDetailAuthor>
              </ArticleDetailAuthorArea>
                <ArticleDetailTitle>{title}</ArticleDetailTitle>
                <ArticleDetailPostDate>{post_time}</ArticleDetailPostDate>
            </div>
        );
      }

      function ArticleDetailInfo({ address, rating, weekday_text, URL}) {
        return (
          <div>
            <div>地址: {address}</div>
            <div>
              營業時間: 
              {weekday_text.map((item, index) => (
                <div key={index}>&emsp;{item}</div>
              ))}
            </div>
            <div>評分: <RatingFood rating={rating} commentStar={false}></RatingFood></div>
            <div>連結: <a href={URL} target="_blank" style={{ fontSize: '10px' }}>{URL}</a></div>
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
                        p_rate : 0,
                      };
                      fetch('/food_review_add', {
                            method: 'POST',
                            headers: {
                              'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(formData)
                          })
                          .then(response =>{
                            if(response.status===400)
                              setIsAlreadyComment(true);
                            else
                              setIsAlreadyComment(false)
                          })
                          .then(data => {
                            console.log(data);
                          })
                          .catch(error => {
                            console.error(error);
                          });
                          window.location.reload();
                          
                       //Form submission happens here
      }

      const handleModifyCommentSubmit = (e) => {
        e.preventDefault();
        //const student_id = loginUser();
        const formData = {
                        postId:postId,
                        studentID: "00957025",
                        p_review : FComment,
                        p_rate : isMeRating,
                      };
                      fetch('/food_review_modify', {
                            method: 'PUT',
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
                          window.location.reload();
                          
                       //Form submission happens here
      }


      const handleAddFoodSavedSubmit = (e) => {
        e.preventDefault();
        //const student_id = loginUser();
        const savedFormData = {
                        studentID: "00957025",
                        postId:postId,
                      };
                      fetch('/favorites', {
                            method: 'POST',
                            headers: {
                              'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(savedFormData)
                          })
                          .then(response => {
                            console.log(response.status);
                            if(response.status==200){
                              console.log("收藏成功!");
                            }
                            else if(response.status==201){
                              setIsAlreadySaved(true);
                              console.log("已收藏過!");
                            }
                            else if(response.status==400){
                              console.log("發生錯誤!");
                            }
                          })
                          .then(data => {
                            console.log(data);
                          })
                          .catch(error => {
                            console.error(error);
                          });
                          setIsFoodSaved(true);
                       //Form submission happens here
      }

      const handleRemovedFoodSavedSubmit = (e) => {
        e.preventDefault();
        //const student_id = loginUser();
        const savedFormData = {
                        studentID: "00957025",
                        postId:postId,
                      };
                      fetch('/favorites_delete', {
                            method: 'DELETE',
                            headers: {
                              'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(savedFormData)
                          })
                          .then(response => response.status)
                          .catch(error => {
                            console.error(error);
                          });
                          setIsFoodSaved(false);
                       //Form submission happens here
      }

      const handleRemovedFoodPostSubmit = (e) => {
        e.preventDefault();
        //const student_id = loginUser();
        const savedFormData = {
                        studentID: "00957025",
                        postId:postId,
                      };
                      fetch(`/food_post_delete?studentID=${studentID}&postId=${postId}`, {
                            method: 'DELETE',
                            headers: {
                              'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(savedFormData)
                          })
                          .then(response => response.status)
                          .catch(error => {
                            console.error(error);
                          });
                          navigate("/food", {
                            state: {
                              studentID:"00957025",
                              fromSearch:false},});
                       //Form submission happens here
      }

      const handleModifyFoodPostSubmit = (e) => {
        e.preventDefault();
        //const student_id = loginUser();
                          navigate("/modifyPost", {
                            state: {
                              studentID:"00957025",
                              postId:postId,
                              fromSearch:false,
                              ModifyType:"food",
                               },});
                       //Form submission happens here
      }

      const handleReportSubmit = (e) => {
        e.preventDefault();
        //const student_id = loginUser();
        const formData = {
                        postId:postId,
                      };
                      fetch('/food_close_report', {
                            method: 'POST',
                            headers: {
                              'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(formData)
                          })
                          .then(response => response.status)
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
                <ArticleTitleinfo key={data.store} author={data.nickname} title={data.store} post_time={data.post_time}></ArticleTitleinfo>
                <hr></hr>
                <ArticleDetailInfo key={data.postId}   address={data.address} rating={data.rating} weekday_text={data.weekday_text} URL={data.url}  ></ArticleDetailInfo>
                <hr></hr>
                {isCreator && (<ButtonContainer><ArticleDetailNormalBtn onClick={handleModifyFoodPostSubmit}>修改貼文</ArticleDetailNormalBtn>
                    <ArticleDetailNormalBtn onClick={handleRemovedFoodPostSubmit}>刪除貼文</ArticleDetailNormalBtn>
                </ButtonContainer>)}
                {!isCreator && (
                  <ButtonContainer>
                    <ArticleDetailRatingdBtn onClick={() => setOpenModal(true)}>
                      評分
                    </ArticleDetailRatingdBtn>
                    
                    {isFoodSaved ? (
                      <ArticleDetailAlreadySavedBtn onClick={handleRemovedFoodSavedSubmit}>
                        已收藏
                      </ArticleDetailAlreadySavedBtn>
                    ) : (
                      <ArticleDetailSavedBtn onClick={handleAddFoodSavedSubmit}>
                        收藏
                      </ArticleDetailSavedBtn>
                    )}
                    <ArticleDetailNormalBtn onClick={handleReportSubmit}>回報歇業</ArticleDetailNormalBtn>
                  </ButtonContainer>
                )}
                <hr></hr>
                <ArticleDetailComment>
                {data.review.map(item => {
                    if(item.p_studentID===studentID){
                      setIsAlreadyComment(true);
                    }
                    if(item.p_review==="test"){
                      setIsAlreadyRating(true);
                    }
                    if (item.p_review) {
                      if(item.p_studentID===studentID){
                        return (
                          <Commentinfo
                            key={item.p_name}
                            author={item.p_name}
                            text={item.p_review}
                            commentTime={item.p_time}
                            commentRating={item.p_rate}
                            commentCreator={true}
                          />
                        );
                      }
                      else{
                        if(item.p_review!=='尚未發表評論'){
                          return (
                            <Commentinfo
                              key={item.p_name}
                              author={item.p_name}
                              text={item.p_review}
                              commentTime={item.p_time}
                              commentRating={item.p_rate}
                              commentCreator={false}
                            />
                          );
                        }
                      }
                      
                    }
                  })}
                </ArticleDetailComment>
            </ArticleDetailPosition>
            <ArticleDetailPostCommentPosition>
              {isAlreadyComment? (
                     <form onSubmit={handleModifyCommentSubmit}>
                     <ArticleDetailCommentImg src={banana}/>
                     <ArticleDetailPostComment type='text' name = 'FComment'  placeholder='如留言過再次留言會更新舊有留言' onChange={handleFCommentChange} value={FComment}></ArticleDetailPostComment>
                     <ArticleDetailPostBtn type='submit'>送出</ArticleDetailPostBtn>
                   </form>
                    ) : (
                      <form onSubmit={handleCommentSubmit}>
                    <ArticleDetailCommentImg src={dog}/>
                    <ArticleDetailPostComment type='text' name = 'FComment' onChange={handleFCommentChange} value={FComment}></ArticleDetailPostComment>
                    <ArticleDetailPostBtn type='submit'>送出</ArticleDetailPostBtn>
                  </form>
                    )}
                
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
                .then(data => {
                  if(data.studentID==studentID){
                    console.log("same");
                    setIsCreator(true);
                  }
                  else{
                    setIsCreator(false);
                    if(data.saved[0]=='true'){
                      console.log(data.saved[0]);
                      setIsFoodSaved(true);
                    }
                    else {
                      console.log(data.saved[0]);
                      setIsFoodSaved(false);
                    }
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



          const handleBackSubmit = (e) => {
            e.preventDefault();
            navigate("/food", {
              state: {
                fromSearch:false,},});
          }

      return (
        <ArticleDetailPage>
           <Back src={back} alt="回上一頁" onClick={handleBackSubmit}/>
            {openModal && <Modal closeModal={setOpenModal} type={"rating"} postId={postId} comment={isMeComment} alreadyComment={isAlreadyComment}/>}
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
