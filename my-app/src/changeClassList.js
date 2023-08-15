//import './food.css';
import React from 'react';
import Modal from "./components/Modal";
import yolk from './img/yolk.PNG';
import redBall from './img/redBall.PNG';
import {Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleText, ArticlePostTimeRating, ArticleContainer,
   ArticleFoodContainer, ArticleAuthor, ArticlePostTime, ArticlePostRating, ArticleBody, ArticleDistance,ChangeClassCategorySelect}  from './components/ArticleStyle.js';
import { Routes ,Route,Link,useNavigate,useLocation } from 'react-router-dom';
import {useEffect,useState} from "react";

//列出該時段有的課
const ChangeClassList=()=> {

    const [data, setData] = useState(null);
    const [isPostID, setisPostID] = useState(null);
    const [openModal, setOpenModal] = useState(false);
    let navigate = useNavigate();
    const location = useLocation();
    const { studentID, time } = location.state;

    function Articleinfo({ author, className, category, postID }) {

      const handleShowClassSubmit = (e) => {
        e.preventDefault();
        //const student_id = loginUser();
        const formData = {
                        studentID: "00957025",
                        time:time,
                      };
          fetch('/course_full_post', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(formData)})
                  .then(response => response.json())
                  .then(data => {
                        console.log(data);})
                  .catch(error => {
                        console.error(error);});
            setisPostID(postID);
            setOpenModal(true);
            /*
            navigate("/changeClassArticle", {
              state: {
                studentID: "00957025",
                postId : postID,
                time:time},});
            */
      }


        return (
          <ArticleContainer>
            <ArticleText onClick={handleShowClassSubmit}>
                <ArticleDistance>{category}</ArticleDistance>
                <ArticleAuthor>{author}</ArticleAuthor>
                <ArticleBody>{className}</ArticleBody>
            </ArticleText>
          </ArticleContainer>
        );
      }

      function ChangeClassList_all(){
        const handleCategoryChange = event => {
          fetch(` /course_classify?category=${event.target.value}`)
          .then(response => response.json())
          .then(data => {
            console.log(data);
            setData(data);
          })
          .catch(error => {
            console.error('Error:', error);
          });
          //setCategory(event.target.value);
        };
        return(
          <Page>
              <Pagebg>
                <Title>換課板</Title>
                <Link to='/postArticle'>
                  <PostArticleBtn >我要發文</PostArticleBtn>
                </Link>
                <ChangeClassCategorySelect onChange={handleCategoryChange}>
                  <option >分類</option>
                  <option value='PE'>體育</option>
                  <option value='general'>通識</option>
                  <option value='english'>英文</option>
                  <option value='foreign_language'>第二外語</option>
                  <option value='compulsory'>必修</option>
                  <option value='elective'>選修</option>
                </ChangeClassCategorySelect>
                <ArticleList>
                    {data.map(item => (
                      <Articleinfo key={item.postId} author={item.studentID} className={item.course} category={item.category} postID={item.postId}></Articleinfo>
                    ))}
                </ArticleList>
            </Pagebg>
          </Page>
        );
      }
  


    function ChangeClassList() {
      useEffect(() => {
        if (!data) {
          fetch(`/course_change?time=${time}`)
          .then(response => response.json())
          .then(data => {
            console.log(time);
            console.log(data);
            setData(data);
          })
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
          {openModal && <Modal closeModal={setOpenModal} type={"classArticle"} postId={isPostID} studentID={studentID} time={time} />}
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
