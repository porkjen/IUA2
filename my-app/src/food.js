import './food.css';
import React from 'react';
import Modal from "./components/Modal";
import yolk from './img/yolk.PNG';
import star from './img/star.png';
import redBall from './img/redBall.PNG';
import logo from './img/IUAlogo.png';
import student from './img/student.png';
import cat from './img/SignIn4.PNG';
import studentboy from './img/studentboy.png';
import { Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleText, ArticlePostTimeRating, ArticleContainer, ArticleFoodContainer, ArticleDistance, 
  ArticleAuthorArea, ArticleAuthor, ArticleAuthorImg, ArticlePostTime, ArticlePostRating, ArticleBody, ArticleSelect } from './components/ArticleStyle.js';
import { Routes, Route, Link, useNavigate, useLocation } from 'react-router-dom';
import { useEffect, useState, useRef,  } from "react";

const Food = () => {

  const [data, setData] = useState(null);
  const [openModal, setOpenModal] = useState(false);
  const [visibleData, setVisibleData] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [isIUA, setIsIUA] = useState(false);
  const [AS, setAS] = useState("PostTimeNtoF");
  const [isRate, setisRate] = useState(false);
  const [isPostTime, setisPostTime] = useState(true);
  let navigate = useNavigate();
  const articleListRef = useRef(null);
  const location = useLocation();
  const { fromSearch, FArea, FName, FAddr } = location.state;
  const scrollPositionRef = useRef(0);

  const handlePostASChange = event => {
    setAS("PostTimeNtoF");
    setisPostTime(true);
    setisRate(false);
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
        studentID: "00957025",
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
              studentID: "00957025",
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
          {road!==null &&  <ArticleDistance>{road}</ArticleDistance>}
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
    return (
      <Page>
        <Pagebg>
          <Title>美食板</Title>
          <img className='food_yolk' src={yolk} alt="yolk" />
          <img className='food_redBall' src={redBall} alt="redBall" />
          <Link to='/postArticle'>
            <PostArticleBtn>我要發文</PostArticleBtn>
          </Link>
          <ChooseArticleBtn onClick={() => setOpenModal(true)}>篩選貼文</ChooseArticleBtn>
          <div className='ArticleSelect'>
          {isPostTime && <input type="radio" id='distance' name='AS' value='PostTimeNtoF' onChange={handlePostASChange} checked></input>}
          {!isPostTime && <input type="radio" id='distance' name='AS' value='PostTimeNtoF' onChange={handlePostASChange}></input>}
          <label for="distance">發布時間近到遠</label>

          {isRate && <input type="radio" id='rate' name='AS' value='rate_Decrease' onChange={handleRateASChange} checked></input>}
          {!isRate && <input type="radio" id='rate' name='AS' value='rate_Decrease' onChange={handleRateASChange}></input>}
          <label for="rate">評分高到低</label>
          </div><br/>
          <ArticleList ref={articleListRef}>
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
        fetch(`/food_search?area=${FArea}&store=${FName}&addr=${FAddr}`)
        .then(response => response.json())
        .then(data => {
          console.log("searchIn");
          console.log(FArea);
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
  }, [data]);

  const handleScroll = () => {
    const scrollPosition = articleListRef.current.scrollTop;
    const listHeight = articleListRef.current.clientHeight;
    const scrollThreshold = articleListRef.current.scrollHeight * 0.8 - listHeight;

    if (scrollPosition >= scrollThreshold && !isLoading) {
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
      {openModal && <Modal closeModal={setOpenModal} type={"food"} />}
      {!openModal && <Food_all />}
    </Page>
  );
}

export default Food;
