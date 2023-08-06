import './App.css';
import React from 'react';
import {BrowserRouter} from "react-router-dom"//BrowserRouter
import { Routes ,Route } from 'react-router-dom';
import Cover from './Cover.js';
import SignIn from './SignIn.js';
import Remain from './remain.js';
import NickName from './nickName';
import TimeTable from './timeTable';
import ChangeClass from './changeClass';
import ChangeClassList from './changeClassList';
import Food from './food';
import Rent from './rent';
import PostArticle from './postArticle';
import RentArticle from './rentArticle';
import FoodArticle from './foodArticle';
import ChangeClassArticle from './changeClassArticle';
import HomePage from './homePage';
import Credit from './Credit';
import Core from './Core';
import Must from './Must';
import Graduation from './Graduation';
import Search from './Search';
import Choose from './choose';
import CourseSelection from './courseSelection';
import GeneralEducaton from './generalEducation';
import Favorite from './favorite';
import DetectCourse from './DetectCourse';
import Chatroom from './chatroom';
import ModifyPost from './ModifyPost';
import ChatRoomList from './ChatRoomList';

const App=()=> {
   
    return (
           
                <Routes>
                    <Route path="/" element={<Cover/>} />
                    <Route path="/signIn" element={<SignIn/>} />
                    <Route path="/remain" element={<Remain/>} />
                    <Route path="/nickName" element={<NickName/>} />
                    <Route path="/timeTable" element={<TimeTable/>} />
                    <Route path="/choose" element={<Choose />} />
                    <Route path="/changeClass" element={<ChangeClass/>} />
                    <Route path="/changeClassList" element={<ChangeClassList/>} />
                    <Route path="/food" element={<Food/>} />
                    <Route path="/foodArticle/*" element={<FoodArticle/>} />
                    <Route path="/rent/*" element={<Rent/>} />
                    <Route path="/rentArticle/*" element={<RentArticle/>} />
                    <Route path="/postArticle" element={<PostArticle/>} />
                    <Route path="/changeClassArticle" element={<ChangeClassArticle/>} />
                    <Route path="/changeClassArticle" element={<ChangeClassArticle/>} />
                    <Route path="/HomePage" element={<HomePage />} />
                    <Route path="/Credit" element={<Credit />} />
                    <Route path="/Core" element={<Core />} />
                    <Route path="/Must" element={<Must />} />
                    //<Route path="/Must/*" element={<Must />} />
                    <Route path="/Graduation" element={<Graduation />} />
                    <Route path="/Search" element={<Search />} />
                    <Route path="/CourseSelection" element={<CourseSelection />} />
                    <Route path="/generalEducation" element={<GeneralEducaton />} />
                    <Route path="/favorite" element={<Favorite />} />
                    <Route path="/DetectCourse" element={<DetectCourse />} />
                    <Route path="/Chatroom/:id" element={<Chatroom />} />
                    <Route path="/modifyPost" element={<ModifyPost />} />
                    <Route path="/ChatRoomList" element={<ChatRoomList />} />
                    <Route path="/navbar" element={<navbar/> } />
                </Routes>
             
    );
}

export default App;
