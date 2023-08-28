import './MyArticles.css';
import stars from './img/stars.png';
import heart from './img/heart.png';
import star from './img/star.png';
import logo from './img/IUAlogo.png';
import dog from './img/dog.png';
import yolk from './img/yolk.PNG';
import redBall from './img/redBall.PNG';
import student from './img/student.png';
import { Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleText, ArticlePostTimeRating, ArticleContainer, ArticleFoodContainer, ArticleDistance, 
  ArticleAuthorArea, ArticleAuthor, ArticleAuthorImg, ArticlePostTime, ArticlePostRating, ArticleBody, ArticleSelect } from './components/ArticleStyle.js';
import {ArticleDetailPage, ArticleDetailPosition, ArticleDetailAuthor, ArticleDetailAuthorArea, ArticleDetailAuthorImg, ArticleDetailTitle, ArticleDetailPostDate, ArticleDetailText, ArticleDetailSavedBtn, ArticleDetailAlreadySavedBtn, ArticleDetailContactdBtn, ArticleDetailComment, ArticleDetailPostCommentPosition, ArticleDetailCommentImg, ArticleDetailPostComment, ArticleDetailPostBtn}  from './components/ArticleDetailStyle.js';
import { Routes ,Route,useLocation, useNavigate } from 'react-router-dom';
import {useEffect,useState} from "react";

function MyArticles() {
  const [FoodData, setFoodData] = useState([]);
  const [RentData, setRentData] = useState([]);
  const [ChangeClassData, setChangeClassData] = useState([]);
  const [selectedOption, setSelectedOption] = useState('');
  const [isFoodShown, setIsFoodShown] = useState(true);
  const [isRentShown, setIsRentShown] = useState(false);
  const [isChangeClassShown, setIsChangeClassShown] = useState(false);
  let navigate = useNavigate();
  
  function RatingFood({ rating }) {
    const stars = [];
    for (let i = 1; i <= rating; i++) {
      stars.push(<img key={i} className="rating_star" src={star} alt="star" />);
    }

    return <div>{stars}</div>;
  }

  function MineFinfo({ author, post_time, store, rating, postID, road }) {
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

  function MineHinfo({ author, time, text, postID }) {
    const handleShowHouseSubmit = (e) => {
      e.preventDefault();
      //const student_id = loginUser();
      const formData = {
                      studentID: "00957025",
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
              studentID: "00957025",
              postId : postID,},});
    }

      return (
        <ArticleContainer>
            <ArticleText onClick={handleShowHouseSubmit}>
            <ArticleDetailAuthorArea>
              <ArticleDetailAuthorImg src={dog}></ArticleDetailAuthorImg>
              <ArticleDetailAuthor>{author}</ArticleDetailAuthor>
            </ArticleDetailAuthorArea>
                <ArticleBody>{text}</ArticleBody>
                <ArticlePostTime>{time}</ArticlePostTime>
            </ArticleText>
        </ArticleContainer>
      );
  }
  
  useEffect(() => {
    const queryParams = new URLSearchParams({
      studentID: "00957025"
    });

    const url = '/my_food_posts?' + queryParams.toString();

    fetch(url)
      .then(response => response.json())
      .then(data => {
        console.log(data);
        setRentData(data); 
      })
      .catch(error => {
        console.error(error); 
      });
  }, []); 

  useEffect(() => {
    const queryParams = new URLSearchParams({
      studentID: "00957017",
    });

    const url = '/my_rent_posts?' + queryParams.toString();

    fetch(url)
      .then(response => response.json())
      .then(data => {
        console.log(data);
        setRentData(data); 
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
    // else if (selectedValue === 'changeClass') {
    //   ChangeClassHandler();
    // }
    
  };
  const FoodHandler = () => {
    setIsFoodShown(true);
    setIsRentShown(false);
    // setIsChangeClassShown(false);
  };

  const RentHandler = () => {
    setIsFoodShown(false);
    setIsRentShown(true);
    // setIsChangeClassShown(false);
  };

  // const ChangeClassHandler = () => {
  //   setIsFoodShown(false);
  //   setIsRentShown(false);
  //   setIsChangeClassShown(true);
  // };

    return (
      <div className='MyArticles'>
        <div>
          <Title>我的文章</Title>
          <img className='heart' src={yolk}/>
          <img className='stars' src={redBall}/>
        </div>
        <div>
          <select className='selectType' onChange={handleSelectChange}> 
              <option value="food">美食版</option>
              <option value="rent">租屋版</option>
              <option value="changeClass">換課版</option>
          </select>
        </div>
        {isFoodShown && <ArticleList>
          {FoodData.map((item, index) => (
            <MineFinfo
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
            <MineHinfo
              key={index}
              author={item.name}
              time={item.post_time}
              text={item.title}
              postID={item.postId}
            />
          ))}
        </ArticleList>}
        
      </div>
    );
  }
  
  export default MyArticles;