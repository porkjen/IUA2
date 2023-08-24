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
    const [myClass, setMyClass] = useState(false);
    const [changeTable, setchangeTable] = useState(true);



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
      }, []); // 添加依賴項data

      const handleShowClassInfoSubmit = (timeValue) => {
        
        setValue(timeValue);
        console.log(value);
        //const student_id = loginUser();
                       //Form submission happens here
            navigate("/changeClassList", {
              state: {
                studentID: "00957017",
                time:timeValue,
                },});
      }

      function IsChangeTable() {


        return (
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
        );
      }

      function ClassItem({name,time,classNum}){
        return(
          <ArticleContainer>
            <ArticleText>
                <ArticleAuthor>{classNum}</ArticleAuthor>
                <ArticleBody>{name}</ArticleBody>
                <ArticleBody>{time.join('、')}</ArticleBody>
            </ArticleText>
          </ArticleContainer>
        );
      }

      function AllMyClass({myClassData}) {


        return (

          <ArticleList >
          {myClassData.map(item => (
            <ClassItem key={item.name} name={item.name} time={item.time} classNum={item.classNum}></ClassItem>
          ))}
        </ArticleList>
          
        );
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

      const handleMyClassTableChange = event => {
        setMyClass(true);
        setchangeTable(false);
        setMyClass(true);
        fetch(`/curriculum_search?studentID=${"00957025"}`)
          .then(response => response.json())
          .then(data => {
            console.log(data);
            console.log(myClass);
            setData(data);
          })
          .catch(error => {
            console.error('Error:', error);
          });
      };
      

      const handleChangeTableChange = event => {
        setMyClass(false);
        setchangeTable(true);
        console.log("handleChangeTableChange called");
        console.log(myClass);
        fetch("/course_change_have")
        .then(response => response.json())
        .then(data => {
          console.log(data);
          setData(data);
        })
        .catch(error => {
          console.error('Error:', error);
        });
      };

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
        <div className='ChangeClassSelect'>
          {myClass && <input type="radio" id='myclass' name='selectTable' onChange={handleMyClassTableChange} checked></input>}
          {!myClass && <input type="radio" id='myclass' name='selectTable'  onChange={handleMyClassTableChange}></input>}
          <label for="myclass">我的課表</label>

          {changeTable && <input type="radio" id='changeTable' name='selectTable' onChange={handleChangeTableChange} checked></input>}
          {!changeTable && <input type="radio" id='changeTable' name='selectTable' onChange={handleChangeTableChange}></input>}
          <label for="changeClassTable">選課課表</label>

        </div><br/>
          {changeTable && <IsChangeTable/>}
          {myClass && <AllMyClass myClassData={data}/>}
       

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
