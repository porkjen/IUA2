import './generalEducaton.css';
import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';
import back from './img/back.png';
import { GEinfo } from './components/Style';

const GeneralEducaton=()=>{
    function GeneralEducaton(){
        const [selectedField, setSelectedField] = useState('');
        const [responseData, setResponseData] = useState([]);

        const handleSelectChange = (e) => {
        setSelectedField(e.target.value);
        }

        useEffect(() => {
        if (selectedField) {
            const queryParams = new URLSearchParams({
                field: selectedField
              });

            const url = '/general_education?' + queryParams.toString();

            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setResponseData(data);
                console.log(data);
            })
            .catch(error => {
                console.error(error);
            });
        }
        }, [selectedField]);

        return(
            <div className='GeneralEducation'>
                <div className='g_education'>
                    <div>
                        <Link to='/Credit'>
                            <img src={back} alt="回上一頁" className="g_backicon"/>
                        </Link>
                    </div>
                    <div className="g_title">
                        <label className="titleText">通識領域</label>
                    </div>
                    <div className="g_selectDiv">
                        <select 
                            className="g_selectMust" 
                            value={selectedField}
                            onChange={handleSelectChange}
                        >
                            <option value="">--------請選擇--------</option>
                            <option value="一般">一般</option>
                            <option value="中外經典">中外經典</option>
                            <option value="全球化與社經結構">全球化與社經結構</option>
                            <option value="美學與美感表達">美學與美感表達</option>
                            <option value="社會脈動">社會脈動</option>
                            <option value="科技創新">科技創新</option>
                            <option value="人文探索">人文探索</option>
                            <option value="跨域永續">跨域永續</option>
                            <option value="自然科學">自然科學</option>
                            <option value="民主法治與公民意識">民主法治與公民意識</option>
                            <option value="科技與社會">科技與社會</option>
                            <option value="人格培育與多元文化">人格培育與多元文化</option>
                        </select>
                    </div>
                    <div className='g_Lable'>
                        <div className="g_scrollableContainer">
                            {responseData && responseData.map((item) => (
                            <GEinfo key={item.id}>
                                {item.name}<br />{item.number}<br />{item.teacher}<br />上課時間: {item.time}<br />上課地點: {item.classroom}
                            </GEinfo>
                            ))}
                        </div>     
                    </div>
                </div>
            </div>
        );
    }
    return (    
        <Routes>
            <Route path="/" element={<GeneralEducaton />}/>
        </Routes>
    
    );
}

export default GeneralEducaton;