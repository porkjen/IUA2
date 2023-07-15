import './favorite.css';
import star from './img/stars.png';
import heart from './img/heart.png';
import {Page, Pagebg, Title, PostArticleBtn, ArticleList, ArticleText, ArticleContainer, ArticleAuthor, ArticleBody, ArticlePostTime}  from './components/ArticleStyle.js';
import {useEffect,useState} from "react";
import {useNavigate} from 'react-router-dom';

function Favorite() {
    const [FoodData, setFoodData] = useState([]);
    const [RentData, setRentData] = useState([]);
    const [ChangeClassData, setChangeClassData] = useState([]);
    const [selectedOption, setSelectedOption] = useState('');
    const [isFoodShown, setIsFoodShown] = useState(true);
    const [isRentShown, setIsRentShown] = useState(false);
    const [isChangeClassShown, setIsChangeClassShown] = useState(false);
    let navigate = useNavigate();
    
    function SavedFinfo({ author, time, text, postID }) {
      const handleShowFoodSubmit = (e) => {
        e.preventDefault();
        //const student_id = loginUser();
        const formData = {
                        studentID: "00957025",
                        postId : postID,
                      };
          fetch('/food_full_post', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(formData)})
                  .then(response => response.json())
                  .then(data => {
                        console.log(data);})
                  .catch(error => {
                        console.error(error);});
                       //Form submission happens here
            navigate("/foodArticle", {
              state: {
                studentID: "00957025",
                postId : postID,},});
      }


      return (
        <ArticleContainer>
          <ArticleText onClick={handleShowFoodSubmit}>
            <ArticleAuthor>{author}</ArticleAuthor>
            <ArticlePostTime>{time}</ArticlePostTime>
            <ArticleBody>{text}</ArticleBody>
          </ArticleText>
        </ArticleContainer>
      );
    }

    function SavedHinfo({ author, time, text, postID }) {
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
          // 從服務器獲取的 JSON 格式數據是一個物件，包含 food 和 rent 兩個屬性
          // 每個屬性的值都是一個物件的陣列，將這些陣列組合成一個新的陣列

          const newFoodData = [...data.savedFood];
          const newHouseData = [...data.savedHouse];
          // const newChangeClassData = [...data.savedChangeClass];
          setFoodData(newFoodData);
          setRentData(newHouseData);
          // setChangeClassData(newChangeClassData);
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
      <div className='Favorite'>
        <div>
          <Title>我的收藏</Title>
          <img className='heart' src={heart}/>
          <img className='star' src={star}/>
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
            <SavedFinfo
              key={index}
              author={item.nickname}
              time={item.post_time}
              text={item.store}
            />
          ))}
        </ArticleList>}
        {isRentShown && <ArticleList>
          {RentData.map((item, index) => (
            <SavedHinfo
              key={index}
              author={item.name}
              text={item.title}
            />
          ))}
        </ArticleList>}
        {/* {isChangeClassShown && <ArticleList>
          {ChangeClassData.map((item, index) => (
            <Savedinfo
              
            />
          ))}
        </ArticleList>} */}
        
      </div>
    );
  }
  
  export default Favorite;