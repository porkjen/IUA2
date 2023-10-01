import './courseSelection.css';
import React from 'react';
import back from './img/back.png';
import finger from './img/finger.png';
import searchScheduleImg from "./img/searchSchedule.PNG";
import rulesImg from "./img/rules.PNG";
import detectionCourseImg from "./img/detectionCourse.PNG";
import recommendedCourseImg from "./img/recommendedCourse.PNG";
import cuteDogImg from "./img/cutedoggy.PNG";
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';

const CourseSelection=()=> {
    function CourseSelection(){
        return(
            <div className="CourseSelection">
                <div className="courseSelection_bg">
                    <div>
                        <Link to='/homePage'>
                            <img src={back} alt="回上一頁" className="courseSelection_backicon"/>
                        </Link>
                    </div>
                    <div className="text">
                        <h1>選課</h1>
                    </div>
                    <div className="finger"><img src={finger} alt="IUA"></img></div>
                    <div className="courseSelection_allBtn">
                            <div class="flex">
                                <Link to='/timeTableChoose'>
                                <div className="searchScheduleBtn">
                                    <button className="searchScheduleButton">
                                        <img src={searchScheduleImg} className='courseSelectionpic'/>
                                    </button>
                                </div>
                                </Link>
                                <Link to='/rules'>
                                    <div className="rulesBtn">
                                        <button className="rulesButton">
                                            <img src={rulesImg} className='courseSelectionpic'/>
                                        </button>
                                    </div>
                                </Link>
                            </div>
                            <div class="flex">
                                <Link to='/preTimeTable'>
                                    <div className="recommendedCourseBtn">
                                        <button className="recommendedCourseButton">
                                            <img src={recommendedCourseImg} className='courseSelectionpic'/>
                                        </button>
                                    </div>
                                </Link>
                                <Link to='/DetectCourse'>
                                    <div className="detectionCourseBtn">
                                        <button className="detectionCourseButton">
                                            <img src={detectionCourseImg} className='courseSelectionpic'/>
                                        </button>
                                    </div>
                                </Link>
                            </div>
                    </div>
                    <div><img src={cuteDogImg} className='dogpic'/></div>
                </div> 
            </div>
            
            

        );

    }
    return (    
        <Routes>
            <Route path="/" element={<CourseSelection />}/>
        </Routes>
    
    );
}

export default CourseSelection;