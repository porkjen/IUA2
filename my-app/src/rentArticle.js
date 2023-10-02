//import './changeClass.css';
import React from 'react';
import dog from './img/dog.png';
import banana from './img/banana.png';
import back from './img/back.png';
import cat from './img/SignIn4.PNG';
import {Back}  from './components/Style.js';
import {ArticleDetailPage, ArticleDetailPosition, ArticleDetailAuthor, ArticleDetailAuthorArea, ArticleDetailAuthorImg, 
  ArticleDetailTitle, ArticleDetailPostDate, ButtonContainer, ArticleDetailNormalBtn, ArticleDetailSavedBtn, 
  ArticleDetailAlreadySavedBtn, ArticleDetailContactdBtn}  from './components/ArticleDetailStyle.js';
import{CommentText, CommentContainer, CommentAuthor, CommentBody} from './components/CommentStyle.js';
import { Routes ,Route,useLocation,useNavigate } from 'react-router-dom';
import {useEffect,useState} from "react";
import { loginUser } from './cookie';
import { getAuthToken } from "./utils";
import { Link } from 'react-router-dom';

const RentArticle=()=> {

    let navigate = useNavigate();
    const location = useLocation();
    const [data, setData] = useState(null);
    const [isCreator, setIsCreator] = useState(false);
    const [isRentSaved, setIsRentSaved] = useState(false);
    const [isRentDelete, setIsRentDelete] = useState(false);
    const {postId,fromRent,fromMyPost,fromSearch,RSArea, RSGender, RSPeople, RSType, RSCar  } = location.state;
    const userInfo = loginUser();
    const token = getAuthToken();
    
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
                <ArticleDetailAuthorImg src={cat}></ArticleDetailAuthorImg>
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
                <div>人數: {people}&nbsp;&#40;目前已確定人數:&#41;</div>
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
            studentID:  userInfo,
            postId : postId,
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
                  if(data.studentID==userInfo){
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
                              setIsRentSaved(false);
                           //Form submission happens here
          }


          const handleModifyRentPostSubmit = (e) => {
            e.preventDefault();
            //const student_id = loginUser();
                              navigate("/modifyPost", {
                                state: {
                                  studentID:userInfo,
                                  postId:postId,
                                  fromSearch:false,
                                  ModifyType:"rent",
                                   },});
                           //Form submission happens here
          }

          function ConfirmRentDelete(){

            const handleRemovedRentPostSubmit = (e) => {
              e.preventDefault();
              //const student_id = loginUser();
              const savedFormData = {
                              studentID: userInfo,
                              postId:postId,
                            };
                            fetch(`/rent_post_delete?studentID=${userInfo}&postId=${postId}`, {
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
                                navigate("/rent", {
                                  state: {
                                    fromSearch:false},});
                             //Form submission happens here
            }
      
            const handleCancelRemovedRentPostSubmit = (e) => {
              e.preventDefault();
      
              setIsRentDelete(false);
                      
            }
            
            return(
                    <div className='deleteModal'>
                      <label className='deleteText'>確定要刪除嗎!!</label>
                      <button className='modal_yes' onClick={handleRemovedRentPostSubmit}>確定</button>
                      <button className='modal_no' onClick={handleCancelRemovedRentPostSubmit}>取消</button>
                    </div>
            );
        }
    
    
          if (!data) {
            return <div>Loading...</div>;
          }

          const handleBackSubmit = (e) => {
            e.preventDefault();
            navigate("/rent", {
              state: {
                fromSearch:fromSearch,
                RSArea:RSArea,
                RSGender:RSGender,
                RSPeople:RSPeople,
                RSType:RSType,
                RSCar:RSCar},});
          }
          const handleBackToFavoriteSubmit = (e) => {
            e.preventDefault();
            navigate("/favorite");
          }

          const handleRemovedRentPostConfirmSubmit = (e) => {
            e.preventDefault();
            //const student_id = loginUser();
            setIsRentDelete(true);
          }

          const handleBackToMyPostSubmit = (e) => {
            e.preventDefault();
            navigate("/MyArticles")
          }

          const handleContactBtnClick = () => {
            const queryParams = new URLSearchParams({
              first: userInfo,
              second: data.studentID,
            });
          
            const url = '/pickRoomApi?' + queryParams.toString();
          
            const requestData = {
              first: userInfo,
              second: data.studentID,
            };
          
            fetch(url, {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
              },
              body: JSON.stringify(requestData),
            })
              .then((response) => response.json())
              .then((apidata) => {
                navigate(`/chatroom/${apidata.roomApi}`, { state: { roomApi: apidata.roomApi } });
                localStorage.setItem('userName', userInfo);
                localStorage.setItem('nowRoom', apidata.roomApi);
                localStorage.setItem('nowRoomName', data.title);
                localStorage.setItem('poster', data.studentID);
              })
              .catch((error) => {
                console.error('Error:', error);
              });
          };

      return (
        <ArticleDetailPage>
          {fromRent && !fromMyPost && <Back src={back} alt="回上一頁" onClick={handleBackSubmit}/>}
          {!fromRent && !fromMyPost && <Back src={back} alt="回上一頁" onClick={handleBackToFavoriteSubmit}/>}
          {!fromRent && fromMyPost && <Back src={back} alt="回上一頁" onClick={handleBackToMyPostSubmit}/>}
            <ArticleDetailPosition>
                <ArticleTitleinfo author={data.name} title={data.title} post_time={data.post_time}></ArticleTitleinfo>
                <hr></hr>
                <ArticleDetailInfo  address={data.address} area={data.area} car={data.car} floor={data.floor} gender={data.gender} money={data.money} people={data.people} power={data.power} water={data.water} style={data.style} rent_date={data.rent_date} note={data.note} >拜託跟我換課，我請你吃雞排</ArticleDetailInfo>
                <hr></hr>
                {isRentDelete && <ConfirmRentDelete/>}
                {isCreator && isRentDelete && (<ButtonContainer>
                  <ArticleDetailNormalBtn onClick={handleModifyRentPostSubmit} disabled>修改貼文</ArticleDetailNormalBtn>
                  <ArticleDetailNormalBtn onClick={handleRemovedRentPostConfirmSubmit} disabled>刪除貼文</ArticleDetailNormalBtn>
                  </ButtonContainer>)}
                {isCreator && !isRentDelete && (<ButtonContainer>
                  <ArticleDetailNormalBtn onClick={handleModifyRentPostSubmit}>修改貼文</ArticleDetailNormalBtn>
                  <ArticleDetailNormalBtn onClick={handleRemovedRentPostConfirmSubmit}>刪除貼文</ArticleDetailNormalBtn>
                  </ButtonContainer>)}
                {!isCreator && (
                  <ButtonContainer>
                      <ArticleDetailContactdBtn onClick={handleContactBtnClick}>聯絡</ArticleDetailContactdBtn>
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