//import './changeClass.css';
import React from 'react';
import {Page, Pagebg, Title, PostArticleBtn, ArticleList, ArticleText, ArticleContainer, ArticleAuthor, ArticleBody}  from './components/ArticleStyle.js';
import { Routes ,Route,Link } from 'react-router-dom';
import {useState} from "react";

const Rent=()=> {
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


    function Rent() {
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
                <Title>租屋板</Title>
                <Link to='/postArticle'>
                  <PostArticleBtn>我要發文</PostArticleBtn>
                </Link>
                <ArticleList>
                    <Articleinfo author={"evelyn"} text={"I love you"}></Articleinfo>
                    <Articleinfo author={"vvvvvvvv"} text={"I love you soooo such"}>hiiiii</Articleinfo>
                </ArticleList>
            </Pagebg>
        </Page>
      );
    }

    return (
           
                <Routes>
                    <Route path="/" element={<Rent />} />
                </Routes>
             
    );
}

export default Rent;
