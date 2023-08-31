import './favorite.css';
import stars from './img/stars.png';
import heart from './img/heart.png';
import star from './img/star.png';
import logo from './img/IUAlogo.png';
import dog from './img/dog.png';
import yolk from './img/yolk.PNG';
import redBall from './img/redBall.PNG';
import student from './img/student.png';
import cat from './img/SignIn4.PNG';
import { Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleText, ArticlePostTimeRating, ArticleContainer, 
  ArticleAuthorArea, ArticleAuthor, ArticleAuthorImg, ArticlePostTime, ArticlePostRating, ArticleBody, ArticleSelect 
  ,ArticleBaiDistance, ArticleXiangDistance, ArticleXiDistance, ArticleZhongDistance,ArticleXingDistance,ChangeClassCategorySelect} from './components/ArticleStyle.js';
import {ArticleDetailPage, ArticleDetailPosition, ArticleDetailAuthor, ArticleDetailAuthorArea, ArticleDetailAuthorImg, ArticleDetailTitle, ArticleDetailPostDate, ArticleDetailText, ArticleDetailSavedBtn, ArticleDetailAlreadySavedBtn, ArticleDetailContactdBtn, ArticleDetailComment, ArticleDetailPostCommentPosition, ArticleDetailCommentImg, ArticleDetailPostComment, ArticleDetailPostBtn}  from './components/ArticleDetailStyle.js';
import { Routes ,Route,Link ,useNavigate, useLocation} from 'react-router-dom';
import {useEffect,useState} from "react";
import back from './img/back.png';
import {Back}  from './components/Style.js';
import { loginUser } from './cookie';
import { getAuthToken } from "./utils";


function Favorite() {
    const [FoodData, setFoodData] = useState([]);
    const [RentData, setRentData] = useState([]);
    const [ChangeClassData, setChangeClassData] = useState([]);
    const [selectedOption, setSelectedOption] = useState('');
    const [isFoodShown, setIsFoodShown] = useState(true);
    const [isRentShown, setIsRentShown] = useState(false);
    const [data, setData] = useState(null);
    let navigate = useNavigate();
    const userInfo = loginUser();
    
    function RatingFood({ rating }) {
      const stars = [];
      for (let i = 1; i <= rating; i++) {
        stars.push(<img key={i} className="rating_star" src={star} alt="star" />);
      }
  
      return <div>{stars}</div>;
    }

    function SavedFinfo({ author, post_time, store, rating, postID, road }) {
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
                postId: postID,
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
              {author!=="IUA" &&  <img className='food_authorImg' src={student}/>}
              {author==="IUA" &&  <img className='food_authorImg' src={logo}/>}
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

    function SavedHinfo({ author, time, text, postID }) {
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
                postId : postID,},});
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
  
    useEffect(() => {
      const formData = {
        studentID: userInfo,
      };
  
      fetch('/favorites_load', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData)
      })
        .then(response => response.json())
        .then(data => {
          // 從服務器獲取的 JSON 格式數據是一個物件，包含 food 和 rent 兩個屬性
          // 每個屬性的值都是一個物件的陣列，將這些陣列組合成一個新的陣列
          console.log(data);
          const newFoodData = [...data.savedFood];
          const newHouseData = [...data.savedHouse];
          setFoodData(newFoodData);
          setRentData(newHouseData);
        })
        .catch(error => {
          console.error(error);
        });
    }, []);


    if (!FoodData || !RentData || !ChangeClassData) {
      return <div>Loading...</div>;
    }

    const handleSelectChange = (event) => {
      const selectedValue = event.target.value;
      setSelectedOption(selectedValue);
  
      if (selectedValue === 'food') {
        FoodHandler();
      }
      else if (selectedValue === 'rent') {
        RentHandler();
      }
      
    };
    const FoodHandler = () => {
      setIsFoodShown(true);
      setIsRentShown(false);
    };

    const RentHandler = () => {
      setIsFoodShown(false);
      setIsRentShown(true);
    };

    return (
      <div className='Favorite'>
        <Link to='/choose'>
              <Back src={back} alt="回上一頁" />
        </Link>
        <div>
          <Title>我的收藏</Title>
          <img className='heart' src={yolk}/>
          <img className='stars' src={redBall}/>
        </div>
        <div>
          <select className='selectType' onChange={handleSelectChange}> 
              <option value="food">美食版</option>
              <option value="rent">租屋版</option>
          </select>
        </div>
        <div className='scrollableContainer'>
          {isFoodShown && <ArticleList>
            {FoodData.map((item, index) => (
              <SavedFinfo
                key={item.postId}
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
                key={item.postId}
                author={item.name} 
                time={item.postTime} 
                text={item.title} 
                postID={item.postId}
              />
            ))}
          </ArticleList>}
        </div>
        
        
      </div>
    );
  }
  
  export default Favorite;