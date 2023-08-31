import './food.css';
import React from 'react';
import dog from './img/dog.png';
import banana from './img/banana.png';
import Modal from "./components/Modal";
import logo from './img/IUAlogo.png';
import star from './img/star.png';
import student from './img/student.png';
import back from './img/back.png';
import {Back}  from './components/Style.js';
import{ArticleAuthorImg} from './components/ArticleStyle.js';
import {ArticleDetailPage, ArticleDetailPosition, ArticleDetailAuthorArea, ArticleDetailAuthorImg, ArticleDetailAuthor, 
  ArticleDetailStar, ArticleDetailTitle, ArticleDetailPostDate, ArticleDetailText, 
  ButtonContainer, ArticleDetailSavedBtn, ArticleDetailNormalBtn, ArticleDetailAlreadySavedBtn, ArticleDetailRatingdBtn, 
  ArticleDetailComment, ArticleDetailPostCommentPosition, ArticleDetailCommentImg, ArticleDetailPostComment, 
  ArticleDetailPostBtn, ArticleDetailContactdBtn, ArticleDetailCommentDeleteBtn,ArticleDetailReportdBtn, ArticleDetailCommentModifyBtn}  from './components/ArticleDetailStyle.js';
import{CommentText, CommentContainer, CommentAuthor, CommentBody, CommentTimeRating, CommentAuthorBtn, CommentAuthorImg} from './components/CommentStyle.js';
import { Routes ,Route,useLocation, useNavigate,Link } from 'react-router-dom';
import {useEffect,useState} from "react";
import { loginUser } from './cookie';
import { getAuthToken } from "./utils";

