//import './changeClass.css';
import React from 'react';
import {Page, Pagebg, Title, PostArticleBtn, ArticleList, ArticleText, ArticleContainer, ArticleAuthor, ArticleBody}  from './components/ArticleStyle.js';
import { Routes ,Route,Link ,useNavigate} from 'react-router-dom';
import {useEffect,useState} from "react";

const Rent=()=> {
    
    const [data, setData] = useState(null);
    let navigate = useNavigate();

    function Articleinfo({ author, text, postID }) {

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
                  <ArticleBody>{text}</ArticleBody>
              </ArticleText>
            
          </ArticleContainer>
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
            <Pagebg>
                <Title>租屋板</Title>
                <Link to='/postArticle'>
                  <PostArticleBtn>我要發文</PostArticleBtn>
                </Link>
                <ArticleList>
                    {data.map(item => (
                      <Articleinfo key={item.postId} author={item.name} text={item.title} postID={item.postId}></Articleinfo>
                    ))}
                    <Articleinfo author={"evelyn"} text={"I love you"}></Articleinfo>
                    <Articleinfo author={"vvvvvvvv"} text={"I love you soooo such"}></Articleinfo>
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