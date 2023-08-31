import './PostArticle.css';
import React from 'react';
import elmo from './img/elmo.png';
import conversation from './img/conversation.png';
import back from './img/back.png';
import {Back}  from './components/Style.js';
import {Title}  from './components/ArticleStyle.js';
import {ArticleSubmitBtn, ArticleSubmitBtnPosition}  from './components/ArticleStyle.js';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route, useNavigate, useLocation } from 'react-router-dom';
import {useEffect,useState} from "react";
import { loginUser } from './cookie';
import { getAuthToken } from "./utils";

const ModifyPost=()=> {
    let navigate = useNavigate();
    const location = useLocation();
    const [data, setData] = useState(null);
    const { studentID, postId, fromSearch, ModifyType, timeValue, ArticleAS } = location.state;
    const userInfo = loginUser();
    const token = getAuthToken();
    

    function ArticleModifyFoodInput({ newData }) {

        const [Ftitle, setFtitle] = useState("");
        const [Ftime, setFtime] = useState("");
        const [FtimeMonday, setFtimeMonday] = useState("");
        const [FtimeTuesday, setFtimeTuesday] = useState("");
        const [FtimeWednesday, setFtimeWednesday] = useState("");
        const [FtimeThursday, setFtimeThursday] = useState("");
        const [FtimeFriday, setFtimeFriday] = useState("");
        const [FtimeSaturday, setFtimeSaturday] = useState("");
        const [FtimeSunday, setFtimeSunday] = useState("");
        const [Faddress, setFaddress] = useState("");
        const [Farea, setFarea] = useState("");
        const [Froad, setFroad] = useState("");
        const [Frate, setFrate] = useState("");
        const [FLink, setFLink] = useState("");


        useEffect(() => {
            if (newData) {
              setFtitle(newData.store);
              console.log(newData.weekday_text[0].replace("星期一:", ""));
              setFtimeMonday(newData.weekday_text[0].replace("星期一:", ""));
              setFtimeTuesday(newData.weekday_text[1].replace("星期二:", ""));
              setFtimeWednesday(newData.weekday_text[2].replace("星期三:", ""));
              setFtimeThursday(newData.weekday_text[3].replace("星期四:", ""));
              setFtimeFriday(newData.weekday_text[4].replace("星期五:", ""));
              setFtimeSaturday(newData.weekday_text[5].replace("星期六:", ""));
              setFtimeSunday(newData.weekday_text[6].replace("星期日:", ""));
              setFaddress(newData.address);
              setFarea(newData.district);
              setFroad(newData.road);
              setFrate(newData.rating);
              setFLink(newData.url);
            }
          }, [newData]);


        const openTime = ["星期一:"+FtimeMonday,"星期二:"+FtimeTuesday,"星期三:"+FtimeWednesday,"星期四:"+FtimeThursday,"星期五:"+FtimeFriday,"星期六:"+FtimeSaturday,"星期日:"+FtimeSunday];

        const handleFtitleChange = event => {
          setFtitle(event.target.value);
        };
        const handleFtimeChange = event => {
          setFtime(event.target.value);
        };
        const handleFtimeMondayChange = event => {
          setFtimeMonday(event.target.value);
        };
        const handleFtimeTuesdayChange = event => {
          setFtimeTuesday(event.target.value);
        };
        const handleFtimeWednesdayChange = event => {
          setFtimeWednesday(event.target.value);
        };
        const handleFtimeThursdayChange = event => {
          setFtimeThursday(event.target.value);
        };
        const handleFtimeFridayChange = event => {
          setFtimeFriday(event.target.value);
        };
        const handleFtimeSaturdayChange = event => {
          setFtimeSaturday(event.target.value);
        };
        const handleFtimeSundayChange = event => {
          setFtimeSunday(event.target.value);
        };

        const handleFaddressChange = event => {
          setFaddress(event.target.value);
        };

        const handleFareaChange = event => {
          setFarea(event.target.value);
        };

        const handleFroadChange = event => {
          setFroad(event.target.value);
        };

        const handleFLinkChange = event => {
          setFLink(event.target.value);
        };

        const handleFrateChange = event => {
          setFrate(event.target.value);
        };
        

        const handleFoodSubmit = (e) => {
            e.preventDefault();
            const student_id = loginUser();
            const formData = {
              postId: postId,
              studentID: userInfo,
              store: Ftitle,
              weekday_text: openTime,
              address: Faddress,
              road : Froad,
              url : FLink,
              district : Farea,
              
            };
            fetch('/food_modify', {
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
                navigate("/foodArticle", {
                  state: {
                    fromSearch: false,
                    postId: postId,
                    ArticleAS:ArticleAS,
                  }
                });
              })
              .catch(error => {
                console.error(error);
              });
          }
          

        return (
          <form className='articleFoodForm' onSubmit={handleFoodSubmit}>
            <div className='articleFoodFormTitle'>
              <label>標題:&emsp;&emsp;&emsp;</label>
              <input type='text' name = 'Ftitle' onChange={handleFtitleChange} value={Ftitle}></input>
            </div><br/>
            <div className='articleFoodFormTime'>
              <label>營業時間&emsp;</label><br/>
              <label>&emsp;星期一:&emsp;</label>
              <input type='text' name = 'FtimeMonday' onChange={handleFtimeMondayChange} value={FtimeMonday}></input><br/>
              <label>&emsp;星期二:&emsp;</label>
              <input type='text' name = 'FtimeTuesday' onChange={handleFtimeTuesdayChange} value={FtimeTuesday}></input><br/>
              <label>&emsp;星期三:&emsp;</label>
              <input type='text' name = 'FtimeWednesday' onChange={handleFtimeWednesdayChange} value={FtimeWednesday}></input><br/>
              <label>&emsp;星期四:&emsp;</label>
              <input type='text' name = 'FtimeThursday' onChange={handleFtimeThursdayChange} value={FtimeThursday}></input><br/>
              <label>&emsp;星期五:&emsp;</label>
              <input type='text' name = 'FtimeFriday' onChange={handleFtimeFridayChange} value={FtimeFriday}></input><br/>
              <label>&emsp;星期六:&emsp;</label>
              <input type='text' name = 'FtimeSaturday' onChange={handleFtimeSaturdayChange} value={FtimeSaturday}></input><br/>
              <label>&emsp;星期日:&emsp;</label>
              <input type='text' name = 'FtimeSunday' onChange={handleFtimeSundayChange} value={FtimeSunday}></input>
            </div><br/>
            <div className='articleFoodFormArea'>
            <label>店家地區:&emsp;</label>
              <select name = 'Farea' className='articleFoodFormSelect' value={Farea} onChange={handleFareaChange}>
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
            <div className='articleFoodFormAddress'>
              <label>店家路名:&emsp;</label>
              <input type='text' name = 'Faddress' onChange={handleFroadChange} value={Froad} ></input>
            </div><br/>
            <div className='articleFoodFormAddress'>
              <label>店家地址:&emsp;</label>
              <input type='text' name = 'Faddress' onChange={handleFaddressChange} value={Faddress}></input>
            </div><br/>
            <div className='articleFoodFormAddress'>
              <label>店家連結:&emsp;</label>
              <input type='text' name = 'FLink' onChange={handleFLinkChange} value={FLink}></input>
            </div><br/>
              <ArticleSubmitBtnPosition>
                <ArticleSubmitBtn type="submit">確認修改</ArticleSubmitBtn>
              </ArticleSubmitBtnPosition>
          </form>  
        );
      }

      function ArticleModifyRentInput({ newData }) {
        const [Htitle, setHtitle] = useState("");
        const [Haddress, setHaddress] = useState("");
        const [Hmoney, setHmoney] = useState("");
        const [rentGender, setrentGender] = useState("");
        const [Hpeople, setHpeople] = useState("");
        const [Htype, setHtype] = useState("");
        const [Harea, setHarea] = useState("");
        const [haveCar, sethaveCar] = useState("");
        const [Hwater, setHwater] = useState("");
        const [newHwater, setNewHwater] = useState("");
        const [HwaterMoney, setHwaterMoney] = useState("");
        const [Hpower, setHpower] = useState("");
        const [newHpower, setNewHpower] = useState("");
        const [HpowerMoney, setHpowerMoney] = useState("");
        const [Hnote, setHnote] = useState("");
        const [Hfloor, setHfloor] = useState("");
        const [rent_date, setrent_date] = useState("");

        useEffect(() => {
            if (newData) {
              setHtitle(newData.title);
              setHarea(newData.area);
              setHaddress(newData.address);
              setHmoney(newData.money);
              setrentGender(newData.gender);
              setHpeople(newData.people);
              setHtype(newData.style);
              sethaveCar(newData.car);
              setHwater(newData.water);
              setNewHwater(newData.water);
              setNewHpower(newData.power);
              setHpower(newData.power);
              setHnote(newData.note);
              setHfloor(newData.floor);
              setrent_date(newData.rent_date);
            }

            if(Hwater.includes("月繳")){
              const inputString = Hwater;
              const numbers = inputString.match(/\d+/g);
              setHwaterMoney(numbers);
              setHwater("月繳");
            }

            if(Hpower.includes("一度")){
              const inputPowerString = Hpower;
              const powerNumbers = inputPowerString.match(/\d+/g);
              setHpowerMoney(powerNumbers);
              setHpower("一度");
            }
            
          }, [newData]);

          
        const handleHtitleChange = event => {
          setHtitle(event.target.value);
        };
        const handleHaddressChange = event => {
          setHaddress(event.target.value);
        };
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
          setHtype(event.target.value);
        };
        const handleHareaChange = event => {
          setHarea(event.target.value);
        };
        const handlehaveCarChange = event => {
          sethaveCar(event.target.value);
        };
        const handleHwaterChange = event => {
          console.log(event.target.value);
          setHwater(event.target.value);
        };
        const handleHwaterMoneyChange = event => {
          setHwaterMoney(event.target.value);
          console.log(event.target.value);
          if(Hwater!=="台水" && Hwater!=="含房租內" && Hwater!==""){
            setHwater("月繳"+event.target.value+"元");
          }
        };
        const handleHpowerChange = event => {
          console.log(event.target.value);
          setHpower(event.target.value);
        };
        const handleHpowerMoneyChange = event => {
          setHpowerMoney(event.target.value);
          console.log(event.target.value);
          if(Hpower!=="台電" && Hpower!=="含房租內" && Hpower!==""){
            setHpower("一度"+event.target.value+"元");
          }
        };
        const handleHnoteChange = event => {
          setHnote(event.target.value);
        };
        const handleHfloorChange = event => {
          setHfloor(event.target.value);
        };
        const handlerent_dateChange = event => {
          setrent_date(event.target.value);
        };

  const handleModifyHouseSubmit = (e) => {
    console.log(Hpower);
    e.preventDefault();
    const student_id = loginUser();
    const formData = {
                    studentID: userInfo,
                    postId:postId,
                    title : Htitle,
                    money : Hmoney,
                    people : Hpeople,
                    address : Haddress,
                    area : Harea,
                    gender : rentGender,
                    style : Htype,
                    water : Hwater,
                    power : Hpower,
                    car : haveCar,
                    floor : Hfloor,
                    rent_date : rent_date,
                    note : Hnote,
                  };
                  
                  if(Hwater==='月繳'){
                    formData.water = "月繳"+HwaterMoney+"元";
                  }
                  if(Hpower==='一度'){
                    formData.power = "一度"+HpowerMoney+"元";
                  }
                  fetch('/rent_post_modify', {
                        method: 'PUT',
                        headers: {
                          'Content-Type': 'application/json',
                          'Authorization': `${token}`
                        },
                        body: JSON.stringify(formData)
                      })
                      .then(response => response.status)
                      .then(data => {
                        console.log(data);
                        navigate("/rentArticle", {
                            state: {
                              fromSearch:false,
                              studentID: userInfo,
                              postId: postId},});
                      })
                      .catch(error => {
                        console.error(error);
                      });
                      
                   //Form submission happens here
  }
        return (
          <form className='articleRentForm' onSubmit={handleModifyHouseSubmit}>
            <div className='articleRentFormTitle'>
              <label>標題:</label>
              <input type='text' name = 'Htitle' onChange={handleHtitleChange} value={Htitle}></input>
            </div><br/>
            <div className='articleRentFormAddress'>
              <label>地址:</label>
              <input type='text' name = 'Haddress' onChange={handleHaddressChange} value={Haddress}></input>
            </div><br/>
            <div className='articleRentMoney'>
              <label>租金:</label>
              <input type='text' name = 'Hmoney' onChange={handleHmoneyChange} value={Hmoney}></input>
            </div><br/>
            <div className='articleRentFormGender'>
              <label>性別:</label>
              <input type="radio" id='girl' value='女' name='rentGender' checked={rentGender === '女'} onChange={handlerentGenderChange} ></input>
              <label for="girl">女</label>
              <input type="radio" id='boy' value='男' name='rentGender' checked={rentGender === '男'} onChange={handlerentGenderChange}></input>
              <label for="boy">男</label>
              <input type="radio" id='both' value='不限' name='rentGender' checked={rentGender === '不限'} onChange={handlerentGenderChange}></input>
              <label for="both">無限制</label>
            </div><br/>
            <div className='articleRentFormPeople'>
              <label>人數:</label>
              <input type='text' name = 'Hpeople' onChange={handleHpeopleChange} value={Hpeople}></input>
            </div><br/>
            <div className='articleRentFormType'>
              <label>房型:</label>
              <input type='text' name = 'Htype' className='articleRentFormTypeInput' onChange={handleHtypeChange} value={Htype}></input>
            </div><br/>
            <div className='articleRentFormRegion'>
              <label>地區:</label>
              <select name = 'Harea' value={Harea} className='articleRentFormRegionInput' onChange={handleHareaChange}>
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
            <div className='articleRentFormFloor'>
              <label>樓層:</label>
              <input type='text' name = 'Hfloor' onChange={handleHfloorChange} value={Hfloor}></input>
            </div><br/>
            <div className='articleRentFormCar'>
              <label>車位:</label>
              <input type="radio" id='yes' value='有' name='haveCar' checked={haveCar === '有'} onChange={handlehaveCarChange}></input>
              <label for="yes">有</label>
              <input type="radio" id='no' value='無' name='haveCar' checked={haveCar === '無'} onChange={handlehaveCarChange}></input>
              <label for="no">無</label>
            </div><br/>
            <div className='articleRentFormWater'>
              <label>水費:</label>
              <input type="radio" id='postArticle_waterOfficial' value='台水' name='rentWater' checked={Hwater === '台水'} onChange={handleHwaterChange} ></input>
              <label for="postArticle_waterOfficial">台水</label>
              <input type="radio" id='postArticle_waterLandlord' value='含房租內' name='rentWater' checked={Hwater === '含房租內'} onChange={handleHwaterChange}></input>
              <label for="postArticle_waterLandlord">含房租內</label>
              <input type="radio" id='postArticle_waterOne' value='月繳' name='rentWater' checked={Hwater === '月繳'}   onChange={handleHwaterChange}></input>
              <label for="postArticle_waterOne">月繳<input type='text' className='postArticle_waterMoney' value={HwaterMoney} onChange={handleHwaterMoneyChange}></input>元</label>
            </div><br/>
            <div className='articleRentFormPower'>
              <label>電費:</label>
              <input type="radio" id='postArticle_powerOfficial' value='台電' name='rentPower' checked={Hpower === '台電'} onChange={handleHpowerChange} ></input>
              <label for="postArticle_powerOfficial">台電</label>
              <input type="radio" id='postArticle_powerLandlord' value='含房租內' name='rentPower' checked={Hpower === '含房租內'} onChange={handleHpowerChange}></input>
              <label for="postArticle_powerLandlord">含房租內</label>
              <input type="radio" id='postArticle_powerOne' value='一度' name='rentPower' checked={Hpower === '一度'}  onChange={handleHpowerChange}></input>
              <label for="postArticle_powerOne">一度<input type='text' className='postArticle_powerMoney' value={HpowerMoney} onChange={handleHpowerMoneyChange} ></input>元</label>
            </div><br/>
            <div className='articleRentFormDate'>
              <label>起租日期:</label>
              <input type='text' name = 'rent_date' onChange={handlerent_dateChange} value={rent_date}></input>
            </div><br/>
            <div className='articleRentFormText'>
              <label>內文:</label><br/>
              <textarea type='text' className='articleRentFormTextInput' name = 'Hnote' onChange={handleHnoteChange} value={Hnote}></textarea>
            </div><br/>
            <ArticleSubmitBtnPosition>
                <ArticleSubmitBtn type="submit">確認修改</ArticleSubmitBtn>
            </ArticleSubmitBtnPosition>
          </form>  
        );
      }

      function ArticleChangeClassInput({ newData }) {

        const [Ctitle, setCtitle] = useState("");
        const [Ctext, setCtext] = useState("");
        const [CCategory, setCCategory] = useState("");
        const [Ctime, setCtime] = useState([]);
        const [Cteacher, setCteacher] = useState("");
        const [timeArray, setTimeArray] = useState([]);
        const [numberArray, setNumberArray] = useState([]);

        useEffect(() => {
          if (newData) {
            setCtitle(newData.course);
            setCtext(newData.content);
            setCCategory(newData.category);
            setCtime(newData.time.join('、 '));
            setCteacher(newData.teacher);
            setNumberArray(newData.time);
          }
        }, [newData]);


        const handleCtitleChange = event => {
          setCtitle(event.target.value);
        };
        const handleCCategoryChange = event => {
          setCCategory(event.target.value);
        };
        const handleCtimeChange = event => {
          setCtime(event.target.value);
          const numbers = event.target.value.split('、').map(Number);
          console.log(numbers);
          setNumberArray(numbers);
        };
        const handleCteacherChange = event => {
          setCteacher(event.target.value);
        };
        const handleCtextChange = event => {
          setCtext(event.target.value);
        };
        const handleChangeClassSubmit = (e) => {
          e.preventDefault();
          const student_id = loginUser();
          const formData = {
                          studentID: userInfo,
                          postId:postId,
                          course : Ctitle,
                          category:CCategory,
                          time: numberArray,
                          teacher:Cteacher,
                          content : Ctext,
                        };
                        fetch('/course_post_modify', {
                              method: 'PUT',
                              headers: {
                                'Content-Type': 'application/json',
                                'Authorization': `${token}`
                              },
                              body: JSON.stringify(formData)
                            })
                            .then(response => response.status)
                            .then(data => {
                              console.log(data);
                              navigate("/changeClassList", {
                                state: {
                                  studentID: userInfo,
                                        time:timeValue,},});
                            })
                            .catch(error => {
                              console.error(error);
                            });
                            
                     
                         //Form submission happens here
        }

        return (
          <form className='articleChangeClassForm' onSubmit={handleChangeClassSubmit}>
            <div className='articleChangeClassFormTitle'>
              <label>標題:</label>
              <input type='text' className='articleChangeClassFormTitleInput'  value={Ctitle} disabled></input>
            </div><br/>
            <div className='articleChangeClassFormCategory'>
            <label>分類:</label>
              <input type='text' className='articleChangeClassFormCategoryInput' value={CCategory}  disabled></input>
              </div><br/>
            <div className='articleChangeClassFormTime'>
              <label>時間:</label>
              <input type='text' className='articleChangeClassFormTimeInput' value={Ctime} disabled></input>
            </div><br/>
            <div className='articleChangeClassFormTeacher'>
              <label>老師:</label>
              <input type='text' className='articleChangeClassFormTeacherInput'  value={Cteacher} disabled></input>
            </div><br/>
            <div className='articleChangeClassFormText'>
              <label>內文:</label><br/>
              <textarea type='text' className='articleChangeClassFormTextInput'  value={Ctext}></textarea>
            </div><br/>
            <ArticleSubmitBtnPosition>
                <ArticleSubmitBtn type="submit">確認修改</ArticleSubmitBtn>
            </ArticleSubmitBtnPosition>
          </form>  
        );
      }

      function ModifyPost() {
        const [isFoodShown, setIsFoodShown] = useState(true);
        const [isRentShown, setIsRentShown] = useState(false);
        const [isChangeClassShown, setIsChangeClassShown] = useState(false);
        const [Fetchurl, setFetchurl] = useState("");
        const [data, setData] = useState(null);
        const formData = {
          studentID: '00957017',
          postId: postId,
        };
      
        useEffect(() => {
          let isHandled = false;
      
          const FoodHandler = () => {
            setIsFoodShown(true);
            setIsRentShown(false);
            setIsChangeClassShown(false);
            //setFetchurl('/food_full_post');
          };
      
          const RentHandler = () => {
            setIsFoodShown(false);
            setIsRentShown(true);
            setIsChangeClassShown(false);
            // setFetchurl('/rent_full_post');
          };
      
          const ChangeClassHandler = () => {
            setIsFoodShown(false);
            setIsRentShown(false);
            setIsChangeClassShown(true);
            // setFetchurl('/course_full_post');
          };
      
          if (ModifyType === 'food' && !isHandled) {
            FoodHandler();
            isHandled = true;
          } else if (ModifyType === 'rent' && !isHandled) {
            RentHandler();
            isHandled = true;
          } else if (ModifyType === 'changeClass' && !isHandled) {
            ChangeClassHandler();
            isHandled = true;
          }
        }, [ModifyType]);
      
        useEffect(() => {
            if (!data) {
              let fetchUrl = '';
              
              if (ModifyType === 'food') {
                fetchUrl = '/food_full_post';
              } else if (ModifyType === 'rent') {
                fetchUrl = '/rent_full_post';
              } else if (ModifyType === 'changeClass') {
                fetchUrl = '/course_full_post';
              }
              
              if (fetchUrl) {
                fetch(fetchUrl, {
                  method: 'POST',
                  headers: { 'Content-Type': 'application/json' },
                  body: JSON.stringify(formData),
                })
                  .then((response) => response.json())
                  .then((data) => {
                    setData(data);
                    console.log(data);
                  })
                  .catch((error) => {
                    console.error('Error:', error);
                  });
              }
            }
          }, [data]);

          const handleBackChange = event => {
            if(isFoodShown){
              navigate("/foodArticle", {
                state: {
                  postId : postId,},});
            }
            else if(isRentShown){
              navigate("/rentArticle", {
                state: {
                  postId : postId,},});
            }
          };
          
          
      
        return (
          <div className="PostArticle">
            <Back src={back} onClick={handleBackChange} alt="回上一頁" />
            <div className="PostArticle_bg">
              <Title>修改</Title>
              <img className="post_elmo" src={elmo} />
              <img className="post_conversation" src={conversation} />
              <div className="articleFormPosition">
                <div className="articleForm">
                  <div className="inputFormPosition">
                    {isFoodShown && data && <ArticleModifyFoodInput newData={data}/>}
                    {isRentShown && <ArticleModifyRentInput newData={data}/>}
                    {isChangeClassShown && <ArticleChangeClassInput newData={data}/>}
                  </div>
                </div>
              </div>
            </div>
          </div>
        );
      }
      

    return (
           
                <Routes>
                    <Route path="/" element={<ModifyPost  />} />
                </Routes>
             
    );
}

export default ModifyPost;


/* note
-select要用onChange事件才可執行
*/
