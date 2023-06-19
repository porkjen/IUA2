import './Modal.css';
import React from "react";
import {ArticleSubmitBtn} from './ArticleStyle.js';
import {useState,useEffect} from "react";

function ModalRent(){
    return(
            <form>
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
                <ArticleSubmitBtn>確認</ArticleSubmitBtn>
            </form>
    );
}

function ModalChangeClass(){
    return(
            <form>
                <div className='modalBodyText'>
                    <div className='ModalRentFormRegion'>
                        <label>地區: </label>
                        <select>
                            <option>請選擇區域</option>
                            <option value='Zhongzheng'>中正區</option>
                            <option value='Xinyi'>信義區</option>
                            <option value='Renai'>仁愛區</option>                                    <option value='Zhongshan'>中山區</option>
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
                <ArticleSubmitBtn>確認</ArticleSubmitBtn>
            </form>
    );
}

function ModalFood(){
    return(
            <form>
                <div className='modalBodyText'>
                    <div className='ModalFoodFormRegion'>
                        <label>地區: </label>
                        <select>
                            <option>請選擇區域</option>
                            <option value='Zhongzheng'>中正區</option>
                            <option value='Xinyi'>信義區</option>
                            <option value='Renai'>仁愛區</option>                                    <option value='Zhongshan'>中山區</option>
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
                <ArticleSubmitBtn>確認</ArticleSubmitBtn>
            </form>
    );
}


function Modal({closeModal,type}){

    const [isModalFood, setisModalFood] = useState(true);
    const [isModalRent, setIsModalRent] = useState(false);
    const [isModalChangeClass, setisModalChangeClass] = useState(false);
  
    useEffect(() => {
      if (type === "rent") {
        setisModalFood(false);
        setIsModalRent(true);
        setisModalChangeClass(false);
      } else if (type === "food") {
        setisModalFood(true);
        setIsModalRent(false);
        setisModalChangeClass(false);
      } else if (type === "changeClass") {
        setisModalFood(false);
        setIsModalRent(false);
        setisModalChangeClass(true);
      }
    }, [type]);

    return(
        <div className="modalBackground">
            <div className="modalContainer">
                <button className='modalClose' onClick={() => closeModal(false)}>X</button>
                <div className="modalTitle">
                    <h2 className='modal_tilte'>篩選貼文</h2>
                    <div className="modalBody">
                        {isModalFood && <ModalFood/>}
                        {isModalRent && <ModalRent/>}
                        {isModalChangeClass && <ModalChangeClass/>}
                    </div>
                </div>
            </div>
        </div>

    );
}

export default Modal;