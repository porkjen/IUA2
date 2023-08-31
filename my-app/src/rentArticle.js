//import './changeClass.css';
import React from 'react';
import dog from './img/dog.png';
import {ArticleDetailPage, ArticleDetailPosition, ArticleDetailAuthor, ArticleDetailAuthorArea, ArticleDetailAuthorImg, 
  ArticleDetailTitle, ArticleDetailPostDate, ArticleDetailText, ButtonContainer, ArticleDetailNormalBtn, ArticleDetailSavedBtn, 
  ArticleDetailAlreadySavedBtn, ArticleDetailContactdBtn, ArticleDetailComment, ArticleDetailPostCommentPosition, ArticleDetailCommentImg, ArticleDetailPostComment, ArticleDetailPostBtn}  from './components/ArticleDetailStyle.js';
import{Page, Pagebg, CommentList, CommentText, CommentContainer, CommentAuthor, CommentBody, CommentTimeRating, CommentRating} from './components/CommentStyle.js';
import { Routes ,Route,useLocation,useNavigate } from 'react-router-dom';
import {useEffect,useState} from "react";
import { Link } from 'react-router-dom';

const RentArticle=()=> {

    let navigate = useNavigate();
    const location = useLocation();
    const [data, setData] = useState(null);
    const [isCreator, setIsCreator] = useState(false);
    const [isRentSaved, setIsRentSaved] = useState(false);
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
                <ArticleDetailAuthorArea>
                <ArticleDetailAuthorImg src={dog}></ArticleDetailAuthorImg>
                <ArticleDetailAuthor>{author}</ArticleDetailAuthor>
              </ArticleDetailAuthorArea>
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
            studentID:  '00957025',
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
                .then(data => {
                  setData(data);
                  if(data.studentID==studentID){
                    console.log("same");
                    setIsCreator(true);
                  }
                  else{
                    setIsCreator(false);
                    if(data.saved[0]=='true'){
                      setIsRentSaved(true);
                    }
                    else{
                      setIsRentSaved(false);
                    }
                  }
                })
                .catch(error => {
                  console.error('Error:', error);
                });
            }
          }, [data]); // 添加依賴項data

          const handleAddRentSavedSubmit = (e) => {
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
                              .then(response => response.status)
                              .then(data => {
                                console.log(data);
                              })
                              .catch(error => {
                                console.error(error);
                              });
                              setIsRentSaved(true);
                           //Form submission happens here
          }

          const handleRemovedRentSavedSubmit = (e) => {
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
                              setIsRentSaved(false);
                           //Form submission happens here
          }

          const handleRemovedRentPostSubmit = (e) => {
            e.preventDefault();
            //const student_id = loginUser();
            const savedFormData = {
                            studentID: "00957025",
                            postId:postId,
                          };
                          fetch(`/rent_post_delete?studentID=${studentID}&postId=${postId}`, {
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
                              navigate("/rent", {
                                state: {
                                  studentID:"00957025",
                                  fromSearch:false},});
                           //Form submission happens here
          }

          const handleModifyRentPostSubmit = (e) => {
            e.preventDefault();
            //const student_id = loginUser();
                              navigate("/modifyPost", {
                                state: {
                                  studentID:"00957025",
                                  postId:postId,
                                  fromSearch:false,
                                  ModifyType:"rent",
                                   },});
                           //Form submission happens here
          }
    
    
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
                {isCreator && (<ButtonContainer>
                  <ArticleDetailNormalBtn onClick={handleModifyRentPostSubmit}>修改貼文</ArticleDetailNormalBtn>
                  <ArticleDetailNormalBtn onClick={handleRemovedRentPostSubmit}>刪除貼文</ArticleDetailNormalBtn>
                  </ButtonContainer>)}
                {!isCreator && (
                  <ButtonContainer>
                    <Link to={`/chatroom/${postId}`} onClick={() => {
                                                localStorage.setItem('nowRoom', postId);
                                                localStorage.setItem('nowRoomName', data.title)
                                                localStorage.setItem('userName', 'White')}}>
                      <ArticleDetailContactdBtn>聯絡</ArticleDetailContactdBtn>
                    </Link>
                    {isRentSaved ? (
                      <ArticleDetailAlreadySavedBtn onClick={handleRemovedRentSavedSubmit}>
                        已收藏
                      </ArticleDetailAlreadySavedBtn>
                    ) : (
                      <ArticleDetailSavedBtn onClick={handleAddRentSavedSubmit}>
                        收藏
                      </ArticleDetailSavedBtn>
                    )}
                  </ButtonContainer>
                )}
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

//${postId}
