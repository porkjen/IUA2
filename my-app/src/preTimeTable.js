import './preTimeTable.css';
import React from 'react';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import back from './img/back.png';
import {Back}  from './components/Style.js';
import { Routes ,Route } from 'react-router-dom';
import {useNavigate} from 'react-router-dom';
import {useState, useEffect} from "react";
import { useCookies } from 'react-cookie';
import { loginUser } from './cookie';
import { getAuthToken } from "./utils";
import { Page, Pagebg, Title,ArticleContainer} from './components/ArticleStyle.js';
import { PreText ,PreList, PreClassBody, PreSearchList, PreSearchBody} from './components/Style.js';
import Modal from 'react-modal';
const PreTimeTable=()=> {

    let navigate = useNavigate();
    const [modalIsOpen, setModalIsOpen] = useState(false); // 控制彈出視窗的狀態
    const [data, setData] = useState([]);
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
    const [addSuccess, setAddSuccess] = useState(false);
    
    const userInfo = loginUser();
    const token = getAuthToken();


     

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

    function CourseList({ list }) {
        //加入預選
        const handleAddPreCourse = (courseName,classNum,classTime,classRoom,category,teacher,target,syllabus,evaluation) =>{
            console.log(classTime);
            setAddSuccess(true);
            const formData = {
                studentID:userInfo,
                pre_info:
                    {
                        p_class:courseName,
                        p_classNum:classNum,
                        p_time:classTime,
                        p_classroom:classRoom,
                        p_category :category,
                        p_teacher:teacher,
                        p_target:target ,
                        p_evaluation:evaluation,
                        p_syllabus:syllabus
        
                    }

            };
            fetch('/pre_curriculum', {
                method: 'POST',
                headers: { 
                    'Content-Type': 'application/json' ,
                    'Authorization': `${token}`,
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
                    <div className='addSucText'>
                        加入成功!
                    </div>
                }
                    {list.map((item, index) => (
                        <PreSearchBody key={index}>
                            <PreText>{item.name}&nbsp;<br/>{item.time}<br/>
                            <button className='preInfol' onClick={() => handlePreCourseInfo(item.name,item.classNum,item.time,item.classroom,item.category,item.teacher,item.target,item.syllabus,item.evaluation)}>詳細資訊</button>
                            <button className='preInfor' onClick={() => handleAddPreCourse(item.name,item.classNum,item.time,item.classroom,item.category,item.teacher,item.target,item.syllabus,item.evaluation)}>加入預選</button></PreText>
                        </PreSearchBody>
                    ))}
                </ArticleContainer>
            </PreSearchList>
        );
    }

    function PreShowData({ShowData}){
        //刪除預選
        const handleDelPreCourse = (classNum) =>{
            console.log(classNum);
            fetch(`/cancel_pre_curriculum?studentID=${userInfo}&p_classNum=${classNum}`, {
                method: 'DELETE',
                headers: { 
                    'Content-Type': 'application/json' ,
                    'Authorization': `${token}`,
                    
                },
            })
                .then(response => response.status)
                .catch(error => {
                    console.error(error);
                });
                window.location.reload();
        }
        return(
            <ArticleContainer>
                {ShowData.map((item, index) => (
                    <PreClassBody key={index}>
                        <PreText>{item.p_class}&nbsp;{item.p_time}<br/>
                        <button className='preShowInfo' onClick={() => handlePreCourseInfo(item.p_name,item.p_classNum,item.p_time,item.p_classroom,item.p_category,item.p_teacher,item.p_target,item.p_syllabus,item.p_evaluation)}>詳細資訊</button>
                        <button className='preDel' onClick={() => handleDelPreCourse(item.p_classNum)} >刪除</button></PreText>
                    </PreClassBody>
                ))}
            </ArticleContainer>
        );
    }

    function PreTableShow(){
        const [PreTableShowData, setPreTableShowData] = useState('');
        const [PreTableShowDataMon, setPreTableShowDataMon] = useState('');
        const [PreTableShowDataTue, setPreTableShowDataTue] = useState('');
        const [PreTableShowDataWed, setPreTableShowDataWed] = useState('');
        const [PreTableShowDataThr, setPreTableShowDataThr] = useState('');
        const [PreTableShowDataFri, setPreTableShowDataFri] = useState('');
        const [PreTableShowDataSat, setPreTableShowDataSat] = useState('');
        const [PreTableShowDataSun, setPreTableShowDataSun] = useState('');
        useEffect(() => {
            if (!PreTableShowData) {
                const formData = {
                    studentID:userInfo,
                };
              fetch(`/my_pre_curriculum`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json' ,
                    'Authorization': `${token}`,
                },
                body: JSON.stringify(formData)
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
                console.log(data[2]);
                //setPreTableShowData(data);
                setPreTableShowDataMon(data[1]);
                setPreTableShowDataTue(data[2]);
                setPreTableShowDataWed(data[3]);
                setPreTableShowDataThr(data[4]);
                setPreTableShowDataFri(data[5]);
                setPreTableShowDataSat(data[6]);
                setPreTableShowDataSun(data[7]);
              })
              .catch(error => {
                console.error('Error:', error);
              });
            }
          }, [PreTableShowData]); // 添加依賴項data、userInfo和authHeader

        return(
            <div className='preTable_day'>
                <PreList>
                <div className='preDay'>星期一</div>
                {PreTableShowDataMon && <PreShowData ShowData={PreTableShowDataMon}/>}
                <div className='preDay'>星期二</div>
                {PreTableShowDataTue && <PreShowData ShowData={PreTableShowDataTue}/>}
                <div className='preDay'>星期三</div>
                {PreTableShowDataWed && <PreShowData ShowData={PreTableShowDataWed}/>}
                <div className='preDay'>星期四</div>
                {PreTableShowDataThr && <PreShowData ShowData={PreTableShowDataThr}/>}
                <div className='preDay'>星期五</div>
                {PreTableShowDataFri && <PreShowData ShowData={PreTableShowDataFri}/>}
                <div className='preDay'>星期六</div>
                {PreTableShowDataSat && <PreShowData ShowData={PreTableShowDataSat}/>}
                <div className='preDay'>星期日</div>
                {PreTableShowDataSun && <PreShowData ShowData={PreTableShowDataSun}/>}
                </PreList>
            </div>
        );
    }



    function PreTimeTable() {
        
        const [searchResults, setSearchResults] = useState([]);
        const [predata, setPreData] = useState([]);
        const [searchTerm, setSearchTerm] = useState('');
        const [searchCategory, setSearchCategory] = useState('general');

        const closeModal = () => {
            setModalIsOpen(false);
          };

        const handlePTTFunctionSelect  = event => {

            if(event.target.value==='preTimeTable'){
                setPreTable(true);
                setPreSearch(false);
            }
            else if(event.target.value==='preSearch'){
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
            <Link to='/timeTableChoose'>
              <Back src={back} alt="回上一頁" />
            </Link> 
            {!modalIsOpen && <Title>預選課表</Title>}
            {!modalIsOpen && 
            <select className='preTtimeTableSelect' onChange={handlePTTFunctionSelect}>
                <option>功能選單</option>
                  <option value='preSearch'>預選課程查詢</option>
                  <option value='preTimeTable'>預選課表</option>
            </select>
            }
                {preTable && 
                    <div>
                        <PreTableShow/>
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
                    <Route path="/" element={<PreTimeTable />} />
                </Routes>
             
    );
}

export default PreTimeTable;