const FoodArticle=()=> {

    let navigate = useNavigate();
    const location = useLocation();
    const [data, setData] = useState(null);
    const [openModal, setOpenModal] = useState(false);
    const { postId, ArticleAS,fromFood,fromMyPost } = location.state;
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
    const [confirmDelete, setConfirmDelete] = useState(false);
    const userInfo = loginUser();
    const token = getAuthToken();

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
                        studentID: userInfo,
                      };
                      fetch('/food_review_delete', {
                            method: 'DELETE',
                            headers: {
                              'Content-Type': 'application/json',
                              'Authorization': `${token}`
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

      

      if(commentCreator===userInfo){
        setIsMeComment(text);
        setIsMeRating(commentRating)
        return (
          <CommentContainer>
            <CommentText>
            <CommentAuthorBtn>
                <CommentAuthorImg src={student}></CommentAuthorImg>
                <CommentAuthor>{author}</CommentAuthor>
                <ArticleDetailCommentModifyBtn onClick={() => setOpenModal(true)}>修改評分</ArticleDetailCommentModifyBtn>
                <ArticleDetailCommentDeleteBtn onClick={handleCommentDeleteSubmit}>刪除</ArticleDetailCommentDeleteBtn>
              </CommentAuthorBtn>
                <CommentBody>{text}</CommentBody>
                <CommentTimeRating><RatingFood rating={commentRating} commentStar={true}></RatingFood>{commentTime}</CommentTimeRating>
            </CommentText>
          </CommentContainer>
        );
      }
      else if(commentCreator==='IUA'){
        return (
          <CommentContainer>
            <CommentText>
            <CommentAuthorBtn>
                 <CommentAuthorImg src={logo}></CommentAuthorImg>
                <CommentAuthor>{author}</CommentAuthor>
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
            <CommentAuthorBtn>
                 <CommentAuthorImg src={student}></CommentAuthorImg>
                <CommentAuthor>{author}</CommentAuthor>
            </CommentAuthorBtn>
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
        if(rating<1){
          rating=1;
        }
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
                        studentID: userInfo,
                        p_review : FComment,
                        p_rate : 0,
                      };
                      fetch('/food_review_add', {
                            method: 'POST',
                            headers: {
                              'Content-Type': 'application/json',
                              'Authorization': `${token}`
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
                        studentID: userInfo,
                        p_review : FComment,
                        p_rate : isMeRating,
                      };
                      fetch('/food_review_modify', {
                            method: 'PUT',
                            headers: {
                              'Content-Type': 'application/json',
                              'Authorization': `${token}`
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
                        studentID: userInfo,
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
                        studentID: userInfo,
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


      const handleModifyFoodPostSubmit = (e) => {
        e.preventDefault();
        //const student_id = loginUser();
                          navigate("/modifyPost", {
                            state: {
                              studentID:userInfo,
                              postId:postId,
                              fromSearch:false,
                              ModifyType:"food",
                              ArticleAS:ArticleAS,
                               },});
                       //Form submission happens here
      }

      function ConfirmDelete(){

        const handleRemovedFoodPostSubmit = (e) => {
          e.preventDefault();
          //const student_id = loginUser();
          const savedFormData = {
                          studentID: userInfo,
                          postId:postId,
                        };
                        fetch(`/food_post_delete?studentID=${userInfo}&postId=${postId}`, {
                              method: 'DELETE',
                              headers: {
                                'Content-Type': 'application/json',
                                'Authorization': `${token}`
                              },
                              body: JSON.stringify(savedFormData)
                            })
                            .then(response => response.status)
                            .catch(error => {
                              console.error(error);
                            });
                            navigate("/food", {
                              state: {
                                studentID:userInfo,
                                fromSearch:false,
                                ArticleAS:ArticleAS,},});
                         //Form submission happens here
        }
  
        const handleCancelRemovedFoodPostSubmit = (e) => {
          e.preventDefault();
  
          setConfirmDelete(false);     
        }
        
        return(
                <div className='food_deleteModal'>
                  <label className='food_deleteText'>確定要刪除嗎!!</label>
                  <button className='food_modal_yes' onClick={handleRemovedFoodPostSubmit}>確定</button>
                  <button className='food_modal_no' onClick={handleCancelRemovedFoodPostSubmit}>取消</button>
                </div>
        );
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

      const handleRemovedFoodPostConfirmSubmit = (e) => {
        e.preventDefault();
        setConfirmDelete(true);

      }


      return (
        <ArticleDetailPage>
            <ArticleDetailPosition>
                <ArticleTitleinfo key={data.store} author={data.nickname} title={data.store} post_time={data.post_time}></ArticleTitleinfo>
                <hr></hr>
                <ArticleDetailInfo key={data.postId}   address={data.address} rating={data.rating} weekday_text={data.weekday_text} URL={data.url}  ></ArticleDetailInfo>
                <hr></hr>
                {confirmDelete && <ConfirmDelete/>}
                {!confirmDelete && isCreator && (<ButtonContainer>
                  <ArticleDetailNormalBtn onClick={handleModifyFoodPostSubmit}>修改貼文</ArticleDetailNormalBtn>
                    <ArticleDetailNormalBtn onClick={handleRemovedFoodPostConfirmSubmit}>刪除貼文</ArticleDetailNormalBtn>
                </ButtonContainer>)}
                {confirmDelete && isCreator && (<ButtonContainer><ArticleDetailNormalBtn onClick={handleModifyFoodPostSubmit} disabled>修改貼文</ArticleDetailNormalBtn>
                    <ArticleDetailNormalBtn onClick={handleRemovedFoodPostConfirmSubmit} disabled>刪除貼文</ArticleDetailNormalBtn>
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
                    if(item.p_studentID===userInfo){
                      setIsAlreadyComment(true);
                    }
                    if(item.p_review==="test"){
                      setIsAlreadyRating(true);
                    }
                    if (item.p_review) {
                      if(item.p_studentID===userInfo){
                        return (
                          <Commentinfo
                            key={item.p_name}
                            author={item.p_name}
                            text={item.p_review}
                            commentTime={item.p_time}
                            commentRating={item.p_rate}
                            commentCreator={item.p_studentID}
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
                              commentCreator={item.p_studentID}
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
                     <ArticleDetailCommentImg src={student}/>
                     <ArticleDetailPostComment type='text' name = 'FComment'  placeholder='如留言過再次留言會更新舊有留言' onChange={handleFCommentChange} value={FComment}></ArticleDetailPostComment>
                     <ArticleDetailPostBtn type='submit'>送出</ArticleDetailPostBtn>
                   </form>
                    ) : (
                      <form onSubmit={handleCommentSubmit}>
                    <ArticleDetailCommentImg src={student}/>
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
            studentID:  userInfo,
            postId : postId,
          };
          console.log(ArticleAS);

        useEffect(() => {
            if (!data) {
                fetch('/food_full_post', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(formData)})
                .then(response => response.json())
                .then(data => {
                  if(data.studentID==userInfo){
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
                fromSearch:false,
                ArticleAS:ArticleAS,
                },});
          }

          const handleBackToFavoriteSubmit = (e) => {
            e.preventDefault();
            navigate("/favorite");
          }

          const handleBackToMyPostSubmit = (e) => {
            e.preventDefault();
            navigate("/MyArticles")
          }

      return (
        <ArticleDetailPage>
            {!openModal && fromFood && !fromMyPost && <img src={back} className='food_back' alt="回上一頁" onClick={handleBackSubmit}/>}
            {!openModal && !fromFood && !fromMyPost && <img src={back} className='food_back' alt="回上一頁" onClick={handleBackToFavoriteSubmit}/>}
            {!openModal && !fromFood && fromMyPost && <img src={back} className='food_back' alt="回上一頁" onClick={handleBackToMyPostSubmit}/>}
            {openModal && <Modal closeModal={setOpenModal} type={"rating"} postId={postId} comment={isMeComment} alreadyComment={isAlreadyComment} ArticleAS={ArticleAS}/>}
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
