import './favorite.css';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';
import bookmark from './img/bookmark.png';
import {Page, Pagebg, Title, PostArticleBtn, ArticleList, ArticleText, ArticleContainer, ArticleAuthor, ArticleBody, ArticlePostTime}  from './components/ArticleStyle.js';
import {useEffect,useState} from "react";


function Favorite() {
    const [FoodData, setFoodData] = useState([]);
    const [RentData, setRentData] = useState([]);
    
    function Savedinfo({ author, time, text }) {
      return (
        <ArticleContainer>
          <ArticleText>
            <ArticleAuthor>{author}</ArticleAuthor>
            <ArticlePostTime>{time}</ArticlePostTime>
            <ArticleBody>{text}</ArticleBody>
          </ArticleText>
        </ArticleContainer>
      );
    }
  
    useEffect(() => {
      const formData = {
        studentID: "00957025",
      };
  
      fetch('/favorites_load', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData)
      })
        .then(response => response.json())
        .then(data => {
            
          const newFoodData = [...data.savedFood];
          const newHouseData = [...data.savedHouse];
          setFoodData(newFoodData);
          setRentData(newHouseData);
        })
        .catch(error => {
          console.error(error);
        });
    }, []);
  
    if (!FoodData) {
      return <div>Loading...</div>;
    }
  
    return (
      <div className='Favorite'>
        <div id='div1'>
          <img src={bookmark} alt='收藏' className='favorite_icon'></img>
          <h1>我的收藏</h1>
        </div>
        <ArticleList>
          {FoodData.map((item, index) => (
            <Savedinfo
              key={index}
              author={item.nickname}
              time={item.post_time}
              text={item.store}
            />
          ))}
        </ArticleList>
      </div>
    );
  }
  
  export default Favorite;
  