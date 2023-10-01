import './DetectCourse.css';
import './preTimeTable.css';
import React, { useEffect, useState } from 'react';
import back from './img/back.png';
import { Back } from './components/Style.js';
import logo from './img/IUAlogo.png';
import { Page, Pagebg, Title, ArticleList, ArticleText, ArticleContainer } from './components/ArticleStyle.js';
import { PreText ,PreList,PreClassBody,PreSearchBody, PreSearchList} from './components/Style.js';
import { BrowserRouter as Router, Link } from 'react-router-dom';
import { Routes, Route } from 'react-router-dom';
import { loginUser } from './cookie';
import { useCookies } from 'react-cookie';
import { getAuthToken } from './utils';
import Modal from 'react-modal';

const DetectCourse = () => {
  const userInfo = loginUser();
  const token = getAuthToken();
  const [startDetect, setStartDetect] = useState(false);
  const [modalIsOpen, setModalIsOpen] = useState(false); // 控制彈出視窗的狀態
  const [data, setData] = useState([]);
  const [courseNum, setCourseNum] = useState('');
  const [pcourseName, setCourseName] = useState('');
  const [pclassNum, setClassNum] = useState('');
  const [pclassTime, setClassTime] = useState('');
  const [pclassRoom, setClassRoom] = useState('');
  const [pcategory, setCategory] = useState('');
  const [pteacher, setTeacher] = useState('');
  const [ptarget, setTarget] = useState('');
  const [psyllabus, setSyllabus] = useState('');
  const [pevaluation, setEvaluation] = useState('');
  const [cookies, setCookie] = useCookies(['token']);
  const [error, setError] = useState(false);
  const [preSearch, setPreSearch] = useState(false);
  const [preTable, setPreTable] = useState(true);
  const [del, setDel] = useState(false);
  const [addSuccess, setAddSuccess] = useState(false);

  const handlePreCourseInfo = (courseName,classNum,classTime,classRoom,category,teacher,target,syllabus,evaluation) =>{

    setModalIsOpen(true);
    setCourseName(courseName);
    setClassNum(classNum);
    setClassTime(classTime);
    setClassRoom(classRoom);
    setCategory(category);
    setTeacher(teacher);
    setTarget(target);
    setSyllabus(syllabus);
    setEvaluation(evaluation);

  };

  function DelSuc({classNum}){
    console.log("ok");
    const no = (e) => {
      setDel(false);
    }
    const ok = (e) => {
      console.log(del);
      fetch(`/delete_detect_course?studentID=${userInfo}&number=${classNum}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      })
        .catch((error) => {
          console.error(error);
        });
        setDel(false);
    }


    return(
      <div className='delDet'>
        <label className='delText'>確定要刪除嗎!</label>
        <button className='delYes' onClick={() =>ok()}>確定</button>
        <button className='delOk' onClick={() =>no()}>關閉</button>
      </div>
    );
  }


  function DetectData({ShowData}){
    //刪除預選
    const handleCourseDetectDelSubmit = (classNum) => {
      setDel(true);
      setCourseNum(classNum);
    };
    return(
      <div>
         {del && <DelSuc classNum={courseNum}/>}
         <ArticleContainer>
            {ShowData.map((item, index) => (
                <PreClassBody key={index}>
                    <PreText>{item.name}<br/>
                    <button className='detDel' onClick={() => handleCourseDetectDelSubmit(item.number)} >刪除</button></PreText>
                </PreClassBody>
            ))}
        </ArticleContainer>
      </div>
    );
}

  function MyDetectCourse(){
    const [data,setData]= useState('');
    useEffect(() => {
      if (!data) {
          const formData = {
              studentID:userInfo,
          };
        fetch(`/load_detect_course?studentID=${userInfo}`, {
          method: 'GET',
          headers: {
              'Content-Type': 'application/json' ,
          },
        })
        .then(response => {
          console.log(response.status);
          if (response.status === 200) {
            
            return response.json(); // 解析回應為 JSON
          } else {
            throw new Error('Unauthorized'); // 或者在其他狀況處理錯誤
          }
        })
        .then(data => {
          console.log(data);
          setData(data);
        })
        .catch(error => {
          console.error('Error:', error);
        });
      }
    }, [data]); // 添加依賴項data、userInfo和authHeader
    return(
      <div>
        <PreList>
          {data && <DetectData ShowData={data}/>}
        </PreList>
      </div>

    );
  }
  

  function DetectCourse_all({ list }) {
    const handleCourseDetectDelSubmit = (classNum) => {
      console.log(classNum);
      fetch(`/delete_detect_course?studentID=${userInfo}&number=${classNum}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      })
        .then((response) => response.status)
        .catch((error) => {
          console.error(error);
        });
    };

    return (
      <PreSearchList>
                <ArticleContainer>
          {list.map((item, index) => (
            <PreSearchBody key={index}>
              <PreText>
                {item.number}&nbsp;<br />
                <button className='detectedDelBtn' onClick={() => handleCourseDetectDelSubmit(item.number)}>刪除</button>
              </PreText>
            </PreSearchBody>
          ))}
       </ArticleContainer>
            </PreSearchList>
    );
  }

  function CourseList({ list }) {
    //加入預選
    const handleAddPreCourse = (courseName,classNum) =>{
      console.log(courseName);
      setAddSuccess(true);
        const formData = {
            studentID:userInfo,
            courseNumber:classNum,
            courseName:courseName

        };
        fetch('/add_detect_course', {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json' ,
            },
            body: JSON.stringify(formData)
        })
            .then(response => response.status)
           
            .catch(error => {
                console.error(error);
            });
            const timer = setTimeout(() => {
              setAddSuccess(false);
            }, 500);

          return () => clearTimeout(timer);
    }
    return (
        <PreSearchList>
            <ArticleContainer>
              {addSuccess &&
                    <div className='Det_addSucText'>
                        加入成功!
                    </div>
                }
                {list.map((item, index) => (
                    <PreSearchBody key={index}>
                        <PreText>{item.name}&nbsp;<br/>{item.time}<br/>
                        <button className='preInfol' onClick={() => handlePreCourseInfo(item.name,item.classNum,item.time,item.classroom,item.category,item.teacher,item.target,item.syllabus,item.evaluation)}>詳細資訊</button>
                         <button className='preInfor' onClick={() => handleAddPreCourse(item.name,item.classNum)}>加入偵測</button>
                        </PreText>
                    </PreSearchBody>
                ))}
            </ArticleContainer>
        </PreSearchList>
    );
}

  function DetectCourse() {
    const [searchResults, setSearchResults] = useState([]);
        const [predata, setPreData] = useState([]);
        const [searchTerm, setSearchTerm] = useState('');
        const [searchCategory, setSearchCategory] = useState('general');

        const closeModal = () => {
            setModalIsOpen(false);
            
          };

        const handlePTTFunctionSelect  = event => {

            if(event.target.value==='detCourse'){
                setPreTable(true);
                setPreSearch(false);
            }
            else if(event.target.value==='detSearch'){
                setPreSearch(true);
                setPreTable(false);
            }
    
          };

          const handlePTTCategorySelect  = event => {
            setSearchCategory(event.target.value);
          };


        
        useEffect(() => {
            console.log(searchCategory);
            if (searchTerm||searchCategory||searchCategory=='') {          
              fetch(`/pre_curriculum_search?category=${searchCategory}&name=${searchTerm}`)
                .then((response) => response.json())
                .then((data) => {
                    console.log(data);
                    setPreData(data);
                })
                .catch((error) => {
                  console.error('error:', error);
                });
            } else {
           
              setSearchResults([]);
            }
          }, [searchTerm,searchCategory,data]);
          
        

      return (
        
        <div className="PreTimeTable">   
            <Link to='/CourseSelection'>
              <Back src={back} alt="回上一頁" />
            </Link>
            {!modalIsOpen && <Title>偵測課程</Title>}
            {!modalIsOpen && 
            <select className='preTtimeTableSelect' onChange={handlePTTFunctionSelect}>
                <option>功能選單</option>
                  <option value='detSearch'>偵測課程查詢</option>
                  <option value='detCourse'>我的偵測課程</option>
            </select>
            }
                {preTable && 
                    <div>
                      <MyDetectCourse/>
                        <Modal
                            isOpen={modalIsOpen}
                            onRequestClose={closeModal}
                            contentLabel="課程詳細資訊"
                        >
                            <div>
                                <h2>{pcourseName}</h2>
                                <p>課程代號：{pclassNum}</p>
                                <p>選課類別：{pcategory}</p>
                                <p>授課教師：{pteacher}</p>
                                <p>上課時間：{pclassTime}</p>
                                <p>上課地點：{pclassRoom}</p>
                                <p>教學目標：<br/>{ptarget}</p>
                                <p>教學進度：<br/>{psyllabus}</p>
                                <p>評分方式:<br/>{pevaluation}</p>
                            </div>
                            <button onClick={closeModal}>關閉</button>
                        </Modal>
                    </div>
                    
                    
                }
                {preSearch &&
                    <div>
                        <select className='preCategorySelect' onChange={handlePTTCategorySelect} value={searchCategory}>
                            <option>分類</option>
                            <option value='general'>通識</option>
                            <option value='PE'>體育</option>
                            <option value='foreign_language'>第二外語</option>
                            <option value='english'>英語</option>
                            <option value='major'>必選修</option>
                            <option value=''>全部</option>
                        </select>
                        <input type="text" className='PreTimeTable_input' placeholder="輸入關鍵字" value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)}/>
                        <CourseList list={predata} />
                        <Modal
                            isOpen={modalIsOpen}
                            onRequestClose={closeModal}
                            contentLabel="課程詳細資訊"
                            searchCategory={searchCategory}
                        >
                            <div>
                                <h2>{pcourseName}</h2>
                                <p>課程代號：{pclassNum}</p>
                                <p>選課類別：{pcategory}</p>
                                <p>授課教師：{pteacher}</p>
                                <p>上課時間：{pclassTime}</p>
                                <p>上課地點：{pclassRoom}</p>
                                <p>教學目標：<br/>{ptarget}</p>
                                <p>教學進度：<br/>{psyllabus}</p>
                                <p>評分方式:<br/>{pevaluation}</p>
                            </div>
                            <button onClick={closeModal}>關閉</button>
                        </Modal>
                    </div>

                }
            
        </div>
      );
  }

  return (
    <Routes>
      <Route path='/' element={<DetectCourse />} />
    </Routes>
  );
};

export default DetectCourse;
