import './food.css';
import React from 'react';
import Modal from "./components/Modal";
import yolk from './img/yolk.PNG';
import star from './img/star.png';
import redBall from './img/redBall.PNG';
import logo from './img/IUAlogo.png';
import student from './img/student.png';
import back from './img/back.png';
import cat from './img/SignIn4.PNG';
import studentboy from './img/studentboy.png';
import { Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleText, ArticlePostTimeRating, ArticleContainer, 
  ArticleAuthorArea, ArticleAuthor, ArticleAuthorImg, ArticlePostTime, ArticlePostRating, ArticleBody, ArticleSelect 
  ,ArticleBaiDistance, ArticleXiangDistance, ArticleXiDistance, ArticleZhongDistance,ArticleXingDistance,ChangeClassCategorySelect} from './components/ArticleStyle.js';
import {Back}  from './components/Style.js';
import { Routes, Route, Link, useNavigate, useLocation } from 'react-router-dom';
import { useEffect, useState, useRef,  } from "react";
import { loginUser } from './cookie';
import { getAuthToken } from "./utils";

const Food = () => {

  const [data, setData] = useState(null);
  const [openModal, setOpenModal] = useState(false);
  const [visibleData, setVisibleData] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [isIUA, setIsIUA] = useState(false);
  const [AS, setAS] = useState("PostTimeNtoF");
  const [isRate, setisRate] = useState(false);
  const [isPostTime, setisPostTime] = useState(true);
  const [isDistance, setisDistance] = useState(false);
  const [isBai, setisBai] = useState(false);
  const [isZhong, setisZhong] = useState(false);
  const [isXiang, setisXiang] = useState(false);
  const [isXing, setisXing] = useState(false);
  const [isXi, setisXi] = useState(false);
  const [noData, setNoData] = useState(false);
  let navigate = useNavigate();
  const articleListRef = useRef(null);
  const location = useLocation();
  const { fromSearch, FArea, FName, FAddr } = location.state;
  const scrollPositionRef = useRef(0);
  const userInfo = loginUser();
  const token = getAuthToken();

  const handlePostASChange = event => {
    setAS("PostTimeNtoF");
    setisPostTime(true);
    setisRate(false);
    setisDistance(false);
    fetch(`/food_load?sort=PostTimeNtoF`)
      .then(response => response.json())
      .then(data => {
        console.log("NOTsearchIn");
        setData(data);
        setVisibleData(data.slice(0, 50));
        setIsLoading(false);
      })
      .catch(error => {
        console.error('Error:', error);
        setIsLoading(false); 
      });
  };
  
  const handleRateASChange = event => {
    setAS("rate_Decrease");
    setisPostTime(false);
    setisRate(true);
    setisDistance(false);
    fetch(`/food_load?sort=rate_Decrease`)
      .then(response => response.json())
      .then(data => {
        console.log("NOTsearchIn");
        setData(data);
        setVisibleData(data.slice(0, 50));
        setIsLoading(false);
      })
      .catch(error => {
        console.error('Error:', error);
        setIsLoading(false); 
      });
  };

  const handleDistanceASChange = event => {
    setAS("distance_Increase");
    setisPostTime(false);
    setisRate(false);
    setisDistance(true);
    fetch(`/food_load?sort=distance_Increase`)
      .then(response => response.json())
      .then(data => {
        console.log("NOTsearchIn");
        setData(data);
        setVisibleData(data.slice(0, 50));
        setIsLoading(false);
      })
      .catch(error => {
        console.error('Error:', error);
        setIsLoading(false); 
      });
  };
  

  function RatingFood({ rating }) {
    const stars = [];
    for (let i = 1; i <= rating; i++) {
      stars.push(<img key={i} className="rating_star" src={star} alt="star" />);
    }

    return <div>{stars}</div>;
  }

  function Articleinfo({ author, post_time, store, rating, postID, road }) {


    const handleShowFoodSubmit = (e) => {
      e.preventDefault();
      const formData = {
        studentID: userInfo,
        postId: postID,
      };
      fetch('/food_full_post', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData)
      })
        .then(response => response.json())
        .then(data => {
          console.log(data);
          navigate("/foodArticle", {
            state: {
              postId: postID
            }
          });
        })
        .catch(error => {
          console.error(error);
        });
    }

    return (
      <ArticleContainer>
        <ArticleText onClick={handleShowFoodSubmit}>
          {road.includes("北寧") &&  <ArticleBaiDistance >{road}</ArticleBaiDistance>}
          {road.includes("中正") &&  <ArticleZhongDistance >{road}</ArticleZhongDistance>}
          {road.includes("祥豐") &&  <ArticleXiangDistance >{road}</ArticleXiangDistance>}
          {road.includes("新豐") && <ArticleXingDistance >{road}</ArticleXingDistance>}
          {road.includes("深溪") &&  <ArticleXiDistance >{road}</ArticleXiDistance>}
          <ArticleAuthorArea>
            {author!=="IUA" &&  <ArticleAuthorImg src={student}></ArticleAuthorImg>}
            {author==="IUA" &&  <ArticleAuthorImg src={logo}></ArticleAuthorImg>}
            <ArticleAuthor>{author}</ArticleAuthor>
          </ArticleAuthorArea>
          <ArticleBody>{store}</ArticleBody>
          <ArticlePostTime>
            <RatingFood rating={rating}></RatingFood>{post_time}
          </ArticlePostTime>
        </ArticleText>
      </ArticleContainer>
    );
  }

  function Food_all() {
    const handleRentFunctionSelect  = event => {

      if(event.target.value==='AllArticle'){
        fetch(`/food_search?area=${''}&store=${''}&addr=${''}`)
        .then(response => response.json())
        .then(data => {
          console.log("NOTsearchIn");
          console.log(data);
          console.log(AS);
          setData(data);
          setVisibleData(data.slice(0, 50));
          setIsLoading(false);
          setNoData(false);
        })
        .catch(error => {
          console.error('Error:', error);
          setIsLoading(false); 
        });
      }
      else if(event.target.value==='FindArticle'){
        setOpenModal(true);
      }

    };
    return (
      <Page>
        <Pagebg>
          <Title>美食板</Title>
          <img className='food_yolk' src={yolk} alt="yolk" />
          <img className='food_redBall' src={redBall} alt="redBall" />
          <Link to='/postArticle'>
            <PostArticleBtn>我要發文</PostArticleBtn>
          </Link>
          <ChangeClassCategorySelect onChange={handleRentFunctionSelect}>
                  <option >功能選單</option>
                  <option value='AllArticle'>全部貼文</option>
                  <option value='FindArticle'>篩選貼文</option>
          </ChangeClassCategorySelect>
          <div className='ArticleSelect'>
          {isPostTime && <input type="radio" id='postTime' name='AS' value='PostTimeNtoF' onChange={handlePostASChange} checked></input>}
          {!isPostTime && <input type="radio" id='postTime' name='AS' value='PostTimeNtoF' onChange={handlePostASChange}></input>}
          <label for="postTime">發布時間</label>

          {isRate && <input type="radio" id='rate' name='AS' value='rate_Decrease' onChange={handleRateASChange} checked></input>}
          {!isRate && <input type="radio" id='rate' name='AS' value='rate_Decrease' onChange={handleRateASChange}></input>}
          <label for="rate">評分高低</label>

          {isDistance && <input type="radio" id='distance' name='AS' value='distance' onChange={handleDistanceASChange} checked></input>}
          {!isDistance && <input type="radio" id='distance' name='AS' value='distance' onChange={handleDistanceASChange}></input>}
          <label for="distance">距離遠近</label>

          </div><br/>
          <ArticleList ref={articleListRef}>
            {noData && <div className='food_noData'>沒有資料</div>}
            {visibleData.map(item => (
              <Articleinfo key={item.postId} author={item.nickname} post_time={item.post_time} store={item.store} rating={item.rating} road={item.road} postID={item.postId}></Articleinfo>
            ))}
          </ArticleList>
        </Pagebg>
      </Page>
    );
  }

  useEffect(() => {
    if(fromSearch===false){
      if (!data) {
        setIsLoading(true);
        fetch(`/food_load?sort=${AS}`)
          .then(response => response.json())
          .then(data => {
            console.log("NOTsearchIn");
            console.log(data);
            console.log(AS);
            setData(data);
            setVisibleData(data.slice(0, 50));
            setIsLoading(false);
          })
          .catch(error => {
            console.error('Error:', error);
            setIsLoading(false); 
          });
      }
    }
    else{
      if (!data) {
        setIsLoading(true);
        console.log(FArea);
        fetch(`/food_search?area=${FArea}&store=${FName}&addr=${FAddr}`)
        .then(response => response.json())
        .then(data => {
          console.log("searchIn");
          console.log(FArea);
          setData(data);
          console.log(data);
          setVisibleData(data.slice(0, 50));
          setIsLoading(false);
          if(data.length===0){
            setNoData(true);
          }
          else
            setNoData(false);
        })
        .catch(error => {
          console.error('Error:', error);
          setIsLoading(false); 
        });

      }
    }   
  }, [data]);

  const handleScroll = () => {
    const scrollPosition = articleListRef.current.scrollTop;
    const listHeight = articleListRef.current.clientHeight;
    const scrollThreshold = articleListRef.current.scrollHeight * 0.8 - listHeight;

    if (scrollPosition >= scrollThreshold && !isLoading && data.length>50) {
      const nextBatch = data.slice(visibleData.length, visibleData.length + 50);
      setVisibleData(prevData => [...prevData, ...nextBatch]);
    }
    scrollPositionRef.current = scrollPosition;
  };

  useEffect(() => {

    articleListRef.current.scrollTop = scrollPositionRef.current;
    articleListRef.current.addEventListener("scroll", handleScroll);

   return () => {
  
    articleListRef.current?.removeEventListener("scroll", handleScroll);
  };
  }, [visibleData, isLoading]);

  return (
    <Page>
      <Link to='/choose'>
              <img src={back} className='food_back' alt="回上一頁" />
      </Link>
      {openModal && <Modal closeModal={setOpenModal} type={"food"} />}
      {!openModal && <Food_all />}
    </Page>
  );
}

export default Food;
