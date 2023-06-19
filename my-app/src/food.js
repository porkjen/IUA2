//import './changeClass.css';
import React from 'react';
import Modal from "./components/Modal";
import {Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleText, ArticleContainer, ArticleAuthor, ArticleBody}  from './components/ArticleStyle.js';
import { Routes ,Route,Link } from 'react-router-dom';
import {useState} from "react";

const Food=()=> {

  const [openModal, setOpenModal] = useState(false);
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

      function Food_all(){
        return(
          <Page>
              <Pagebg>
                <Title>美食板</Title>
                <Link to='/postArticle'>
                  <PostArticleBtn >我要發文</PostArticleBtn>
                </Link>
                <ChooseArticleBtn onClick={()=> setOpenModal(true)}>篩選貼文</ChooseArticleBtn>
                <ArticleList>
                    <Articleinfo author={"singyi"} text={"好時機"}></Articleinfo>
                    <Articleinfo author={"vvvvvvvv"} text={"頂咖哩"}></Articleinfo>
                </ArticleList>
            </Pagebg>
          </Page>
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
          {openModal && <Modal closeModal={setOpenModal} type={"food"}/>}
          {!openModal && < Food_all/>}
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
