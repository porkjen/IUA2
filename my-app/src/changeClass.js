import './changeClass.css';
import React from 'react';
import back from './img/back.png';
import {Back}  from './components/Style.js';
import {Page, Pagebg, Title, PostArticleBtn, ArticleList, ArticleText, ArticleContainer, ArticleAuthor, ArticleBody, PerChangeClassBtn, PerHaveChangeClassBtn}  from './components/ArticleStyle.js';
import { Routes ,Route,Link,useNavigate } from 'react-router-dom';
import {useState,useEffect} from "react";

const ChangeClass=()=> {
    let navigate = useNavigate();
    const [data, setData] = useState([]);
    const [isHaveClass, setHaveClass] = useState(false);


    function Articleinfo({ author, text }) {


        return (
          <ArticleContainer>
            <ArticleText>
                <ArticleAuthor>{author}</ArticleAuthor>
                <ArticleBody>{text}</ArticleBody>
            </ArticleText>
          </ArticleContainer>
        );
      }



    function ChangeClass() {
      const [value, setValue] = useState('');

      useEffect(() => {
        if (data.length === 0) {
          fetch("/course_change_have")
          .then(response => response.json())
          .then(data => {
            console.log(data);
            setData(data);
          })
          .catch(error => {
            console.error('Error:', error);
          });
          
        }
      }, [data]); // 添加依賴項data

      const handleShowClassInfoSubmit = (timeValue) => {
        
        setValue(timeValue);
        console.log(value);
        //const student_id = loginUser();
                       //Form submission happens here
            navigate("/changeClassList", {
              state: {
                studentID: "00957025",
                time:timeValue,
                },});
      }

      function ChangeClassBtn({time, haveClass}) {


        if(haveClass>=1)
        {
          setHaveClass(true);
          console.log(time);
          return (<PerHaveChangeClassBtn onClick={() => handleShowClassInfoSubmit(time)}>{time}</PerHaveChangeClassBtn>)
        }
        else if(haveClass===0){
          setHaveClass(false);
          return (<PerChangeClassBtn onClick={() => handleShowClassInfoSubmit(time)}>{time}</PerChangeClassBtn>)
        }
       
      }
      const itemsPerRow = 14;
      return (
        <Page>
          <Link to='/choose'>
              <Back src={back} alt="回上一頁" />
          </Link>
      <Pagebg>
        <Title>換課板</Title>
        <Link to='/postArticle'>
          <PostArticleBtn>我要發文</PostArticleBtn>
        </Link>
        <div className='PerChangeClassTable'>
          {Array.from({ length: Math.ceil(data.length / itemsPerRow) }).map((_, rowIndex) => (
            <div className='column' key={rowIndex}>
              {data.slice(rowIndex * itemsPerRow, (rowIndex + 1) * itemsPerRow).map(item => (
                <div className='ChangeClassBtnContainer' key={item.time}>
                  <ChangeClassBtn time={item.time} haveClass={item.have} onClick={() => handleShowClassInfoSubmit(item.time)}>
                    {item.time}
                  </ChangeClassBtn>
                </div>
              ))}
            </div>
          ))}
        </div>
      </Pagebg>
    </Page>
      );
    }

    return (
           
                <Routes>
                    <Route path="/" element={<ChangeClass />} />
                </Routes>
             
    );
}

export default ChangeClass;
