import './changeClass.css';
import React from 'react';
import Modal from "./components/Modal";
import back from './img/back.png';
import {Back}  from './components/Style.js';
import {Page, Pagebg, Title, PostArticleBtn, ArticleList, ArticleText, ArticleContainer, ArticleAuthor, ArticleBody, PerChangeClassBtn, PerHaveChangeClassBtn, ChangeClassStatusSelect}  from './components/ArticleStyle.js';
import { RemainTitle, RemainContainer, RemainList, RemainText, RemainBody, MyclassBody, MyclassText } from './components/Style.js';
import { Routes ,Route,Link,useNavigate } from 'react-router-dom';
import {useState,useEffect} from "react";

const ChangeClass=()=> {
    let navigate = useNavigate();
    const [data, setData] = useState([]);
    const [isHaveClass, setHaveClass] = useState(false);
    const [myClass, setMyClass] = useState(false);
    const [notification, setNotification] = useState(false);
    const [changeTable, setchangeTable] = useState(true);
    const [openModal, setOpenModal] = useState(false);


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
                <button className='changeClassBtn' onClick={handleChangeClassBtn} >我要換課</button>
              </div>
                <label className='changeClass_className'>{name}</label>
                <div>課程時間:&nbsp;{time}</div>
            </MyclassBody>
          </ArticleContainer>
        );
      }

      function AllMyClass({myClassData}) {


        return (

          <ArticleList >
          {myClassData.map((item, index) => (
            <ClassItem key={item.name} name={item.name} time={item.time} classNum={item.classNum} teacher={item.teacher} category={item.category}></ClassItem>
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

      const handleSetNotification = event => {
        setNotification(true);
      };


      return (
        <Page>
          <Link to='/choose'>
              <Back src={back} alt="回上一頁" />
          </Link>
      <Pagebg>
        <Title>換課板</Title>
        <PostArticleBtn onClick={handleSetNotification}>設置通知</PostArticleBtn>
        <div className='ChangeClassSelect'>
          {myClass &&  <input type="radio" id='myclass' name='selectTable' onChange={handleMyClassTableChange} checked></input>}
          {!myClass &&  <input type="radio" id='myclass' name='selectTable'  onChange={handleMyClassTableChange}></input>}
          <label for="myclass">我的課表</label>

          {changeTable &&  <input type="radio" id='changeTable' name='selectTable' onChange={handleChangeTableChange} checked></input>}
          {!changeTable &&  <input type="radio" id='changeTable' name='selectTable' onChange={handleChangeTableChange}></input>}
          <label for="changeClassTable">選課課表</label>

        </div><br/>
          {changeTable && <IsChangeTable/>}
          {myClass && <AllMyClass myClassData={data}/>}
          {notification && <Modal closeModal={setOpenModal} type={"setChangeClassNotification"}  />}
       

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
