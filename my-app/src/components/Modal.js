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
import { Routes ,Route, useNavigate } from 'react-router-dom';


function ModalRent(){
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

function ModalFood(){
    return(
            <form>
                <h2 className='modal_tilte'>篩選貼文</h2>
                <div className='modalBodyText'>
                    <div className='ModalFoodFormRegion'>
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
                    <div className='ModalFoodStore'>
                        <label>店家:</label>
                        <input className='ModalFoodStoreText' type="text"></input>
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




function Modal({closeModal,type, postId}){

    let navigate = useNavigate();
    const [isModalFood, setisModalFood] = useState(true);
    const [isModalRent, setIsModalRent] = useState(false);
    const [isModalChangeClass, setisModalChangeClass] = useState(false);
    const [isRating, setisRating] = useState(false);
    const [isPostId, setisPostId] = useState(false);
  
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
            const formData = {
                            postId:isPostId,
                            studentID: "00957025",
                            p_review : "",
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
                              navigate("/food")
                              
                           //Form submission happens here
          }
    
        return(
                <form onSubmit={handleRatingSubmit}>
                    <h2 className='modal_tilte'>評分</h2>
                    <div className='modalBodyText'>
                    <div className='ModalFoodRatingArea'>
                        <label>評分一下吧(1-5):</label>
                        <input className='ModalFoodRating' type="number" onChange={handleFratingChange} value={Frating}></input>
                        </div><br/>
                    </div>
                    <ModalSubmitBtn>確認</ModalSubmitBtn>
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