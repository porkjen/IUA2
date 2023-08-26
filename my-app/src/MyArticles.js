import './MyArticles.css';
import yolk from './img/yolk.PNG';
import redBall from './img/redBall.PNG';
import { Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleText, ArticlePostTimeRating, ArticleContainer, ArticleFoodContainer, ArticleDistance, 
  ArticleAuthorArea, ArticleAuthor, ArticleAuthorImg, ArticlePostTime, ArticlePostRating, ArticleBody, ArticleSelect } from './components/ArticleStyle.js';
import {ArticleDetailPage, ArticleDetailPosition, ArticleDetailAuthor, ArticleDetailAuthorArea, ArticleDetailAuthorImg, ArticleDetailTitle, ArticleDetailPostDate, ArticleDetailText, ArticleDetailSavedBtn, ArticleDetailAlreadySavedBtn, ArticleDetailContactdBtn, ArticleDetailComment, ArticleDetailPostCommentPosition, ArticleDetailCommentImg, ArticleDetailPostComment, ArticleDetailPostBtn}  from './components/ArticleDetailStyle.js';
import { Routes ,Route,useLocation, useNavigate } from 'react-router-dom';
import {useEffect,useState} from "react";


function MyArticles() {
    
    return (
      <div className='MyArticles'>
        <div>
          <Title>我的文章</Title>
          <img className='heart' src={yolk}/>
          <img className='stars' src={redBall}/>
        </div>
        <div>
          <select className='selectType' > 
              <option value="food">美食版</option>
              <option value="rent">租屋版</option>
              <option value="changeClass">換課版</option>
          </select>
        </div>
        {/* {isFoodShown && <ArticleList>
          {FoodData.map((item, index) => (
            <SavedFinfo
              key={index}
              author={item.nickname}
              post_time={item.post_time}
              store={item.store} 
              rating={item.rating} 
              road={item.road}
              postID={item.postId}
            />
          ))}
        </ArticleList>}
        {isRentShown && <ArticleList>
          {RentData.map((item, index) => (
            <SavedHinfo
              key={index}
              author={item.name} 
              time={item.postTime} 
              text={item.title} 
              postID={item.postId}
            />
          ))}
        </ArticleList>}
         */}
      </div>
    );
  }
  
  export default MyArticles;