//import './food.css';
import React from 'react';
import Modal from "./components/Modal";
import yolk from './img/yolk.PNG';
import redBall from './img/redBall.PNG';
import {Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleText, ArticlePostTimeRating, ArticleContainer, ArticleFoodContainer, ArticleAuthor, ArticlePostTime, ArticlePostRating, ArticleBody}  from './components/ArticleStyle.js';
import { Routes ,Route,Link,useNavigate,useLocation } from 'react-router-dom';
import {useEffect,useState} from "react";

const ChangeClassList=()=> {

    const [data, setData] = useState(null);
    const [openModal, setOpenModal] = useState(false);
    let navigate = useNavigate();
    const location = useLocation();
    const { studentID, time } = location.state;

    function Articleinfo({ author, post_time, store, rating, postID }) {

      const handleShowFoodSubmit = (e) => {
        e.preventDefault();
        //const student_id = loginUser();
        const formData = {
                        studentID: "00957025",
                        time:time,
                      };
          fetch('/food_full_post', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(formData)})
                  .then(response => response.json())
                  .then(data => {
                        console.log(data);})
                  .catch(error => {
                        console.error(error);});
                       //Form submission happens here
            navigate("/foodArticle", {
              state: {
                studentID: "00957025",
                postId : postID,},});
      }


        return (
          <ArticleContainer>
            <ArticleText onClick={handleShowFoodSubmit}>
                <ArticleAuthor>{author}</ArticleAuthor>
                <ArticleBody>{store}</ArticleBody>
                <ArticlePostTime>{rating+"顆星 " + post_time}</ArticlePostTime>
            </ArticleText>
          </ArticleContainer>
        );
      }

      function ChangeClassList_all(){
        return(
          <Page>
              <Pagebg>
                <Title>換課板</Title>
                <Link to='/postArticle'>
                  <PostArticleBtn >我要發文</PostArticleBtn>
                </Link>
                <ArticleList>
                    {data.map(item => (
                      <Articleinfo key={item.postId} author={item.studentID} class={item.class} store={item.store} rating={item.rating} postID={item.postId}></Articleinfo>
                    ))}
                </ArticleList>
            </Pagebg>
          </Page>
        );
      }
  


    function ChangeClassList() {
      useEffect(() => {
        if (!data) {
          fetch('/course_change?time='+time)
            .then(response => response.json())
            .then(data => setData(data))
            .catch(error => {
              console.error('Error:', error);
            });
        }
      }, [data]); // 添加依賴項data

      if (!data) {
        return <div>Loading...</div>;
      }

      return (
        <Page>
          {openModal && <Modal closeModal={setOpenModal} type={"food"}/>}
          {!openModal && < ChangeClassList_all/>}
        </Page>
      );
    }

    return (
           
                <Routes>
                    <Route path="/" element={<ChangeClassList />} />
                </Routes>
             
    );
}

export default ChangeClassList;
