import './Modal.css';
import React from "react";
import sandwich from '../img/sandwich.png';
import pizza from '../img/pizza.png';
import taco from '../img/taco.png';
import cookiem from '../img/cookiem.png';
import cookiemonster from '../img/cookiemonster.png';
import minion from '../img/minion.png';
import banana from '../img/banana.png';
import duck from '../img/duck.PNG';
import cuteDogImg from "../img/cutedoggy.PNG";
import {ArticleSubmitBtn, ModalSubmitBtn, ModalNotificationSubmitBtn,ChangeClassStatusSelect} from './ArticleStyle.js';
import {ButtonContainer,ArticleDetailNormalBtn}  from './ArticleDetailStyle.js';
import {useState,useEffect} from "react";
import { Routes ,Route, useNavigate, useLocation } from 'react-router-dom';
import { loginUser } from '../cookie';
import { useCookies } from 'react-cookie';
import { getAuthToken } from "../utils";






function RatingFood(){
    return(
        <div>
            <img src={duck} className='modal_duckpic'/>
        </div>

    );
}

function ChooseArticle(){
    return(
        <div>
            <img src={cuteDogImg} className='modal_dogpic'/>
        </div>

    );
}




function Modal({closeModal, type, postId, comment, alreadyComment, studentID, time, rating, ArticleAS}){

    let navigate = useNavigate();
    const [isModalFood, setisModalFood] = useState(true);
    const [isModalRent, setIsModalRent] = useState(false);
    const [isModalChangeClass, setisModalChangeClass] = useState(false);
    const [isModalRentNotification, setisModalRentNotification] = useState(false);
    const [isModalChangeClassNotification, setisModalChangeClassNotification] = useState(false);
    const [isModalChangeClassArticle, setisModalChangeClassArticle] = useState(false);
    const [isRating, setisRating] = useState(false);
    const [isPostId, setisPostId] = useState(false);
    const [isComment, setisComment] = useState(false);
    const [isMeRating, setisMeRating] = useState(false);
    const [isAlreadyComment, setIsAlreadyComment] = useState(false);
    const [confirm, setConfirm] = useState(false);
    const [noData, setNoData] = useState(false);
    const token = getAuthToken();
    const userInfo = loginUser();

    function ModalRent(){

        const [RSArea, setRSArea] = useState("");
        const [RSGender, setRSGender] = useState("");
        const [RSPeople, setRSPeople] = useState("");
        const [RSType, setRSType] = useState("");
        const [RSCar, setRSCar] = useState("");
        const handleRSAreaChange = event => {
            setRSArea(event.target.value);
          };
          const handleRSGenderChange = event => {
            setRSGender(event.target.value);
          };
          const handleRSPeopleChange = event => {
            setRSPeople(event.target.value);
          };
          const handleRSTypeChange = event => {
            setRSType(event.target.value);
          };
          const handleRSCarChange = event => {
            setRSCar(event.target.value);
          };
          
          const handleSearchRentSubmit = (e) => {
            e.preventDefault();
            //const student_id = loginUser();
                navigate("/rent", {
                  state: {
                    fromSearch:true,
                    RSArea:RSArea,
                    RSGender:RSGender,
                    RSPeople:RSPeople,
                    RSType:RSType,
                    RSCar:RSCar,},});
                    //closeModal(false);
                    window.location.reload();
          }

        return(
                <form onSubmit={handleSearchRentSubmit}>
                    <h2 className='modal_tilte'>篩選貼文</h2>
                    <div className='modalBodyText'>
                        <div className='ModalRentFormRegion'>
                            <label>地區: </label>
                            <select value={RSArea} onChange={handleRSAreaChange}>
                                <option>請選擇區域</option>
                                <option value='中正區'>中正區</option>
                                <option value='信義區'>信義區</option>
                                <option value='仁愛區'>仁愛區</option>
                                <option value='中山區'>中山區</option>
                                <option value='安樂區'>安樂區</option>
                                <option value='暖暖區'>暖暖區</option>
                                <option value='七堵區'>七堵區</option>
                            </select>
                        </div><br/>
                        <div className='ModalRentFormGender'>
                            <label>性別:</label>
                            <input type="radio" id='girl' name='RSrentGender' value='女' checked={RSGender === '女'} onChange={handleRSGenderChange}></input>
                            <label for="girl">女</label>
                            <input type="radio" id='boy' name='RSrentGender' value='男' checked={RSGender === '男'} onChange={handleRSGenderChange}></input>
                            <label for="boy">男</label>
                            <input type="radio" id='both' name='RSrentGender' value='不限' checked={RSGender === '不限'} onChange={handleRSGenderChange}></input>
                            <label for="both">無限制</label>
                        </div><br/>
                        <div className='ModalRentFormPeople'>
                            <label>人數: </label>
                            <select value={RSPeople} onChange={handleRSPeopleChange}>
                                <option>請選擇人數</option>
                                <option value='one'>1</option>
                                <option value='two'>2</option>
                                <option value='three'>3</option>
                                <option value='four'>4</option>
                            </select>
                        </div><br/>
                        <div className='ModalRentFormType'>
                        <label>房型: </label>
                            <select value={RSType} onChange={handleRSTypeChange}>
                                <option>請選擇房型</option>
                                <option value='studio'>套房</option>
                                <option value='room'>雅房</option>
                                <option value='family'>家庭式</option>
                            </select>
                        </div><br/>
                        <div className='ModalRentFormCar'>
                            <label>車位:</label>
                            <input type="radio" id='yes' value='有' checked={RSCar === '有'} onChange={handleRSCarChange}></input>
                            <label for="yes">有</label>
                            <input type="radio" id='no' value='無' checked={RSCar === '無'} onChange={handleRSCarChange}></input>
                            <label for="no">無</label>
                        </div><br/>
                    </div>
                    <ModalSubmitBtn type="submit">確認</ModalSubmitBtn>
                </form>
        );
    }
    

    function ModalFood(){
        let navigate = useNavigate();
        const [FSArea, setFSArea] = useState("");
        const [FSName, setFSName] = useState("");
        const [FSAddress, setFSAddress] = useState("");
        const handleFSAreaChange = event => {
            setFSArea(event.target.value);
          };
          const handleFSNameChange = event => {
            setFSName(event.target.value);
          };
          const handleFSAddressChange = event => {
            setFSAddress(event.target.value);
          };
        const handleSearchFoodSubmit = (e) => {
            e.preventDefault();
            //const student_id = loginUser();
            const formData = {
                            area: FSArea,
                            store : FSName,
                          };
              
                navigate("/food", {
                  state: {
                    fromSearch:true,
                    FArea:FSArea,
                    FName:FSName,
                    FAddr:FSAddress,},});
                    //closeModal(false);
                    window.location.reload();
          }
        return(
                <form onSubmit={handleSearchFoodSubmit}>
                    <h2 className='modal_tilte'>篩選貼文</h2>
                    <div className='modalBodyText'>
                        <div className='ModalFoodFormRegion'>
                            <label>地區: </label>
                            <select value={FSArea} onChange={handleFSAreaChange}>
                                <option>請選擇區域</option>
                                <option value='中正區'>中正區</option>
                                <option value='信義區'>信義區</option>
                                <option value='仁愛區'>仁愛區</option>                                    
                                <option value='中山區'>中山區</option>
                                <option value='安樂區'>安樂區</option>
                                <option value='暖暖區'>暖暖區</option>
                                <option value='七堵區'>七堵區</option>
                            </select>
                        </div><br/>
                        <div className='ModalFoodStore'>
                            <label>店家:</label>
                            <input className='ModalFoodStoreText' type="text" onChange={handleFSNameChange}></input>
                        </div><br/>
                        <div className='ModalFoodAddress'>
                            <label>地址:</label>
                            <input className='ModalFoodAddressText' type="text" onChange={handleFSAddressChange}></input>
                        </div><br/>
                    </div>
                    <ModalSubmitBtn type="submit">確認</ModalSubmitBtn>
                </form>
        );
    }
  

    function ModalChangeClassArticle(){
      let navigate = useNavigate();
      //const { studentID, postId, time } = location.state;
      const [data, setData] = useState("");
      const [timeValue, setTimeValue] = useState("");
      const [isCreator, setIsCreator] = useState(false);
      const location = useLocation();
      const formData = {
        studentID:  userInfo,
        postId : postId,
      };

      console.log("modal is: "+ time);

      useEffect(() => {
        if (!data) {
            fetch('/course_full_post', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(formData)})
            .then(response => response.json())
            .then(data => {
              if(data.studentID==userInfo){
                console.log("same");
                setIsCreator(true);
              }
              else{
                setIsCreator(false);
                
              }
              setData(data);
              console.log(data);
            })
            .catch(error => {
              console.error('Error:', error);
            });
        }
      }, [data]); // 添加依賴項data

      if (!data) {
        return <div>Loading...</div>;
      }

      const handleSearchClassSubmit = (e) => {
          e.preventDefault();
          //const student_id = loginUser();
          const formData = {
                          studentID: studentID,
                          postId : postId,
                        };
            
              navigate("/changeClassList", {
                state: {
                  studentID: userInfo,
                        time:time,},});
                  //closeModal(false);
                  window.location.reload();
        }

        const handleModifyClassPostSubmit = (e) => {
          e.preventDefault();
          //const student_id = loginUser();
          const formData = {
                          studentID: studentID,
                          postId : postId,
                        };
            
                        navigate("/modifyPost", {
                          state: {
                            studentID:userInfo,
                            postId:postId,
                            fromSearch:false,
                            ModifyType:"changeClass",
                            timeValue:time,
                             },});
        }

        const handleRemovedClassPostConfirmSubmit = (e) => {
          e.preventDefault();
          //const student_id = loginUser();
          setConfirm(true);
          
        
        }


      return(
             <div><br/>
              <label>發文者:&ensp;{data.studentID}</label><br/>
              <label>標題:&ensp;{data.course}</label><br/>
              <label>課程分類:&ensp;{data.category}</label><br/>
              <label>課程老師:&ensp;{data.teacher}</label><br/>
              <label>課程時間:&ensp;{data.time.join('、')}</label><br/>
              <label>內容:&ensp;{data.content}</label><br/><br/>
              {isCreator===false && <ModalSubmitBtn type="submit">聯絡發文者</ModalSubmitBtn>}
              {isCreator && confirm && (<ButtonContainer><ArticleDetailNormalBtn onClick={handleModifyClassPostSubmit} disabled>修改貼文</ArticleDetailNormalBtn>
                    <ArticleDetailNormalBtn onClick={handleRemovedClassPostConfirmSubmit} disabled>刪除貼文</ArticleDetailNormalBtn>
                </ButtonContainer>)}
                {isCreator && confirm===false &&(<ButtonContainer><ArticleDetailNormalBtn onClick={handleModifyClassPostSubmit}>修改貼文</ArticleDetailNormalBtn>
                    <ArticleDetailNormalBtn onClick={handleRemovedClassPostConfirmSubmit}>刪除貼文</ArticleDetailNormalBtn>
                </ButtonContainer>)}<br/>

              
             </div>
      );
  }
    
  
    useEffect(() => {
      if (type === "rent") {
        setisModalFood(false);
        setIsModalRent(true);
        setisModalChangeClass(false);
        setisRating(false);
        setisModalChangeClassArticle(false);
        setisModalChangeClassNotification(false);
        setisModalRentNotification(false);
      } else if (type === "food") {
        setisModalFood(true);
        setIsModalRent(false);
        setisModalChangeClass(false);
        setisRating(false);
        setisModalChangeClassArticle(false);
        setisModalChangeClassNotification(false);
        setisModalRentNotification(false);
      } else if (type === "changeClass") {
        setisModalFood(false);
        setIsModalRent(false);
        setisModalChangeClass(true);
        setisRating(false);
        setisModalChangeClassArticle(false);
        setisModalChangeClassNotification(false);
        setisModalRentNotification(false);
      }
      else if (type === "rating") {
        setisModalFood(false);
        setIsModalRent(false);
        setisModalChangeClass(false);
        setisRating(true);
        setisModalChangeClassArticle(false);
        setisModalChangeClassNotification(false);
        setisModalRentNotification(false);
        setisPostId(postId)
        setisComment(comment);
        setIsAlreadyComment(alreadyComment);
      }
      else if (type === "classArticle") {
        setisModalFood(false);
        setIsModalRent(false);
        setisModalChangeClass(false);
        setisRating(false);
        setisModalChangeClassArticle(true);
        setisModalChangeClassNotification(false);
        setisModalRentNotification(false);
        setisPostId(postId)
        //setisComment(comment);
        //setIsAlreadyComment(alreadyComment);
      }
      else if (type === "setChangeClassNotification") {
        setisModalFood(false);
        setIsModalRent(false);
        setisModalChangeClass(false);
        setisRating(false);
        setisModalChangeClassArticle(false);
        setisModalChangeClassNotification(true);
        setisModalRentNotification(false);
      }
      else if (type === "setRentNotification") {
        setisModalFood(false);
        setIsModalRent(false);
        setisModalChangeClass(false);
        setisRating(false);
        setisModalChangeClassArticle(false);
        setisModalChangeClassNotification(false);
        setisModalRentNotification(true);
      }
    }, [type]);

    function ModalRating(){
        const [Frating, setFrating] = useState("");
        const handleFratingChange = event => {
            setFrating(event.target.value);
        };
    
        const handleRatingSubmit = (e) => {
            e.preventDefault();
            //const student_id = loginUser();
            
                        if(isAlreadyComment===true){
                            const formData = {
                                postId:isPostId,
                                studentID: userInfo,
                                p_review : isComment,
                                p_rate : Frating,
                              };
                              fetch('/food_review_modify', {
                                method: 'PUT',
                                headers: {
                                  'Content-Type': 'application/json',
                                  'Authorization': `${token}`
                                },
                                body: JSON.stringify(formData)
                              })
                              .then(response => response.json())
                              .then(data => {
                                console.log(data);
                              })
                              .catch(error => {
                                console.error(error);
                              });
                        }
                        else{
                            const formData = {
                                postId:isPostId,
                                studentID: userInfo,
                                p_review : "尚未發表評論",
                                p_rate : Frating,
                              };
                            fetch('/food_review_add', {
                                method: 'POST',
                                headers: {
                                  'Content-Type': 'application/json',
                                  'Authorization': `${token}`
                                },
                                body: JSON.stringify(formData)
                              })
                              .then(response => response.json())
                              .then(data => {
                                console.log(data);
                              })
                              .catch(error => {
                                console.error(error);
                              });
                        }
                              window.location.reload();
                              navigate("/foodArticle", {
                                state: {
                                  postId,postId,
                                  fromSearch:false,
                                  ArticleAS:ArticleAS,},});
                                  
                              
                           //Form submission happens here
          }
    
        return(
                <form onSubmit={handleRatingSubmit}>
                    <h2 className='modal_tilte'>評分</h2>
                    <div className='modalBodyText'>
                    <div className='ModalFoodRatingArea'>
                        <label>評分一下吧:</label><br/>
                        <input type="radio" id='one' name='Frating' value='1' checked={Frating === '1'} onChange={handleFratingChange}></input>
                            <label for="1">1</label>
                            <input type="radio" id='two' name='Frating' value='2' checked={Frating === '2'} onChange={handleFratingChange}></input>
                            <label for="2">2</label>
                            <input type="radio" id='three' name='Frating' value='3' checked={Frating === '3'} onChange={handleFratingChange}></input>
                            <label for="3">3</label>
                            <input type="radio" id='three' name='Frating' value='4' checked={Frating === '4'} onChange={handleFratingChange}></input>
                            <label for="4">4</label>
                            <input type="radio" id='three' name='Frating' value='5' checked={Frating === '5'} onChange={handleFratingChange}></input>
                            <label for="5">5</label>
                        
                        </div><br/>
                    </div>
                    <ModalSubmitBtn type='submit'>確認</ModalSubmitBtn>
                </form>
        );
    
    }

    function SetChangeClassNotification(){
        const [classNum, setClassNum] = useState("");
        const [time, setTime] = useState([]);
        const [typeTime, setTypeTime] = useState(false);
        const [classNumber, setClassNumber] = useState(true);
        const [selectedType, setSelectedType] = useState([]);

        const handleClassNotificationSubmit = (e) => {
          e.preventDefault();
          //const student_id = loginUser();
          const formData = {
            studentID: userInfo,
            calssNum : "123456",
            time : "45",
            type : selectedType,
          };
                        fetch('/exchange_notification_add', {
                              method: 'POST',
                              headers: {
                                'Content-Type': 'application/json'
                              },
                              body: JSON.stringify(formData)
                            })
                            .then(response => response.json())
                            .then(data => {
                              console.log(data);
                            })
                            .catch(error => {
                              console.error(error);
                            });
                            navigate("/changeClass", {
                              state: {
                                fromSearch:false,},});
                                window.location.reload();
                         //Form submission happens here
        }

        const handleTypeChange = event => {
          const valueType = event.target.value;
          if (event.target.checked) {
            setSelectedType([...selectedType, valueType]);
          }else {
            setSelectedType(selectedType.filter(item => item !== valueType));
          }
          console.log(selectedType);
        };

        const handleClassNum = event => {
          setClassNum(event.target.value);
        };

        const handleTime = event => {
          const timeArray = event.target.value.split("、");
          setTime(timeArray);
        };

        const handleClassNumChange = event => {
          console.log("innum");
          setClassNumber(true);
          setTypeTime(false);
        };

        const handleTimeTypeChange = event => {
          setClassNumber(false);
          setTypeTime(true);
        };


        function TimeTypeNotification(){
          return(
            <div>
                  <label>分類:</label>
                    <input type="checkbox" id='required' value='必修' name='selectedType[]' onChange={handleTypeChange} ></input>
                    <label for="required">必修</label>
                    <input type="checkbox" id='optional' value='選修' name='selectedType[]' onChange={handleTypeChange}></input>
                    <label for="optional">選修</label>
                    <input type="checkbox" id='general' value='通識' name='selectedType[]' onChange={handleTypeChange}></input>
                    <label for="general">通識</label><br/>
                    <input type="checkbox" id='language' value='第二外語' name='selectedType[]' onChange={handleTypeChange} ></input>
                    <label for="language">第二外語</label>
                    <input type="checkbox" id='eng' value='英文' name='selectedType[]' onChange={handleTypeChange}></input>
                    <label for="eng">英文</label>
                    <input type="checkbox" id='pe' value='體育' name='selectedType[]' onChange={handleTypeChange}></input>
                    <label for="pe">體育</label>
                  
                    <div>
                    <label>時間:&emsp;</label>
                    <input type='text' className='changeClassNotificationFormTimeInput' value={time} onChange={handleTime}></input>
                    </div>
            </div>
          );
        }

        function ClassNumNotification(){
          return(
              <div>
                  <label>課號:&emsp;</label>
                  <input type='text' className='changeClassNotificationFormTimeInput' value={classNum} onChange={handleClassNum}></input>
              </div>
          );
        }

         return(
            <form className='classNotificationForm' onSubmit={handleClassNotificationSubmit}>
                <h2 className='modal_tilte'>設置提醒</h2>
                <div className='modalChangeClassNotificationSelect'>
                  {classNumber && <input type="radio" id='myclass' name='selectTable' onChange={handleClassNumChange} checked></input>}
                  {!classNumber &&  <input type="radio" id='myclass' name='selectTable'  onChange={handleClassNumChange}></input>}
                  <label for="myclass">課號設定</label>

                  {typeTime &&  <input type="radio" id='changeTable' name='selectTable' onChange={handleTimeTypeChange} checked></input>}
                  {!typeTime &&  <input type="radio" id='changeTable' name='selectTable' onChange={handleTimeTypeChange}></input>}
                  <label for="changeClassTable">時間分類設定</label>
                </div><br/>

                <div className='modalBodyText'>
                  {typeTime && <TimeTypeNotification/>}
                  {classNumber && <ClassNumNotification/>}
                </div>
                <ModalSubmitBtn type='submit'>確認</ModalSubmitBtn>
            </form>
         );
      }


    function SetRentNotification(){
        const [Haddress, setHaddress] = useState("");
        const [Hmoney, setHmoney] = useState("");
        const [rentGender, setrentGender] = useState("");
        const [Hpeople, setHpeople] = useState("");
        const [Htype, setHtype] = useState("");
        const [Harea, setHarea] = useState("");
        const [haveCar, sethaveCar] = useState("");
        const [Hwater, setHwater] = useState("");
        const [HwaterMoney, setHwaterMoney] = useState("");
        const [Hpower, setHpower] = useState("");
        const [HpowerMoney, setHpowerMoney] = useState("");
        const [Hnote, setHnote] = useState("");
        const [Hfloor, setHfloor] = useState("");
        const [rent_date, setrent_date] = useState("");
        const [selectedArea, setSelectedArea] = useState("");
        const [selectedType, setSelectedType] = useState([]);
        const [selectedHpower, setSelectedHpower] = useState([]);
        const [selectedHwater, setSelectedHwater] = useState([]);

        const handleHmoneyChange = event => {
          setHmoney(event.target.value);
        };
        const handlerentGenderChange = event => {
          setrentGender(event.target.value);
        };
        const handleHpeopleChange = event => {
          setHpeople(event.target.value);
        };
        const handleHtypeChange = event => {
          const valueType = event.target.value;
          if (event.target.checked) {
            setSelectedType([...selectedType, valueType]);
          }else {
            setSelectedType(selectedType.filter(item => item !== valueType));
          }
          console.log(selectedType);
        };
        const handleHareaChange = event => {
          const valueArea = event.target.value;
          if (event.target.checked) {
            setSelectedArea([...selectedArea, valueArea]);
          }else {
            setSelectedArea(selectedArea.filter(item => item !== valueArea));
          }
          console.log(selectedArea);
        };
        const handlehaveCarChange = event => {
          sethaveCar(event.target.value);
        };
        const handleHwaterChange = event => {
          console.log(HwaterMoney);
          const valueWater = event.target.value;
          if (event.target.checked) {
              setSelectedHwater([...selectedHwater, valueWater]);
          }else {
            setSelectedHwater(selectedHwater.filter(item => item !== valueWater));
            }
            console.log(selectedHwater);
          
        };
        const handleHwaterMoneyChange = event => {
          setHwaterMoney(event.target.value);
          console.log(event.target.value);
          
        };
        const handleHpowerChange = event => {
          console.log(event.target.value);
          const value = event.target.value;
            if (event.target.checked) {
              setSelectedHpower([...selectedHpower, value]);
            } else {
              setSelectedHpower(selectedHpower.filter(item => item !== value));
            }
            console.log(selectedHpower);
        };
        const handleHpowerMoneyChange = event => {
          setHpowerMoney(event.target.value);
          console.log(event.target.value);
          
        };
        const handleHfloorChange = event => {
          setHfloor(event.target.value);
        };
       
        const handleRentNotificationSubmit = (e) => {
          e.preventDefault();
          //const student_id = loginUser();

       
          const formData = {
                          studentID: userInfo,
                          money : Hmoney,
                          people : Hpeople,
                          address : Haddress,
                          area : selectedArea,
                          gender : rentGender,
                          style : selectedType,
                          water : selectedHwater,
                          power : selectedHpower,
                          car : haveCar,
                          floor : Hfloor,
                          HwaterMoney: HwaterMoney,
                          HpowerMoney: HpowerMoney,
                        };
                        fetch('/rent_notification_add', {
                              method: 'POST',
                              headers: {
                                'Content-Type': 'application/json'
                              },
                              body: JSON.stringify(formData)
                            })
                            .then(response => response.json())
                            .then(data => {
                              console.log(data);
                            })
                            .catch(error => {
                              console.error(error);
                            });
                            navigate("/rent", {
                              state: {
                                fromSearch:false,},});
                                window.location.reload();
                         //Form submission happens here
        }

      return(
        <form className='RentNotificationForm' onSubmit={handleRentNotificationSubmit}>
            <h2 className='modal_tilte'>設置提醒</h2>
            <div className='modalBodyText'>
            <div className='rentNotificationInput'>
              <label>租金:&ensp;</label>
              <input type='text' name = 'Hmoney' onChange={handleHmoneyChange} value={Hmoney} placeholder='可接受上限'></input> 
            </div>
            <div className='rentNotificationInput'>
            <label >性別:</label>
            <select name = 'rentGender' className='modalNotificationRentFormSelect' value={rentGender} onChange={handlerentGenderChange}>
                  <option>請選擇</option>
                  <option value='女'>女</option>
                  <option value='男'>男</option>
                  <option value='無限制'>無限制</option>
            </select> 
            </div>
            <div className='rentNotificationInput'>
              <label>人數:&ensp;</label>
              <input type='text' name = 'Hpeople' onChange={handleHpeopleChange} value={Hpeople}></input>
            </div>
            <div className='rentNotificationInputCheckBox'>
            <label>房型:</label>
              <input type="checkbox" id='suite' value='套房' name='selectedType[]' onChange={handleHtypeChange} ></input>
              <label for="suite">套房</label>
              <input type="checkbox" id='room' value='雅房' name='selectedType[]' onChange={handleHtypeChange}></input>
              <label for="room">雅房</label>
              <input type="checkbox" id='family' value='家庭式' name='selectedType[]' onChange={handleHtypeChange}></input>
              <label for="family">家庭式</label>
            </div>
            <div className='rentNotificationInputAreaCheckBox'>
            <label>地區:</label><br/>
            <input type="checkbox" id='Zhong' value='中正區' name='selectedType[]' onChange={handleHareaChange} ></input>
              <label for="Zhong">中正區</label>
              <input type="checkbox" id='Xing' value='信義區' name='selectedType[]' onChange={handleHareaChange}></input>
              <label for="Xing">信義區</label>
              <input type="checkbox" id='Ren' value='仁愛區' name='selectedType[]' onChange={handleHareaChange}></input>
              <label for="Ren">仁愛區</label><br/>
              <input type="checkbox" id='Zhongshan' value='中山區' name='selectedType[]' onChange={handleHareaChange} ></input>
              <label for="Zhongshan">中山區</label>
              <input type="checkbox" id='Anle' value='安樂區' name='selectedType[]' onChange={handleHareaChange}></input>
              <label for="Anle">安樂區</label>
              <input type="checkbox" id='warm' value='暖暖區' name='selectedType[]' onChange={handleHareaChange}></input>
              <label for="warm">暖暖區</label><br/>
              <input type="checkbox" id='Qidu' value='七堵區' name='selectedType[]' onChange={handleHareaChange}></input>
              <label for="Qidu">七堵區</label>
            </div>
            <div className='rentNotificationInput'>
              <label>樓層:&ensp;</label>
              <input type='text' name = 'Hfloor' onChange={handleHfloorChange} value={Hfloor} placeholder='可接受最高樓層'></input> <br/>
            </div>
            <div className='rentNotificationInput'>
            <label>車位:</label>
            <select name = 'haveCar' className='modalNotificationRentFormSelect' value={haveCar} onChange={handlehaveCarChange}>
                  <option>請選擇</option>
                  <option value='有'>有</option>
                  <option value='無'>無</option>
            </select> 
            </div>
            <div>
            <label >水費:</label>
              <input type="checkbox" id='waterOfficial' value='台水' name='selectedHwater[]' onChange={handleHwaterChange} ></input>
              <label for="waterOfficial">台水</label>
              <input type="checkbox" id='waterLandlord' value='含房租內' name='selectedHwater[]' onChange={handleHwaterChange}></input>
              <label for="waterLandlord">含房租內</label>
              <input type="checkbox" id='waterOne' value='月繳' name='selectedHwater[]' onChange={handleHwaterChange}></input>
              <label for="waterOne">月繳<input type='text' className='modal_waterMoney' value={HwaterMoney} onChange={handleHwaterMoneyChange}></input>元</label>
            </div>

            <div>
              <label>電費:</label>
              <input type="checkbox" id='powerOfficial' value='台電' name='rentPower[]' onChange={handleHpowerChange} ></input>
              <label for="powerOfficial">台電</label>
              <input type="checkbox" id='powerLandlord' value='含房租內' name='rentPower[]' onChange={handleHpowerChange}></input>
              <label for="powerLandlord">含房租內</label>
              <input type="checkbox" id='powerOne' value='一度' name='rentPower[]' onChange={handleHpowerChange}></input>
              <label for="powerOne">一度<input type='text' className='modal_powerMoney' value={HpowerMoney} onChange={handleHpowerMoneyChange}></input>元</label>
            </div>
              
            <ModalNotificationSubmitBtn type="submit">確認</ModalNotificationSubmitBtn>
            </div>
        </form>
      );
    }

    function ConfirmDelete(){

      const handleRemovedClassPostSubmit = (e) => {
        e.preventDefault();
        //const student_id = loginUser();
        
        const formData = {
                        studentID: userInfo,
                        postId : postId,
                      };
                      fetch(`/course_post_delete`, {
                        method: 'DELETE',
                        headers: {
                          'Content-Type': 'application/json',
                          'Authorization': `${token}`
                        },
                        body: JSON.stringify(formData)
                      })
                      .then(response => response.status)
                      .catch(error => {
                        console.error(error);
                      });
                      navigate("/changeClassList", {
                        state: {
                          studentID: userInfo,
                          time:time,},});
                window.location.reload();
                
      }

      const handleCancelRemovedClassPostSubmit = (e) => {
        e.preventDefault();

        setConfirm(false);
                
      }
      
      return(
              <div className='deleteModal'>
                <label className='deleteText'>確定要刪除嗎!!</label>
                <button className='modal_yes' onClick={handleRemovedClassPostSubmit}>確定</button>
                <button className='modal_no' onClick={handleCancelRemovedClassPostSubmit}>取消</button>
              </div>
      );
  }

    
    

    return(
        <div className="modalBackground">
            {isRating && <RatingFood/>}
            {!isRating && <ChooseArticle/>}
            <div className="modalContainer">
                <button className='modalClose' onClick={() => closeModal(false)}>X</button>
                <div className="modalTitle">
                    <div className="modalBody">
                        {isModalFood && <ModalFood/>}
                        {isModalRent && <ModalRent/>}
                        {isModalRentNotification && <SetRentNotification/>}
                        {isModalChangeClassNotification && <SetChangeClassNotification/>}
                        {isRating && <ModalRating/>}
                        {isModalChangeClassArticle && <ModalChangeClassArticle/>}
                        {confirm && <ConfirmDelete/>}
                    </div>
                </div>
            </div>
        </div>

    );
}

export default Modal;