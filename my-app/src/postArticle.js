import './PostArticle.css';
import React from 'react';
import elmo from './img/elmo.png';
import conversation from './img/conversation.png';
import cat1 from './img/SignIn1.png';
import cat2 from './img/SignIn2.PNG';
import {Title}  from './components/ArticleStyle.js';
import back from './img/back.png';
import {Back}  from './components/Style.js';
import {ArticleSubmitBtn, ArticleSubmitBtnPosition}  from './components/ArticleStyle.js';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route, useNavigate, useLocation } from 'react-router-dom';
import {useState} from "react";
import { loginUser } from "./cookie.js";
import { getAuthToken } from "./utils";

const PostArticle=()=> {
    let navigate = useNavigate();
    const location = useLocation();
    const { studentID, changeClassClassName, changeClassClassTeacher, changeClassClassTime,IsChangeClass,changeClassCategory } = location.state || {};

    const userInfo = loginUser();
    const token = getAuthToken();

    function ArticleFoodInput() {
        const [Ftitle, setFtitle] = useState("");
        const [Ftime, setFtime] = useState("");
        const [FtimeMonday, setFtimeMonday] = useState("");
        const [FtimeTuesday, setFtimeTuesday] = useState("");
        const [FtimeWednesday, setFtimeWednesday] = useState("");
        const [FtimeThursday, setFtimeThursday] = useState("");
        const [FtimeFriday, setFtimeFriday] = useState("");
        const [FtimeSaturday, setFtimeSaturday] = useState("");
        const [FtimeSunday, setFtimeSunday] = useState("");
        const [Farea, setFarea] = useState("");
        const [Froad, setFroad] = useState("");
        const [Faddress, setFaddress] = useState("");
        const [Finfo, setFinfo] = useState("");
        const [FLink, setFLink] = useState("");
        const [Frate, setFrate] = useState("");

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

        const handleFareaChange = event => {
          setFarea(event.target.value);
        };

        const handleFroadChange = event => {
          setFroad(event.target.value);
        };

        const handleFaddressChange = event => {
          setFaddress(event.target.value);
        };
        const handleFinfoChange = event => {
          setFinfo(event.target.value);
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
                          studentID: userInfo,
                          store : Ftitle,
                          weekday_text : openTime,
                          address : Faddress,
                          url : FLink,
                          road : Froad,
                          district : Farea,
                          review: {
                            p_review: Finfo,
                            p_rate: Frate
                          }
                        };
                        fetch('/food_posts', {
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
                         //Form submission happens here
                         navigate("/food", {
                          state: {
                            fromSearch:false,},});
        }
       
        return (
          <form className='articleFoodForm' onSubmit={handleFoodSubmit}>
            <div className='articleFoodFormTitle'>
              <label>標題:&emsp;&emsp;&emsp;</label>
              <input type='text' name = 'Ftitle' onChange={handleFtitleChange} value={Ftitle} required="required"></input>
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
              <input type='text' name = 'Faddress' onChange={handleFaddressChange} value={Froad} required="required"></input>
            </div><br/>
            <div className='articleFoodFormAddress'>
              <label>店家地址:&emsp;</label>
              <input type='text' name = 'Faddress' onChange={handleFaddressChange} value={Faddress} required="required"></input>
            </div><br/>
            <div className='articleFoodFormAddress'>
              <label>店家連結:&emsp;</label>
              <input type='text' name = 'FLink' onChange={handleFLinkChange} value={FLink}></input>
            </div><br/>
            <div className='articleFoodFormSchoolRate'>
              <label>我的評分:&emsp;</label>
              <select name = 'Frate' className='articleFoodFormSelect' value={Frate} onChange={handleFrateChange}>
                  <option>請選擇評分</option>
                  <option value='1'>1</option>
                  <option value='2'>2</option>
                  <option value='3'>3</option>
                  <option value='4'>4</option>
                  <option value='5'>5</option>
              </select>
            </div><br/>
            <div className='articleFoodFormInfo'>
              <label>我的評論:&emsp;</label>
              <input type='text' name = 'Finfo' onChange={handleFinfoChange} value={Finfo}></input>
            </div><br/>
              <ArticleSubmitBtnPosition>
                <ArticleSubmitBtn type="submit">確認發文</ArticleSubmitBtn>
              </ArticleSubmitBtnPosition>
          </form>  
        );
      }

      function ArticleRentInput() {
        const [Htitle, setHtitle] = useState("");
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

  const handleHouseSubmit = (e) => {
    e.preventDefault();
    const student_id = loginUser();
 
    const formData = {
                    studentID: userInfo,
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
                  fetch('/rent_post', {
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
                      navigate("/rent", {
                        state: {
                          fromSearch:false,},});
                          window.location.reload();
                   //Form submission happens here
  }
        return (
          <form className='articleRentForm' onSubmit={handleHouseSubmit}>
            <div className='articleRentFormTitle'>
              <label>標題:</label>
              <input type='text' name = 'Htitle' onChange={handleHtitleChange} value={Htitle} required="required"></input>
            </div><br/>
            <div className='articleRentFormAddress'>
              <label>地址:</label>
              <input type='text' name = 'Haddress' onChange={handleHaddressChange} value={Haddress} required="required"></input>
            </div><br/>
            <div className='articleRentMoney'>
              <label>租金:</label>
              <input type='text' name = 'Hmoney' onChange={handleHmoneyChange} value={Hmoney} required="required"></input>
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
              <select name = 'Htype' className='articleRentFormSelect' value={Htype} onChange={handleHtypeChange}>
                  <option>請選擇房型</option>
                  <option value='套房'>套房</option>
                  <option value='雅房'>雅房</option>
                  <option value='家庭式'>家庭式</option>
              </select>
            </div><br/>
            <div className='articleRentFormRegion'>
              <label>地區:</label>
              <select name = 'Harea' className='articleRentFormSelect' value={Harea} onChange={handleHareaChange}>
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
              <input type='text' name = 'Hfloor' onChange={handleHfloorChange} value={Hfloor} required="required"></input>
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
              <input type="radio" id='postArticle_waterOne' value='月繳' name='rentWater'  onChange={handleHwaterChange}></input>
              <label for="postArticle_waterOne">月繳<input type='text' className='postArticle_waterMoney' value={HwaterMoney} onChange={handleHwaterMoneyChange}></input>元</label>
            </div><br/>
            <div className='articleRentFormPower'>
            <label>電費:</label>
              <input type="radio" id='postArticle_powerOfficial' value='台電' name='rentPower' checked={Hpower === '台電'} onChange={handleHpowerChange} ></input>
              <label for="postArticle_powerOfficial">台電</label>
              <input type="radio" id='postArticle_powerLandlord' value='含房租內' name='rentPower' checked={Hpower === '含房租內'} onChange={handleHpowerChange}></input>
              <label for="postArticle_powerLandlord">含房租內</label>
              <input type="radio" id='postArticle_powerOne' value='一度' name='rentPower' onChange={handleHpowerChange}></input>
              <label for="postArticle_powerOne">一度<input type='text' className='postArticle_powerMoney' value={HpowerMoney} onChange={handleHpowerMoneyChange} ></input>元</label>
            </div><br/>
            <div className='articleRentFormDate'>
              <label>起租日期:</label>
              <input type='text' name = 'rent_date' onChange={handlerent_dateChange} value={rent_date} required="required"></input>
            </div><br/>
            <div className='articleRentFormText'>
              <label>內文:</label><br/>
              <textarea type='text' className='articleRentFormTextInput' name = 'Hnote' onChange={handleHnoteChange} value={Hnote}></textarea>
            </div><br/>
            <ArticleSubmitBtnPosition>
                <ArticleSubmitBtn type="submit">確認發文</ArticleSubmitBtn>
            </ArticleSubmitBtnPosition>
          </form>  
        );
      }

      function ArticleChangeClassInput() {

        const [Ctitle, setCtitle] = useState(changeClassClassName);
        const [Ctext, setCtext] = useState("");
        const [CCategory, setCCategory] = useState(changeClassCategory);
        const [Ctime, setCtime] = useState(changeClassClassTime);
        const [Cteacher, setCteacher] = useState(changeClassClassTeacher);
        const [numberArray, setNumberArray] = useState([]);
        
        const handleCtextChange = event => {
          setCtext(event.target.value);
        };
        const handleChangeClassSubmit = (e) => {
          console.log("innnnn");
          e.preventDefault();
          const student_id = loginUser();
          console.log("innnnn");
          const formData = {
                          studentID: userInfo,
                          course : Ctitle.name,
                          category:CCategory.category,
                          time:Ctime.time,
                          teacher:Cteacher.teacher,
                          content : Ctext,
                        };
                        fetch('/exchange_course_post', {
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
                            navigate("/changeClass")
                            
                         //Form submission happens here
        }

        return (
          <form className='articleChangeClassForm' onSubmit={handleChangeClassSubmit}>
            <div className='articleChangeClassFormTitle'>
              <label>標題:</label>
              <input type='text' className='articleChangeClassFormTitleInput'  value={Ctitle.name} placeholder='你有的課' required="required" disabled></input>
            </div><br/>
            <div className='articleChangeClassFormCategory'>
              <label>分類:</label>
              <input type='text' className='articleChangeClassFormCategoryInput' value={CCategory.category} disabled></input>
            </div><br/>
            <div className='articleChangeClassFormTime'>
              <label>時間:</label>
              <input type='text' className='articleChangeClassFormTimeInput' value={Ctime.time} required="required" disabled></input>
            </div><br/>
            <div className='articleChangeClassFormTeacher'>
              <label>老師:</label>
              <input type='text' className='articleChangeClassFormTeacherInput' value={Cteacher.teacher} required="required" disabled></input>
            </div><br/>
            <div className='articleChangeClassFormText'>
              <label>內文:</label><br/>
              <textarea type='text' className='articleChangeClassFormTextInput' onChange={handleCtextChange} value={Ctext} placeholder='留下偏好換的課或其他想說的話!' required="required"></textarea>
            </div><br/>
            <ArticleSubmitBtnPosition>
                <ArticleSubmitBtn>確認發文</ArticleSubmitBtn>
            </ArticleSubmitBtnPosition>
          </form>  
        );
      }

    function PostArticle() {
      /*const [nickName_id, setNickName_id] = useState("");
      const handleChange = event => {
        setNickName_id(event.target.nickName_id);
      };
      const handleSubmit = (event) => {
        event.preventDefault();
        alert(`The nickName you entered was: ${nickName_id}`)
      }*/
      const [selectedOption, setSelectedOption] = useState('');
      const [isFoodShown, setIsFoodShown] = useState(false);
      const [isRentShown, setIsRentShown] = useState(false);
      const [isChangeClassShown, setIsChangeClassShown] = useState(false);
      const [isFirst, setIsFirst] = useState(true);

      if(isFirst && IsChangeClass){
        setIsChangeClassShown(true);
        setIsFirst(false);
      }
      else if(isFirst){
        setIsFoodShown(true);
        setIsRentShown(false);
        setIsFirst(false);
      }
      
      const handleSelectChange = (event) => {
        const selectedValue = event.target.value;
        setSelectedOption(selectedValue);
    
        // 執行相應的處理
        if (selectedValue === 'food') {
          FoodHandler();
        }
        else if (selectedValue === 'rent') {
          RentHandler();
        }
        else if (selectedValue === 'changeClass') {
          ChangeClassHandler();
        }
      };

      const FoodHandler = () => {
        setIsFoodShown(true);
        setIsRentShown(false);
        setIsChangeClassShown(false);
      };

      const RentHandler = () => {
        setIsFoodShown(false);
        setIsRentShown(true);
        setIsChangeClassShown(false);
      };

      const ChangeClassHandler = () => {
        setIsFoodShown(false);
        setIsRentShown(false);
        setIsChangeClassShown(true);
      };

      
      
      return (
        <div className="PostArticle">
           <Link to='/choose'>
              <Back src={back} alt="回上一頁" />
          </Link>   
            <div className='PostArticle_bg'>
                <Title>發文</Title>
                <img className='post_cat1' src={cat1}/>
                <img className='post_cat2' src={cat2}/>
                <div className='articleFormPosition'>
                    <div className='articleForm' >
                      {isFoodShown && 
                        <select className='selectType' onChange={handleSelectChange}>
                            <option value="food">美食版</option>
                            <option value="rent">租屋版</option>
                        </select>
                      }
                      {isRentShown && 
                        <select className='selectType' onChange={handleSelectChange}>
                            <option value="food">美食版</option>
                            <option value="rent">租屋版</option>
                        </select>
                      }
                        <div className='inputFormPosition'>
                          {isFoodShown && <ArticleFoodInput/>}
                          {isRentShown && <ArticleRentInput/>}
                          {isChangeClassShown && <ArticleChangeClassInput/>}
                        </div>

                    </div>
                </div>

            </div>
        </div>
      );
    }

    return (
           
                <Routes>
                    <Route path="/" element={<PostArticle />} />
                </Routes>
             
    );
}

export default PostArticle;


/* note
-select要用onChange事件才可執行
*/
