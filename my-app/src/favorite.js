import './favorite.css';
import bookmark from './img/bookmark.png';
import {Page, Pagebg, Title, PostArticleBtn, ArticleList, ArticleText, ArticleContainer, ArticleAuthor, ArticleBody, ArticlePostTime}  from './components/ArticleStyle.js';
import {useEffect,useState} from "react";


function Favorite() {
    const [FoodData, setFoodData] = useState([]);
    const [RentData, setRentData] = useState([]);
    const [ChangeClassData, setChangeClassData] = useState([]);
    const [selectedOption, setSelectedOption] = useState('');
    const [isFoodShown, setIsFoodShown] = useState(true);
    const [isRentShown, setIsRentShown] = useState(false);
    const [isChangeClassShown, setIsChangeClassShown] = useState(false);
    
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
        <div id='div1'>
          <img src={bookmark} alt='收藏' className='favorite_icon'></img>
          <h1>我的收藏</h1>
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
            <Savedinfo
              key={index}
              author={item.nickname}
              time={item.post_time}
              text={item.store}
            />
          ))}
        </ArticleList>}
        {isRentShown && <ArticleList>
          {RentData.map((item, index) => (
            <Savedinfo
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