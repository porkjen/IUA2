import './changeClass.css';
import React from 'react';
import IsModal from "./components/Modal";
import back from './img/back.png';
import {Back}  from './components/Style.js';
import {Page, Pagebg, Title, PostArticleBtn, ArticleList, ArticleContainer, PerChangeClassBtn, PerHaveChangeClassBtn, 
  ChangeClassCategorySelect,PerHavePairChangeClassBtn,PerMineChangeClassBtn} from './components/ArticleStyle.js';
import { MyclassBody } from './components/Style.js';
import { Routes ,Route,Link,useNavigate } from 'react-router-dom';
import {useState,useEffect} from "react";
import { loginUser } from './cookie';
import { useCookies } from 'react-cookie';
import { getAuthToken } from "./utils";

const ChangeClass=()=> {
    let navigate = useNavigate();
    const [data, setData] = useState([]);
    const [isHaveClass, setHaveClass] = useState(false);
    const [myClass, setMyClass] = useState(false);
    const [notification, setNotification] = useState(false);
    const [changeTable, setchangeTable] = useState(true);
    const [openModal, setOpenModal] = useState(false);
    const [waiting, setWaiting] = useState(true);
    const userInfo = loginUser();
    const token = getAuthToken();



    function ChangeClass() {
      const [value, setValue] = useState('');
      

      useEffect(() => {
        if (data.length === 0) {
          fetch(`/course_change_have?studentID=${userInfo}`)
          .then(response => response.json())
          .then(data => {
            console.log(data);
            setData(data);
            setWaiting(false);
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
                time:timeValue,
                },});
      }

      function IsChangeTable() {


        return (
          <div>
            {!waiting && 
            <div className='PerChangeClassTable'>
              {Array.from({ length: Math.ceil(data.length / itemsPerRow) }).map((_, rowIndex) => (
                <div className='column' key={rowIndex}>
                  {data.slice(rowIndex * itemsPerRow, (rowIndex + 1) * itemsPerRow).map(item => (
                    <div className='ChangeClassBtnContainer' key={item.time}>
                      <ChangeClassBtn time={item.time} haveClass={item.have} pair={item.pair} mine={item.mine} onClick={() => handleShowClassInfoSubmit(item.time)}>
                        {item.time}
                      </ChangeClassBtn>
                    </div>
                  ))}
                </div>
              ))}
            </div>
            }
            {waiting && 
                <div className='cc_LoadingText'>
                  <div class="cc_preloader">
                      Loading
                    <div class="cc_dot1"></div>
                    <div class="cc_dot2"></div>
                    <div class="cc_dot3"></div>
                  </div>
                </div>
              }
          </div>
          
        );
      }

      function ClassItem({name,time,classNum,teacher,category,available}){

        const handleChangeClassBtn = event =>{
          console.log(name);
          navigate("/postArticle", {
            state: {
              studentID:userInfo,
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
                {available && <button className='changeClassBtn' onClick={handleChangeClassBtn} >我要換課</button>}
                {!available && <button className='changeClassBtn' onClick={handleChangeClassBtn} disabled>已發文</button>}
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
            <ClassItem key={item.name} name={item.name} time={item.time} classNum={item.classNum} teacher={item.teacher} category={item.category} available={item.available}></ClassItem>
          ))}
        </ArticleList>
          
        );
      }


      function ChangeClassBtn({time, haveClass, pair, mine}) {

        if(mine>=1)
        {
          setHaveClass(true);
          console.log(time);
          return (
              <PerMineChangeClassBtn onClick={() => handleShowClassInfoSubmit(time)}>{time}</PerMineChangeClassBtn>
          
          )
        }

        if(haveClass>=1 && pair===0)
        {
          setHaveClass(true);
          console.log(time);
          return (
              <PerHaveChangeClassBtn onClick={() => handleShowClassInfoSubmit(time)}>{time}</PerHaveChangeClassBtn>
          
          )
        }
        if(haveClass>=1 && pair>=1)
        {
          setHaveClass(true);
          console.log(time);
          return (
            
              <PerHavePairChangeClassBtn onClick={() => handleShowClassInfoSubmit(time)}>{time}</PerHavePairChangeClassBtn>
            
          )
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
        setWaiting(true);
        fetch(`/curriculum_search?studentID=${userInfo}`, {
          headers: {
            'Authorization': `${token}`  // 將 token 添加到請求頭中
          }
        })
          .then(response => response.json())
          .then(data => {
            console.log(data);
            console.log(myClass);
            setData(data);
            setWaiting(false);
          })
          .catch(error => {
            console.error('Error:', error);
          });
      };
      

      const handleChangeTableChange = event => {
        setMyClass(false);
        setchangeTable(true);
        setchangeTable(true);
        setWaiting(true);
        console.log("handleChangeTableChange called");
        console.log(myClass);
        fetch(`/course_change_have?studentID=${userInfo}`)
        .then(response => response.json())
        .then(data => {
          console.log(data);
          setData(data);
          setWaiting(false);
        })
        .catch(error => {
          console.error('Error:', error);
        });
      };

      const handleSetNotification = event => {
        setNotification(true);
      };

      const handleChangeClassFunctionSelect = event => {
        if(event.target.value==="setNotification")
          setOpenModal(true);
        else if(event.target.value==="myNotification")
          navigate('/myNotification');
        else if(event.target.value==="myPost")
          navigate('/MyArticles');
      };


      return (
        <Page>
           {!openModal &&
          <Link to='/choose'>
              <Back src={back} alt="回上一頁" />
          </Link>}
      <Pagebg>
      {!openModal && <Title>換課板</Title>}
        <div className='ChangeClassSelect'>
          {myClass && !openModal &&  <input type="radio" id='myclass' name='selectTable' onChange={handleMyClassTableChange} checked></input>}
          {!myClass && !openModal &&  <input type="radio" id='myclass' name='selectTable'  onChange={handleMyClassTableChange}></input>}
          {!openModal && <label for="myclass">我的課表</label>}

          {changeTable && !openModal &&  <input type="radio" id='changeTable' name='selectTable' onChange={handleChangeTableChange} checked></input>}
          {!changeTable && !openModal &&  <input type="radio" id='changeTable' name='selectTable' onChange={handleChangeTableChange}></input>}
          {!openModal && <label for="changeClassTable">選課課表</label>}

        </div><br/>
          {changeTable && !openModal && 
          <div>
            <label className='cc_note'>綠:有貼文<br/>藍:發過的文<br/>紅:配對成功</label>
            <IsChangeTable/>
          </div>
          
          }
          {myClass && !openModal && <AllMyClass myClassData={data}/>}
          {openModal && <IsModal closeModal={setOpenModal} type={"setChangeClassNotification"}  />}
          
          {!openModal && 
            <ChangeClassCategorySelect onChange={handleChangeClassFunctionSelect}>
              <option >功能選單</option>
              <option value='myNotification'>我的提醒</option>
              <option value='setNotification'>設置提醒</option>
              <option value='myPost'>我的貼文</option>
          </ChangeClassCategorySelect>}
       

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
