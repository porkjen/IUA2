import './Modal.css';
import React from "react";
import sandwich from '../img/sandwich.png';
import pizza from '../img/pizza.png';
import taco from '../img/taco.png';
import cookiem from '../img/cookiem.png';
import cookiemonster from '../img/cookiemonster.png';
import minion from '../img/minion.png';
import banana from '../img/banana.png';
import {ArticleSubmitBtn, ModalSubmitBtn} from './ArticleStyle.js';
import {useState,useEffect} from "react";
import { Routes ,Route, useNavigate, useLocation } from 'react-router-dom';



function ModalChangeClass(){
    return(
            <form>
                <h2 className='modal_tilte'>篩選貼文</h2>
                <div className='modalBodyText'>
                    <div className='ModalRentFormRegion'>
                        <label>地區: </label>
                        <select>
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
                    <div className='ModalRentFormGender'>
                        <label>性別:</label>
                        <input type="radio" id='girl' value='girl' ></input>
                        <label for="girl">女</label>
                        <input type="radio" id='boy' value='boy'></input>
                        <label for="boy">男</label>
                        <input type="radio" id='both' value='both'></input>
                        <label for="both">無限制</label>
                    </div><br/>
                    <div className='ModalRentFormPeople'>
                        <label>人數: </label>
                        <select>
                            <option>請選擇人數</option>
                            <option value='one'>1</option>
                            <option value='two'>2</option>
                            <option value='three'>3</option>
                            <option value='four'>4</option>
                        </select>
                    </div><br/>
                    <div className='ModalRentFormType'>
                    <label>房型: </label>
                        <select>
                            <option>請選擇房型</option>
                            <option value='studio'>套房</option>
                            <option value='room'>雅房</option>
                            <option value='family'>家庭式</option>
                        </select>
                    </div><br/>
                    <div className='ModalRentFormCar'>
                        <label>車位:</label>
                        <input type="radio" id='yes' value='yes'></input>
                        <label for="yes">有</label>
                        <input type="radio" id='no' value='no'></input>
                        <label for="no">無</label>
                    </div><br/>
                </div>
                <ModalSubmitBtn>確認</ModalSubmitBtn>
            </form>
    );
}



function RatingFood(){
    return(
        <div>
            <img className='modal_sandwich' src={sandwich}/>
            <img className='modal_pizza' src={pizza}/>
            <img className='modal_cookiemonster' src={cookiemonster}/>
            <img className='modal_cookiem' src={cookiem}/>
        </div>

    );
}

function ChooseArticle(){
    return(
        <div>
            <img className='modal_minion' src={minion}/>
            <img className='modal_banana' src={banana}/>
        </div>

    );
}




function Modal({closeModal, type, postId, comment, alreadyComment}){

    let navigate = useNavigate();
    const [isModalFood, setisModalFood] = useState(true);
    const [isModalRent, setIsModalRent] = useState(false);
    const [isModalChangeClass, setisModalChangeClass] = useState(false);
    const [isRating, setisRating] = useState(false);
    const [isPostId, setisPostId] = useState(false);
    const [isComment, setisComment] = useState(false);
    const [isAlreadyComment, setIsAlreadyComment] = useState(false);

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
                    FName:FSName,},});
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
    
  
    useEffect(() => {
      if (type === "rent") {
        setisModalFood(false);
        setIsModalRent(true);
        setisModalChangeClass(false);
        setisRating(false);
      } else if (type === "food") {
        setisModalFood(true);
        setIsModalRent(false);
        setisModalChangeClass(false);
        setisRating(false);
      } else if (type === "changeClass") {
        setisModalFood(false);
        setIsModalRent(false);
        setisModalChangeClass(true);
        setisRating(false);
      }
      else if (type === "rating") {
        setisModalFood(false);
        setIsModalRent(false);
        setisModalChangeClass(false);
        setisRating(true);
        setisPostId(postId)
        setisComment(comment);
        setIsAlreadyComment(alreadyComment);
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
                                studentID: "00957025",
                                p_review : isComment,
                                p_rate : Frating,
                              };
                              fetch('/food_review_modify', {
                                method: 'PUT',
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
                        }
                        else{
                            const formData = {
                                postId:isPostId,
                                studentID: "00957025",
                                p_review : "尚未發表評論",
                                p_rate : Frating,
                              };
                            fetch('/food_review_add', {
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
                        }
                              window.location.reload();
                              navigate("/foodArticle", {
                                state: {
                                  studentID:"00957025",
                                  postId,postId,
                                  fromSearch:false},});
                                  
                              
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
                        {isModalChangeClass && <ModalChangeClass/>}
                        {isRating && <ModalRating/>}
                    </div>
                </div>
            </div>
        </div>

    );
}

export default Modal;