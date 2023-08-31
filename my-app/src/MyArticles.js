import './MyArticles.css';
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
  ,ArticleBaiDistance, ArticleXiangDistance, ArticleXiDistance, ArticleZhongDistance,ArticleXingDistance,ChangeClassCategorySelect, ArticleDistance,AlreadyArticleText} from './components/ArticleStyle.js';
import {ArticleDetailPage, ArticleDetailPosition, ArticleDetailAuthor, ArticleDetailAuthorArea, ArticleDetailAuthorImg, ArticleDetailTitle, ArticleDetailPostDate, ArticleDetailText, ArticleDetailSavedBtn, ArticleDetailAlreadySavedBtn, ArticleDetailContactdBtn, ArticleDetailComment, ArticleDetailPostCommentPosition, ArticleDetailCommentImg, ArticleDetailPostComment, ArticleDetailPostBtn}  from './components/ArticleDetailStyle.js';
import { Routes ,Route,Link ,useNavigate, useLocation} from 'react-router-dom';
import {useEffect,useState} from "react";
import back from './img/back.png';
import {Back}  from './components/Style.js';
import { loginUser } from './cookie';
import { getAuthToken } from "./utils";

function MyArticles() {
  const [FoodData, setFoodData] = useState([]);
    const [RentData, setRentData] = useState([]);
    const [ChangeClassData, setChangeClassData] = useState([]);
    const [selectedOption, setSelectedOption] = useState('');
    const [isFoodShown, setIsFoodShown] = useState(true);
    const [isRentShown, setIsRentShown] = useState(false);
    const [isChangeClassShown, setIsChangeClassShown] = useState(false);
    const [data, setData] = useState(null);
    const [alreadyChange, setAlreadyChange] = useState(false);
    let navigate = useNavigate();
    const userInfo = loginUser();
  
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
              fromMyPost :true,
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

  function MineHinfo({ author, time, text, postID }) {
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
              fromMyPost : true,},});
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

  function MineCinfo({ author, className, category, postID, post_time, status }) {

    if(status==="未換"){
      setAlreadyChange(false);
    }
    else{
      setAlreadyChange(true);
    }
    const handleShowClassSubmit = (e) => {
      e.preventDefault();
      //const student_id = loginUser();
      const formData = {
                      studentID: userInfo,
                    };
        fetch('/course_full_post', {
          method: 'POST',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify(formData)})
                .then(response => response.json())
                .then(data => {
                      console.log(data);})
                .catch(error => {
                      console.error(error);});
    }

    function NotChange(){
      return(
          <ArticleText onClick={handleShowClassSubmit}>
              <ArticleDistance>{category}</ArticleDistance>
              <ArticleAuthorArea>
                <ArticleAuthorImg src={student}></ArticleAuthorImg>
                <ArticleAuthor>{author}</ArticleAuthor>
              </ArticleAuthorArea>
              <ArticleBody>{className}</ArticleBody>
              <ArticlePostTime>{post_time}</ArticlePostTime>
          </ArticleText>
      );
    }

    function Change(){
      return(
          <AlreadyArticleText onClick={handleShowClassSubmit} disabled>
              <ArticleDistance>{status}</ArticleDistance>
              <ArticleAuthorArea>
                <ArticleAuthorImg src={student}></ArticleAuthorImg>
                <ArticleAuthor>{author}</ArticleAuthor>
              </ArticleAuthorArea>
              <ArticleBody>{className}</ArticleBody>
              <ArticlePostTime>{post_time}</ArticlePostTime>
          </AlreadyArticleText>
      );
    }


      return (
        <ArticleContainer>
          {alreadyChange && <Change/>}
          {!alreadyChange && <NotChange/>}
          
        </ArticleContainer>
      );
    }
  
  useEffect(() => {
    const queryParams = new URLSearchParams({
      studentID: "00957017"
    });

    const url = '/my_food_posts?' + queryParams.toString();

    fetch(url)
      .then(response => response.json())
      .then(data => {
        console.log(queryParams.toString());
        console.log(data);
        setFoodData(data); 
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
        console.log(queryParams.toString());
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

    const url = '/my_course_posts?' + queryParams.toString();

    fetch(url)
      .then(response => response.json())
      .then(data => {
        console.log(queryParams.toString());
        console.log(data);
        setChangeClassData(data); 
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
    else if (selectedValue === 'changeClass') {
      ChangeClassHandler();
    }
    
  };
  const FoodHandler = () => {
    setIsFoodShown(true);
    setIsRentShown(false);
    setIsChangeClassShown(false);
  };

  const RentHandler = () => {
    setIsFoodShown(false);
    setIsRentShown(true);
    setIsChangeClassShown(false);
  };

  const ChangeClassHandler = () => {
    setIsFoodShown(false);
    setIsRentShown(false);
    setIsChangeClassShown(true);
  };

    return (
      <div className='MyArticles'>
        <Link to='/choose'>
              <Back src={back} alt="回上一頁" />
        </Link>
        <div>
          <Title>我的貼文</Title>
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
              <MineHinfo
                key={item.postId}
                author={item.name} 
                time={item.postTime} 
                text={item.title} 
                postID={item.postId}
              />
            ))}
          </ArticleList>}
          {isChangeClassShown && <ArticleList>
            {ChangeClassData.map((item, index) => (
              <MineCinfo
              key={item.postId} 
              author={item.studentID} 
              className={item.course} 
              category={item.category} 
              postID={item.postId} 
              post_time={item.post_time} 
              status={item.status}
              />
            ))}
          </ArticleList>}
        </div>
    );
  }
  
  export default MyArticles;