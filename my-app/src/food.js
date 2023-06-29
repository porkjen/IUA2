import './food.css';
import React from 'react';
import Modal from "./components/Modal";
import yolk from './img/yolk.PNG';
import star from './img/star.png';
import redBall from './img/redBall.PNG';
import logo from './img/IUAlogo.png';
import { Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleText, ArticlePostTimeRating, ArticleContainer, ArticleFoodContainer, ArticleAuthorArea, ArticleAuthor, ArticleAuthorImg, ArticlePostTime, ArticlePostRating, ArticleBody } from './components/ArticleStyle.js';
import { Routes, Route, Link, useNavigate } from 'react-router-dom';
import { useEffect, useState, useRef } from "react";

const Food = () => {

  const [data, setData] = useState(null);
  const [openModal, setOpenModal] = useState(false);
  const [visibleData, setVisibleData] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  let navigate = useNavigate();
  const articleListRef = useRef(null);

  function RatingFood({ rating }) {
    const stars = [];
    for (let i = 1; i <= rating; i++) {
      stars.push(<img key={i} className="rating_star" src={star} alt="star" />);
    }

    return <div>{stars}</div>;
  }

  function Articleinfo({ author, post_time, store, rating, postID }) {

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
          <ArticleAuthorArea>
            <ArticleAuthorImg src={logo}></ArticleAuthorImg>
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
          <ArticleList ref={articleListRef}>
            {visibleData.map(item => (
              <Articleinfo key={item.postId} author={item.nickname} post_time={item.post_time} store={item.store} rating={item.rating} postID={item.postId}></Articleinfo>
            ))}
          </ArticleList>
        </Pagebg>
      </Page>
    );
  }

  useEffect(() => {
    if (!data) {
      setIsLoading(true);
      fetch('/food_load')
        .then(response => response.json())
        .then(data => {
          setData(data);
          setVisibleData(data.slice(0, 50));
          setIsLoading(false);
        })
        .catch(error => {
          console.error('Error:', error);
          setIsLoading(false); 
        });
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
  };

  useEffect(() => {
    articleListRef.current.addEventListener("scroll", handleScroll);

    return () => {
      articleListRef.current.removeEventListener("scroll", handleScroll);
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
