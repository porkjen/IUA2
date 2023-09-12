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
import { RemainTitle, RemainContainer, RemainList, RemainText, RemainBody } from './components/Style.js';

const PreTimeTable=()=> {

    let navigate = useNavigate();
    const [data, setData] = useState('');
    const [cookies, setCookie] = useCookies(['token']);
    const [error, setError] = useState(false);
    const [preSearch, setPreSearch] = useState(false);
    const [preTable, setPreTable] = useState(true);
    const userInfo = loginUser();
    const token = getAuthToken();

    function CourseList({ list }) {

        //加入預選
        const handleAddPreCourse = (e,courseName,classNum,classTime,classRoom) =>{
            const formData = {
                //studentID:userInfo,
                p_class:courseName,
                p_classNum:classNum,
                p_time:classTime,
                p_classroom:classRoom,

            };
            fetch('/pre_curriculum', {
                method: 'POST',
                headers: { 
                    'Content-Type': 'application/json' ,
                    
                },
                body: JSON.stringify(formData)
            })
                .then(response => response.json())
                .then(data => {
                    setData(data);
                    console.log(data);
                })
                .catch(error => {
                    console.error(error);
                });
        }
        return (
            <RemainList>
                <ArticleContainer>
                    {list.map((item, index) => (
                        <RemainBody key={index}>
                            <RemainText>{item.name}&nbsp;<br/><button className='preInfo' onClick={() => handleAddPreCourse(item.name,item.classNum,item.time,item.classroom )}>加入預選</button></RemainText>
                        </RemainBody>
                    ))}
                </ArticleContainer>
            </RemainList>
        );
    }

    function PreTableShow(){
        useEffect(() => {
            if (!data) {
                const formData = {
                    //studentID:userInfo,
                };
              fetch(`/my_pre_curriculum`, {
                headers: {
                    'Content-Type': 'application/json' ,
                }
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
          }, [data, userInfo]); // 添加依賴項data、userInfo和authHeader

        return(
            <div className='preTable_day'>
                <div className='preDay'>星期一</div>
                <div className='preDay'>星期二</div>
                <div className='preDay'>星期三</div>
                <div className='preDay'>星期四</div>
                <div className='preDay'>星期五</div>
                <div className='preDay'>星期六</div>
                <div className='preDay'>星期日</div>
            </div>
        );
    }



    function PreTimeTable() {
        const [searchTerm, setSearchTerm] = useState('');
        const [searchResults, setSearchResults] = useState([]);
        const [data, setData] = useState('');


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


        /*
        useEffect(() => {
            if (searchTerm) {          
              fetch(`/search?query=${searchTerm}`)
                .then((response) => response.json())
                .then((data) => {
                    setSearchResults(data);
                    console.log(data);
                })
                .catch((error) => {
                  console.error('搜索错误:', error);
                });
            } else {
              // 清除搜索结果
              setSearchResults([]);
            }
          }, [searchTerm]);
          <CourseList list={data} />
          */
        

      return (
        
        <div className="PreTimeTable">   
            <Link to='/CourseSelection'>
              <Back src={back} alt="回上一頁" />
            </Link> 
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
