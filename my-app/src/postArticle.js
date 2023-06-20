import './PostArticle.css';
import React from 'react';
import {Title}  from './components/ArticleStyle.js';
import {ArticleSubmitBtn, ArticleSubmitBtnPosition}  from './components/ArticleStyle.js';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route, useNavigate } from 'react-router-dom';
import {useState} from "react";
import { loginUser } from "./cookie.js";

const PostArticle=()=> {
    let navigate = useNavigate();

    function ArticleFoodInput() {
        const [Ftitle, setFtitle] = useState("");
        const [Ftime, setFtime] = useState("");
        const [Faddress, setFaddress] = useState("");
        const [Finfo, setFinfo] = useState("");
        const [FgoogleRate, setFgoogleRate] = useState("");
        const [Frate, setFrate] = useState("");

        const handleFtitleChange = event => {
          setFtitle(event.target.value);
        };
        const handleFtimeChange = event => {
          setFtime(event.target.value);
        };
        const handleFaddressChange = event => {
          setFaddress(event.target.value);
        };
        const handleFinfoChange = event => {
          setFinfo(event.target.value);
        };
        const handleFgoogleRateChange = event => {
          setFgoogleRate(event.target.value);
        };
        const handleFrateChange = event => {
          setFrate(event.target.value);
        };

        const handleFoodSubmit = (e) => {
          e.preventDefault();
          const student_id = loginUser();
          const formData = {
                          studentID: "00957025",
                          store : Ftitle,
                          weekday_text : [Ftime],
                          address : Faddress,
                          review: {
                            p_review: Finfo,
                            p_rate: Frate
                          }
                        };
                        fetch('/food_posts', {
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
                         //Form submission happens here
                         navigate("/food")
        }
       
        return (
          <form className='articleFoodForm' onSubmit={handleFoodSubmit}>
            <div className='articleFoodFormTitle'>
              <label>標題:&emsp;&emsp;&emsp;</label>
              <input type='text' name = 'Ftitle' onChange={handleFtitleChange} value={Ftitle}></input>
            </div><br/>
            <div className='articleFoodFormTime'>
              <label>營業時間:&emsp;</label>
              <input type='text' name = 'Ftime' onChange={handleFtimeChange} value={Ftime}></input>
            </div><br/>
            <div className='articleFoodFormAddress'>
              <label>店家地址:&emsp;</label>
              <input type='text' name = 'Faddress' onChange={handleFaddressChange} value={Faddress}></input>
            </div><br/>
            <div className='articleFoodFormSchoolRate'>
              <label>我的評分(1-5):&emsp;</label>
              <input type='number' name = 'Frate' onChange={handleFrateChange} value={Frate}></input>
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
        const [Hpower, setHpower] = useState("");
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
        const handleHpowerChange = event => {
          setHpower(event.target.value);
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
                    studentID: "00957025",
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
                      navigate("/rent")
                   //Form submission happens here
  }
        return (
          <form className='articleRentForm' onSubmit={handleHouseSubmit}>
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
              <input type="radio" id='girl' value='girl' name='rentGender' checked={rentGender === 'girl'} onChange={handlerentGenderChange} ></input>
              <label for="girl">女</label>
              <input type="radio" id='boy' value='boy' name='rentGender' checked={rentGender === 'boy'} onChange={handlerentGenderChange}></input>
              <label for="boy">男</label>
              <input type="radio" id='both' value='both' name='rentGender' checked={rentGender === 'both'} onChange={handlerentGenderChange}></input>
              <label for="both">無限制</label>
            </div><br/>
            <div className='articleRentFormPeople'>
              <label>人數:</label>
              <input type='text' name = 'Hpeople' onChange={handleHpeopleChange} value={Hpeople}></input>
            </div><br/>
            <div className='articleRentFormType'>
              <label>房型:</label>
              <input type='text' name = 'Htype' onChange={handleHtypeChange} value={Htype}></input>
            </div><br/>
            <div className='articleRentFormRegion'>
              <label>地區:</label>
              <select name = 'Harea' value={Harea} onChange={handleHareaChange}>
                  <option>請選擇區域</option>
                  <option value='Zhongzheng'>中正區</option>
                  <option value='Xinyi'>信義區</option>
                  <option value='Renai'>仁愛區</option>
                  <option value='Zhongshan'>中山區</option>
                  <option value='Anle'>安樂區</option>
                  <option value='Nuannuan'>暖暖區</option>
                  <option value='Qidu'>七堵區</option>
              </select>
            </div><br/>
            <div className='articleRentFormFloor'>
              <label>樓層:</label>
              <input type='text' name = 'Hfloor' onChange={handleHfloorChange} value={Hfloor}></input>
            </div><br/>
            <div className='articleRentFormCar'>
              <label>車位:</label>
              <input type="radio" id='yes' value='yes' name='haveCar' checked={haveCar === 'yes'} onChange={handlehaveCarChange}></input>
              <label for="yes">有</label>
              <input type="radio" id='no' value='no' name='haveCar' checked={haveCar === 'no'} onChange={handlehaveCarChange}></input>
              <label for="no">無</label>
            </div><br/>
            <div className='articleRentFormWater'>
              <label>水費:</label>
              <input type='text' name = 'Hwater' onChange={handleHwaterChange} value={Hwater}></input>
            </div><br/>
            <div className='articleRentFormPower'>
              <label>電費:</label>
              <input type='text' name = 'Hpower' onChange={handleHpowerChange} value={Hpower}></input>
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
                <ArticleSubmitBtn type="submit">確認發文</ArticleSubmitBtn>
            </ArticleSubmitBtnPosition>
          </form>  
        );
      }

      function ArticleChangeClassInput() {

        const [Ctitle, setCtitle] = useState("");
        const [Ctext, setCtext] = useState("");
        const [CCategory, setCCategory] = useState("");
        const [Ctime, setCtime] = useState("");
        const [Cteacher, setCteacher] = useState("");

        const handleCtitleChange = event => {
          setCtitle(event.target.value);
        };
        const handleCCategoryChange = event => {
          setCCategory(event.target.value);
        };
        const handleCtimeChange = event => {
          setCtime(event.target.value);
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
                          studentID: "00957025",
                          course : Ctitle,
                          category:CCategory,
                          time:[Ctime],
                          teacher:Cteacher,
                          content : Ctext,
                        };
                        fetch('/exchange_course_post', {
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
                            navigate("/changeClass")
                         //Form submission happens here
        }

        return (
          <form className='articleChangeClassForm' onSubmit={handleChangeClassSubmit}>
            <div className='articleChangeClassFormTitle'>
              <label>標題:</label>
              <input type='text' className='articleChangeClassFormTitleInput' onChange={handleCtitleChange} value={Ctitle}></input>
            </div><br/>
            <div className='articleChangeClassFormCategory'>
              <label>分類:</label>
              <input type='text' className='articleChangeClassFormCategoryInput' onChange={handleCCategoryChange} value={CCategory}></input>
            </div><br/>
            <div className='articleChangeClassFormTime'>
              <label>時間:</label>
              <input type='text' className='articleChangeClassFormTimeInput' onChange={handleCtimeChange} value={Ctime}></input>
            </div><br/>
            <div className='articleChangeClassFormTeacher'>
              <label>老師:</label>
              <input type='text' className='articleChangeClassFormTeacherInput' onChange={handleCteacherChange} value={Cteacher}></input>
            </div><br/>
            <div className='articleChangeClassFormText'>
              <label>內文:</label><br/>
              <textarea type='text' className='articleChangeClassFormTextInput' onChange={handleCtextChange} value={Ctext}></textarea>
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
      const [isFoodShown, setIsFoodShown] = useState(true);
      const [isRentShown, setIsRentShown] = useState(false);
      const [isChangeClassShown, setIsChangeClassShown] = useState(false);

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
            <div className='PostArticle_bg'>
                <Title>發文</Title>
                <div className='articleFormPosition'>
                    <div className='articleForm' >
                        <select className='selectType' onChange={handleSelectChange}>
                            <option value="food">美食版</option>
                            <option value="rent">租屋版</option>
                            <option value="changeClass">換課版</option>
                        </select>
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
