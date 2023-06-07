import './Credit.css';
import React from 'react';
import back from './img/back.png';
import pencil from './img/pencil.png';
import remainCreditImg from "./img/remaincredit.PNG";
import mustCourseImg from "./img/mustcourse.PNG";
import coreCourseImg from "./img/corecourse.PNG";
import graduationImg from "./img/graduation.PNG";
import cuteDogImg from "./img/cutedoggy.PNG";
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';

const Credit=()=> {
    function Credit(){
        return(
            <div className="credit_bg">
            <div>
                <Link to='/homePage'>
                    <img src={back} alt="回上一頁" className="credit_backicon"/>
                </Link>
            </div>
            <div className="text">
                <h1>學分查詢</h1>
            </div>
            <div className="pencil"><img src={pencil} alt="IUA"></img></div>
            <div className="allcBtn">
                    <div class="flex">
                        <div className="remainBtn">
                            <button className="remainButton">
                                <img src={remainCreditImg} className='creditpic'/>
                            </button>
                        </div>
                        <Link to='/Search'>
                            <div className="mustclassBtn">
                                <button className="mustclassButton">
                                    <img src={mustCourseImg} className='creditpic'/>
                                </button>
                            </div>
                        </Link>
                    </div>
                    <div class="flex">
                        <Link to='/Core'>
                            <div className="coreclassBtn">
                                <button className="coreclassButton">
                                    <img src={coreCourseImg} className='creditpic'/>
                                </button>
                            </div>
                        </Link>
                        <Link to='/Graduation'>
                            <div className="graduationBtn">
                                <button className="graduationButton">
                                    <img src={graduationImg} className='creditpic'/>
                                </button>
                            </div>
                        </Link>
                    </div>
            </div>
            <div><img src={cuteDogImg} className='dogpic'/></div>
            </div> 
            

        );

    }
    return (    
        <Routes>
            <Route path="/" element={<Credit />}/>
        </Routes>
    
    );
}

export default Credit;