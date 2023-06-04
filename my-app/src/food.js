//import './changeClass.css';
import React from 'react';
import {Page, Pagebg, Title, PostArticleBtn, ArticleList, ArticleText, ArticleContainer, ArticleAuthor, ArticleBody}  from './components/ArticleStyle.js';
import { Routes ,Route,Link } from 'react-router-dom';
import {useState} from "react";

const Food=()=> {
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


    function Food() {
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
                <Title>美食板</Title>
                <Link to='/postArticle'>
                  <PostArticleBtn >我要發文</PostArticleBtn>
                </Link>
                <ArticleList>
                    <Articleinfo author={"singyi"} text={"I love you"}></Articleinfo>
                    <Articleinfo author={"vvvvvvvv"} text={"I love you soooo such"}></Articleinfo>
                </ArticleList>
            </Pagebg>
        </Page>
      );
    }

    return (
           
                <Routes>
                    <Route path="/" element={<Food />} />
                </Routes>
             
    );
}

export default Food;
