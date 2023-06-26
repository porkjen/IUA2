import './rent.css';
import React from 'react';
import Modal from "./components/Modal";
import house from './img/house.png';
import connection from './img/connection.png';
import {Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleText, ArticleContainer, ArticleRentContainer, ArticleAuthor, ArticlePostTime, ArticleBody}  from './components/ArticleStyle.js';
import { Routes ,Route,Link ,useNavigate} from 'react-router-dom';
import {useEffect,useState} from "react";

const Rent=()=> {
    
    const [data, setData] = useState(null);
    const [openModal, setOpenModal] = useState(false);
    let navigate = useNavigate();

    function Articleinfo({ author, time, text, postID }) {

      const handleShowHouseSubmit = (e) => {
        e.preventDefault();
        //const student_id = loginUser();
        const formData = {
                        studentID: "00957025",
                        postId : postID,
                      };
          fetch('/rent_full_post', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(formData)})
                  .then(response => response.json())
                  .then(data => {
                        console.log(data);})
                  .catch(error => {
                        console.error(error);});
                       //Form submission happens here
            navigate("/rentArticle", {
              state: {
                studentID: "00957025",
                postId : postID,},});
      }

        return (
          <ArticleContainer>
              <ArticleText onClick={handleShowHouseSubmit}>
                  <ArticleAuthor>{author}</ArticleAuthor>
                  <ArticlePostTime>{time}</ArticlePostTime>
                  <ArticleBody>{text}</ArticleBody>
              </ArticleText>
          </ArticleContainer>
        );
    }

    function Rent_all(){
      return(
        <Page>
            <Pagebg>
                  <Title>租屋板</Title>
                  <img className='rent_house' src={house}/>
                  <img className='rent_connection' src={connection}/>
                <Link to='/postArticle'>
                  <PostArticleBtn>我要發文</PostArticleBtn>
                </Link>
                  <ChooseArticleBtn onClick={()=> setOpenModal(true)}>篩選貼文</ChooseArticleBtn>
                <ArticleList>
                    {data.map(item => (
                      <Articleinfo key={item.postId} author={item.name} time={item.post_time} text={item.title} postID={item.postId}></Articleinfo>
                    ))}
                    <Articleinfo author={"evelyn"} text={"I love you"}></Articleinfo>
                    <Articleinfo author={"vvvvvvvv"} text={"I love you soooo such"}></Articleinfo>
                </ArticleList>
            </Pagebg>
        </Page>
      );
    }

   


    function Rent() {
      
      useEffect(() => {
        if (!data) {
          fetch('/rent_load')
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
          {openModal && <Modal closeModal={setOpenModal} type={"rent"}/>}
          {!openModal && < Rent_all/>}
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
/*

useEffect(() => {
        fetch('/rent_load')
          .then(response => response.json())
          .then(data => setData(data))
          .catch(error => {
            console.error('Error:', error);
          });
      }, []);

      if (!data) {
        return <div>Loading...</div>;
      }

{data.map(item => (
        <Articleinfo author={item.name} text={item.title}></Articleinfo>
      ))}

*/