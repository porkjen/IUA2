import './changeClass.css';
import React from 'react';
import IsModal from "./components/Modal";
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
    const [rent_data, setRent_data] = useState([]);
    const [cc, setcc] = useState(true);
    const [rent, setRent] = useState(false);
    const [openRentModal, setOpenRentModal] = useState(false);
    const [openCCModal, setOpenCCModal] = useState(false);
    const [isOne, setIsOne] = useState(false);
    const userInfo = loginUser();
    const token = getAuthToken();



    function MyNotification() {
      const [value, setValue] = useState('');
      

      useEffect(() => {
        if (data.length === 0) {
          fetch(`/load_exchange_condition?studentID=${userInfo}`)
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

      
      function ClassItem({name,time,classNum,category,index}){


        const handleChangeClassBtn = event =>{
          fetch(`/delete_exchange_condition?studentID=${userInfo}&listNumber=${index}`, {
            method: 'DELETE',
            headers: {
              'Content-Type': 'application/json',
            },
          })
            .then((response) => response.status)
            .catch((error) => {
              console.error(error);
            });
            window.location.reload();
        };

        return(
          <ArticleContainer>
            <MyclassBody>
              <div className='changeClass_classNum_className'>
                {time.length===0 && 
                  <div>
                      <div>課程名稱:&nbsp;{name}</div>
                      <div>課程代號:&nbsp;{classNum}</div>
                  </div>
                }
                {time.length>0 && 
                  <div>
                      {time&&<div>課程時間:&nbsp;{time.join('、')}</div>}
                      {category&&<div>課程分類:&nbsp;{category.join('、')}</div>}
                  </div>
                }
                <button className='delChangeClassNotiBtn' onClick={handleChangeClassBtn} >刪除</button>
              </div>

            </MyclassBody>
          </ArticleContainer>
        );
      }

      function AllMyNotification({myNotificationData}) {


        return (

          <ArticleList >
          {myNotificationData.map((item, index) => (
            <ClassItem key={item.name} name={item.name} time={item.time} classNum={item.number} category={item.category} index={index}></ClassItem>
          ))}
        </ArticleList>
          
        );
      }

      function PowerArray({pm}){


        return (

          <div>
            {pm==="一度" && <div>{pm}{rent_data.waterMoney}元</div>}
            {pm!=="一度" && <div>{pm}</div>}
          </div>
          
        );
      }

      function AllMyRentNotification({myRentNotificationData}) {
        const handleRentDelBtn = event =>{
          fetch(`/delete_rent_condition?studentID=${userInfo}`, {
            method: 'DELETE',
            headers: {
              'Content-Type': 'application/json',
            },
          })
            .then((response) => response.status)
            .catch((error) => {
              console.error(error);
            });
            window.location.reload();
        };



        return (

          <div className='mN_rentNotification'>
            <div>樓層:{rent_data.floor}</div>
            <div>性別:{rent_data.gender}</div>
            <div>停車位:{rent_data.parking}</div>
            <div>人數:{rent_data.people}</div>
            {rent_data.power && <div>電費計算方式:{rent_data.power.join('、')}</div>}
            <div>電費:一度{rent_data.powerMoney}元</div>
            {rent_data.water && <div>水費計算方式:{rent_data.water.join('、')}</div>}
            <div>水費:月繳{rent_data.waterMoney}元</div>
            {rent_data.region && <div>地區:{rent_data.region.join('、')}</div>}
            <div>租金:{rent_data.rent}</div>
            {rent_data.style && <div>房型:{rent_data.style.join('、')}</div>}
            <button className='rentDelBtn' onClick={handleRentDelBtn}>清除所有條件</button>
          </div>
          
        );
      }



      const handlecc= event => {
        setcc(true);
        setRent(false);
      };

      const handlerent = event => {
        setRent(true);
        setcc(false);
        
          fetch(`/load_rent_condition?studentID=${userInfo}`)
          .then(response => response.json())
          .then(data => {
            console.log(data);
            setRent_data(data);
          })
          .catch(error => {
            console.error('Error:', error);
          });
        
      };

      const handleFunctionSelect = event => {
        if(event.target.value==="setRentNotification"){
          setOpenRentModal(true);
        }
        else if(event.target.value==="setCCNotification"){
          setOpenCCModal(true);
        }
      };


      return (
        <Page>
          <Link to='/choose'>
              <Back src={back} alt="回上一頁" />
          </Link>
      <Pagebg>
        {!openRentModal && !openCCModal &&<Title>我的提醒</Title>}
         {!openRentModal && !openCCModal &&  
         <div>
          <div className='NotiSelect'>
          {cc &&  <input type="radio" id='myclass' name='selectTable' onChange={handlecc} checked></input>}
          {!cc &&  <input type="radio" id='myclass' name='selectTable'  onChange={handlecc}></input>}
          <label for="myclass">選課提醒</label>

          {rent &&  <input type="radio" id='changeTable' name='selectTable' onChange={handlerent} checked></input>}
          {!rent  &&  <input type="radio" id='changeTable' name='selectTable' onChange={handlerent}></input>}
          <label for="changeClassTable">租屋提醒</label>
        </div><br/>
           <ChangeClassCategorySelect onChange={handleFunctionSelect}>
              <option >功能選單</option>
              <option value='selectArticle'>我的提醒</option>
              <option value='setRentNotification'>設置租屋提醒</option>
              <option value='setCCNotification'>設置換課提醒</option>
          </ChangeClassCategorySelect>
         </div>
          }
          {!openRentModal && !openCCModal && cc && data.length>0 && <AllMyNotification myNotificationData={data}/>}
          {!openRentModal && !openCCModal && rent  && <AllMyRentNotification myRentNotificationData={rent_data}/>}
          {openRentModal && <IsModal closeModal={setOpenRentModal} type={"setRentNotification"}/>}
          {openCCModal && <IsModal closeModal={setOpenCCModal} type={"setChangeClassNotification"}  />}
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
