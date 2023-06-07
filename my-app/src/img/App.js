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
import Food from './food';
import Rent from './rent';
import PostArticle from './postArticle';
import ChangeClassArticle from './changeClassArticle';
import HomePage from './homePage';
import Credit from './Credit';
import Core from './Core';
import Must from './Must';
import Graduation from './Graduation';
import Search from './Search';

const App=()=> {
   
    return (
           
                <Routes>
                    <Route path="/" element={<Cover/>} />
                    <Route path="/signIn" element={<SignIn/>} />
                    <Route path="/remain" element={<Remain/>} />
                    <Route path="/nickName" element={<NickName/>} />
                    <Route path="/timeTable" element={<TimeTable/>} />
                    <Route path="/changeClass" element={<ChangeClass/>} />
                    <Route path="/food" element={<Food/>} />
                    <Route path="/rent" element={<Rent/>} />
                    <Route path="/postArticle" element={<PostArticle/>} />
                    <Route path="/changeClassArticle" element={<ChangeClassArticle/>} />
                    <Route path="/changeClassArticle" element={<ChangeClassArticle/>} />
                    <Route path="/HomePage" element={<HomePage />} />
                    <Route path="/Credit" element={<Credit />} />
                    <Route path="/Core" element={<Core />} />
                    <Route path="/Must" element={<Must />} />
                    <Route path="/Graduation" element={<Graduation />} />
                    <Route path="/Search" element={<Search />} />
                    <Route path="/navbar" element={<navbar/> } />
                </Routes>
             
    );
}

export default App;
