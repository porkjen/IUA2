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
import { RemainTitle, RemainContainer, RemainList, RemainText, RemainBody,PreBody,PreText ,PreList, PreClassBody, PreSearchList, PreSearchBody} from './components/Style.js';

const PreTimeTable=()=> {

    let navigate = useNavigate();
    const [data, setData] = useState([]);
    const [cookies, setCookie] = useCookies(['token']);
    const [error, setError] = useState(false);
    const [preSearch, setPreSearch] = useState(false);
    const [preTable, setPreTable] = useState(true);
    const userInfo = loginUser();
    const token = getAuthToken();

    function CourseList({ list }) {

        //加入預選
        const handleAddPreCourse = (courseName,classNum,classTime,classRoom,category,teacher,target,syllabus,evaluation) =>{
            console.log(classTime);
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
                },
                body: JSON.stringify(formData)
            })
                .then(response => response.status)
               
                .catch(error => {
                    console.error(error);
                });
        }
        return (
            <PreSearchList>
                <ArticleContainer>
                    {list.map((item, index) => (
                        <PreSearchBody key={index}>
                            <PreText>{item.name}&nbsp;<br/><button className='preInfo' onClick={() => handleAddPreCourse(item.name,item.classNum,item.time,item.classroom,item.category,item.teacher,item.target,item.syllabus,item.evaluation)}>加入預選</button></PreText>
                        </PreSearchBody>
                    ))}
                </ArticleContainer>
            </PreSearchList>
        );
    }

    function PreShowData({ShowData}){
        //刪除預選
        const handleDelPreCourse = (e,classNum) =>{
            console.log(classNum);
            fetch(`/cancel_pre_curriculum?studentID=${userInfo}&p_classNum=${classNum}`, {
                method: 'DELETE',
                headers: { 
                    'Content-Type': 'application/json' ,
                    
                },
            })
                .then(response => response.json())
                .catch(error => {
                    console.error(error);
                });
        }
        return(
            <ArticleContainer>
                {ShowData.map((item, index) => (
                    <PreClassBody key={index}>
                        <RemainText>{item.p_class}&nbsp;{item.p_time}<br/><button onClick={() => handleDelPreCourse(item.p_classNum)} className='preDel'>刪除</button></RemainText>
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
                    studentID:"00957025",
                };
              fetch(`/my_pre_curriculum`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json' ,
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
                console.log(data[1]);
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
        const [searchTerm, setSearchTerm] = useState('');
        const [searchResults, setSearchResults] = useState([]);
        const [predata, setPreData] = useState([]);


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


        
        useEffect(() => {
            if (searchTerm) {          
              fetch(`/pre_curriculum_search?category=&name=${searchTerm}`)
                .then((response) => response.json())
                .then((data) => {
                    setSearchResults(data);
                    console.log(data);
                    setPreData(data);
                })
                .catch((error) => {
                  console.error('搜索错误:', error);
                });
            } else {
           
              setSearchResults([]);
            }
          }, [searchTerm]);
          
        

      return (
        
        <div className="PreTimeTable">   
            <Link to='/CourseSelection'>
              <Back src={back} alt="回上一頁" />
            </Link> 
            <Title>預選課表</Title>
            <select className='preTtimeTableSelect' onChange={handlePTTFunctionSelect}>
                <option>功能選單</option>
                  <option value='preSearch'>預選課程查詢</option>
                  <option value='preTimeTable'>預選課表</option>
            </select>
                {preTable && 
                    <div>
                        <PreTableShow/>
                    </div>
                    
                }
                {preSearch &&
                    <div>
                        <input type="text" className='PreTimeTable_input' placeholder="輸入關鍵字" value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)}/>
                        <CourseList list={predata} />
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
