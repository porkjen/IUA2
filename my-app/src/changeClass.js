//import './changeClass.css';
import React from 'react';
import {Page, Pagebg, Title, PostArticleBtn, ArticleList, ArticleText, ArticleContainer, ArticleAuthor, ArticleBody}  from './components/ArticleStyle.js';
import { Routes ,Route,Link } from 'react-router-dom';
import {useState} from "react";

const ChangeClass=()=> {
    function Articleinfo({ author, text }) {
        return (
          <ArticleContainer>
            <ArticleText>
                <ArticleAuthor>{author}</ArticleAuthor>
                <ArticleBody>{text}</ArticleBody>
            </ArticleText>
          </ArticleContainer>
        );
      }


    function ChangeClass() {
      /*const [nickName_id, setNickName_id] = useState("");
      const handleChange = event => {
        setNickName_id(event.target.nickName_id);
      };
      const handleSubmit = (event) => {
        event.preventDefault();
        alert(`The nickName you entered was: ${nickName_id}`)
      }*/
      return (
        <Page>
            <Pagebg>
                <Title>換課板</Title>
                <Link to='/postArticle'>
                  <PostArticleBtn>我要發文</PostArticleBtn>
                </Link>
                <ArticleList>
                    <Articleinfo author={"evelyn"} text={"球球排球課"}></Articleinfo>
                    <Articleinfo author={"vvvvvvvvv"} text={"我要涼課"}>hiiiii</Articleinfo>
                </ArticleList>
            </Pagebg>
        </Page>
      );
    }

    return (
           
                <Routes>
                    <Route path="/" element={<ChangeClass />} />
                </Routes>
             
    );
}

export default ChangeClass;
