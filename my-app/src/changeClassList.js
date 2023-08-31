import './changeClass';
import React from 'react';
import Modal from "./components/Modal";
import student from './img/student.png';
import back from './img/back.png';
import yolk from './img/yolk.PNG';
import redBall from './img/redBall.PNG';
import {Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleText, ArticlePostTimeRating, ArticleContainer,
   ArticleFoodContainer, ArticleAuthor, ArticlePostTime, ArticlePostRating, ArticleBody, ArticleDistance,ChangeClassCategorySelect,
   ArticleAuthorArea,ArticleAuthorImg, AlreadyArticleText, ChangeClassStatusSelect}  from './components/ArticleStyle.js';
import {Back}  from './components/Style.js';
import { Routes ,Route,Link,useNavigate,useLocation } from 'react-router-dom';
import {useEffect,useState} from "react";
import { loginUser } from './cookie';
import { getAuthToken } from "./utils";

//列出該時段有的課
const ChangeClassList=()=> {

    const [data, setData] = useState(null);
    const [isPostID, setisPostID] = useState(null);
    const [openModal, setOpenModal] = useState(false);
    const [setNotification, setSetNotification] = useState(false);
    const [haveData, setHaveData] = useState(true);
    const [alreadyChange, setAlreadyChange] = useState(false);
    let navigate = useNavigate();
    const location = useLocation();
    const { time } = location.state;
    const userInfo = loginUser();
    const token = getAuthToken();


    function Articleinfo({ author, className, category, postID, post_time, status }) {

      if(status==="未換"){
        setAlreadyChange(false);
      }
      else{
        setAlreadyChange(true);
      }
      const handleShowClassSubmit = (e) => {
        e.preventDefault();
        //const student_id = loginUser();
        const formData = {
                        studentID: userInfo,
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

      function NotChange(){
        return(
            <ArticleText onClick={handleShowClassSubmit}>
                <ArticleDistance>{category}</ArticleDistance>
                <ArticleAuthorArea>
                  <ArticleAuthorImg src={student}></ArticleAuthorImg>
                  <ArticleAuthor>{author}</ArticleAuthor>
                </ArticleAuthorArea>
                <ArticleBody>{className}</ArticleBody>
                <ArticlePostTime>{post_time}</ArticlePostTime>
            </ArticleText>
        );
      }

      function Change(){
        return(
            <AlreadyArticleText onClick={handleShowClassSubmit} disabled>
                <ArticleDistance>{status}</ArticleDistance>
                <ArticleAuthorArea>
                  <ArticleAuthorImg src={student}></ArticleAuthorImg>
                  <ArticleAuthor>{author}</ArticleAuthor>
                </ArticleAuthorArea>
                <ArticleBody>{className}</ArticleBody>
                <ArticlePostTime>{post_time}</ArticlePostTime>
            </AlreadyArticleText>
        );
      }


        return (
          <ArticleContainer>
            {alreadyChange && <Change/>}
            {!alreadyChange && <NotChange/>}
            
          </ArticleContainer>
        );
      }

      function ChangeClassList_all(){
        const handleCategoryChange = event => {
          console.log(event.target.value);

          if(event.target.value==='All'){
            fetch(`/course_change?time=${time}`)
            .then(response => response.json())
            .then(data => {
              console.log(time);
              console.log(data);
              setData(data);
              setHaveData(true);
            })
            .catch(error => {
              console.error('Error:', error);
            });
          }
          else{
            fetch(`/course_classify?category=${event.target.value}&time=${time}`)
            .then(response => {
              console.log(response.status);
              if (response.status === 200) {
                return response.json(); 
              } else {
                throw new Error('Response status not 200');
              }
            })
            .then(data => {
              console.log(data);
              if(data=="")
                setHaveData(false);
              else
                setHaveData(true);
              setData(data); 
            })
            .catch(error => {
              console.error('Error:', error);
            });

          }
          
          //setCategory(event.target.value);
        };
        return(
          <Page>
              <Pagebg>
                <Title>換課板</Title>
                <ChangeClassCategorySelect onChange={handleCategoryChange}>
                  <option >分類</option>
                  <option value='All'>全部</option>
                  <option value='PE'>體育</option>
                  <option value='general'>通識</option>
                  <option value='english'>英文</option>
                  <option value='foreign_language'>第二外語</option>
                  <option value='compulsory'>必修</option>
                  <option value='elective'>選修</option>
                </ChangeClassCategorySelect>
                <ArticleList>
                    {haveData===false && <div className='noData'>沒有資料</div>}
                    {data.map(item => (
                      <Articleinfo key={item.postId} author={item.studentID} className={item.course} category={item.category} postID={item.postId} post_time={item.post_time} status={item.status}></Articleinfo>
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
            if(data=="")
                setHaveData(false);
              else
                setHaveData(true);
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
          {!openModal &&
          <Link to='/changeClass'>
              <Back src={back} alt="回上一頁" />
          </Link>}
          {openModal && !setNotification && <Modal closeModal={setOpenModal} type={"classArticle"} postId={isPostID} studentID={userInfo} time={time} />}
          {!openModal && !setNotification && < ChangeClassList_all/>}
          
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
