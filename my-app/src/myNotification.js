import './changeClass.css';
import React from 'react';
import Modal from "./components/Modal";
import back from './img/back.png';
import {Back}  from './components/Style.js';
import {Page, Pagebg, Title, PostArticleBtn, ArticleList, ArticleContainer, PerChangeClassBtn, PerHaveChangeClassBtn, ChangeClassCategorySelect} from './components/ArticleStyle.js';
import { MyclassBody } from './components/Style.js';
import { Routes ,Route,Link,useNavigate } from 'react-router-dom';
import {useState,useEffect} from "react";
import { loginUser } from './cookie';
import { useCookies } from 'react-cookie';
import { getAuthToken } from "./utils";

const MyNotification=()=> {
    let navigate = useNavigate();
    const [data, setData] = useState([]);
    const [isHaveClass, setHaveClass] = useState(false);
    const [myClass, setMyClass] = useState(false);
    const [notification, setNotification] = useState(false);
    const [changeTable, setchangeTable] = useState(true);
    const [openModal, setOpenModal] = useState(false);
    const userInfo = loginUser();
    const token = getAuthToken();



    function MyNotification() {
      const [value, setValue] = useState('');
      

      useEffect(() => {
        if (data.length === 0) {
          fetch("/exchange_notification")
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

      
      function ClassItem({name,time,classNum,teacher,category}){

        const handleChangeClassBtn = event =>{
          console.log(name);
          navigate("/postArticle", {
            state: {
              studentID:"00957025",
              changeClassClassName:{name},
              changeClassClassTeacher:{teacher},
              changeClassClassTime:{time},
              changeClassCategory:{category},
              IsChangeClass:true,
               },});
        };

        return(
          <ArticleContainer>
            <MyclassBody>
              <div className='changeClass_classNum_className'>
                <label className='changeClass_classNum'>{classNum}</label>
                <button className='changeClassBtn' onClick={handleChangeClassBtn} >刪除</button>
              </div>
                <label className='changeClass_className'>{name}</label>
                <div>課程時間:&nbsp;{time}</div>
            </MyclassBody>
          </ArticleContainer>
        );
      }

      function AllMyNotification({myNotificationData}) {


        return (

          <ArticleList >
          {myNotificationData.map((item, index) => (
            <ClassItem key={item.name} name={item.name} time={item.time} classNum={item.classNum} teacher={item.teacher} category={item.category}></ClassItem>
          ))}
        </ArticleList>
          
        );
      }


      

      const handleSetNotification = event => {
        setNotification(true);
      };

      const handleChangeClassFunctionSelect = event => {
        setOpenModal(true);
      };


      return (
        <Page>
          <Link to='/choose'>
              <Back src={back} alt="回上一頁" />
          </Link>
      <Pagebg>
        <Title>我的通知</Title>
        <div className='ChangeClassSelect'>

        </div><br/>
            <ChangeClassCategorySelect onChange={handleChangeClassFunctionSelect}>
              <option >功能選單</option>
              <option value='selectArticle'>我的提醒</option>
              <option value='setNotification'>設置提醒</option>
          </ChangeClassCategorySelect>
       <AllMyNotification/>

      </Pagebg>
    </Page>
      );
    }

    return (
           
                <Routes>
                    <Route path="/" element={<MyNotification />} />
                </Routes>
             
    );
}

export default MyNotification;
