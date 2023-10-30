import './rent.css';
import React from 'react';
import IsModal from "./components/Modal";
import bee1 from './img/bee1.png';
import bee2 from './img/bee2.png';
import yolk from './img/yolk.PNG';
import cat from './img/SignIn4.PNG';
import star from './img/star.png';
import back from './img/back.png';
import {Back}  from './components/Style.js';
import {ArticleDetailPage, ArticleDetailPosition, ArticleDetailAuthor, ArticleDetailAuthorArea, ArticleDetailAuthorImg, ArticleDetailTitle, ArticleDetailPostDate, ArticleDetailText, ArticleDetailSavedBtn, ArticleDetailAlreadySavedBtn, ArticleDetailContactdBtn, ArticleDetailComment, ArticleDetailPostCommentPosition, ArticleDetailCommentImg, ArticleDetailPostComment, ArticleDetailPostBtn}  from './components/ArticleDetailStyle.js';
import {Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleText, ArticleContainer, ArticleRentContainer, ArticleAuthor, ArticlePostTime, ArticleBody, ChangeClassCategorySelect}  from './components/ArticleStyle.js';
import { Routes ,Route,Link ,useNavigate, useLocation} from 'react-router-dom';
import {useEffect,useState} from "react";
import { loginUser } from './cookie';
import { getAuthToken } from "./utils";

const Rent=()=> {
    
    const [data, setData] = useState(null);
    const [openModal, setOpenModal] = useState(false);
    const [rentSelect, setRentSelect] = useState(false);
    const [rentNotification, setRentNotification] = useState(false);
    const [isPost, setIsPost] = useState(false);
    let navigate = useNavigate();
    const location = useLocation();
    const { fromSearch, RSArea, RSGender, RSPeople, RSType, RSCar } = location.state;
    const token = getAuthToken();
    const userInfo = loginUser();

    function Articleinfo({ author, time, text, postID }) {

      const handleShowHouseSubmit = (e) => {
        e.preventDefault();
        //const student_id = loginUser();
        const formData = {
                        studentID: userInfo,
                        postId : postID,
                      };
          fetch('/rent_full_post', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(formData)})
                  .then(response => response.json())
                  .then(data => {
                        console.log(data);})
                  .catch(error => {
                        console.error(error);});
                       //Form submission happens here
              navigate("/rentArticle", {
                state: {
                  postId : postID,
                  fromRent:true,
                  fromSearch:fromSearch,
                  RSArea:RSArea,
                  RSGender:RSGender,
                  RSPeople:RSPeople,
                  RSType:RSType,
                  RSCar:RSCar},});
            
      }

        return (
          <ArticleContainer>
              <ArticleText onClick={handleShowHouseSubmit}>
              <ArticleDetailAuthorArea>
                <ArticleDetailAuthorImg src={cat}></ArticleDetailAuthorImg>
                <ArticleDetailAuthor>{author}</ArticleDetailAuthor>
              </ArticleDetailAuthorArea>
                  <ArticleBody>{text}</ArticleBody>
                  <ArticlePostTime>{time}</ArticlePostTime>
              </ArticleText>
          </ArticleContainer>
        );
    }

    function Rent_all(){

      const handleRentFunctionSelect  = event => {

        if(event.target.value==='selectArticle'){
          setOpenModal(true);
          setRentSelect(true);
          setRentNotification(false);
        }
        else if(event.target.value==='setNotification'){
          setOpenModal(true);
          setRentSelect(false);
          setRentNotification(true);
        }
        else if(event.target.value==='AllArticle'){
          fetch(`/rent_search?area=&gender=&people=&style=&car=`)
              .then(response => response.json())
              .then(data => {
                console.log(data);
                setData(data);
              })
              .catch(error => {
                console.error('Error:', error);
              });
        }
        else if(event.target.value==="myPost")
          navigate('/MyArticles');

        else if(event.target.value==="myNotification")
          navigate('/myNotification');
      };

      return(
        <Page>
            <Pagebg>
                  <Title>租屋板</Title>
                    <img src={bee1} className='rent_bee1Img'></img>
                    <img src={bee2} className='rent_bee2Img'></img>
                <Link to='/postArticle'>
                  <PostArticleBtn>我要發文</PostArticleBtn>
                </Link>
                <ChangeClassCategorySelect onChange={handleRentFunctionSelect}>
                  <option >功能選單</option>
                  <option value='AllArticle'>全部貼文</option>
                  <option value='selectArticle'>篩選貼文</option>
                  <option value='setNotification'>設置提醒</option>
                  <option value='myPost'>我的貼文</option>
                  <option value='myNotification'>我的提醒</option>
                </ChangeClassCategorySelect>
                  
                <ArticleList>
                    {data.map(item => (
                      <Articleinfo key={item.title} author={item.name} time={item.postTime} text={item.title} postID={item.postId} ></Articleinfo>
                    ))}
                </ArticleList>
            </Pagebg>
        </Page>
      );
    }

   


    function Rent() {
      
      useEffect(() => {
        if(fromSearch==false){
          if (!data) {
            fetch('/rent_load')
              .then(response => response.json())
              .then(data => {
                console.log(data);
                setData(data);
              })
              .catch(error => {
                console.error('Error:', error);
              });
          }
        }
        else{
          if (!data) {
            fetch(`/rent_search?area=${RSArea}&gender=${RSGender}&people=${RSPeople}&style=${RSType}&car=${RSCar}`)
              .then(response => response.json())
              .then(data => {
                console.log(data);
                setData(data);
              })
              .catch(error => {
                console.error('Error:', error);
              });
          }
        }
        
      }, [data]); // 添加依賴項data

      if (!data) {
        return (
          <div className='rentLoadingText'>
            <div class="rentPreloader">
                正在抓取資料
                <div class="rentDot1"></div>
                <div class="rentDot2"></div>
                <div class="rentDot3"></div>
            </div>
          </div>
        );
      }

      
      return (
        <Page>
          {!openModal && 
            <Link to='/choose'>
              <Back src={back} alt="回上一頁" />
          </Link>
          }
          {openModal && rentSelect && <IsModal closeModal={setOpenModal} type={"rent"}/>}
          {openModal && rentNotification && <IsModal closeModal={setOpenModal} type={"setRentNotification"}/>}
          {!openModal && < Rent_all/>}
        </Page>
      );
    }

    return (
           
                <Routes>
                    <Route path="/" element={<Rent />} />
                </Routes>
             
    );
}

export default Rent;
/*

useEffect(() => {
        fetch('/rent_load')
          .then(response => response.json())
          .then(data => setData(data))
          .catch(error => {
            console.error('Error:', error);
          });
      }, []);

      if (!data) {
        return <div>Loading...</div>;
      }

{data.map(item => (
        <Articleinfo author={item.name} text={item.title}></Articleinfo>
      ))}

*/
