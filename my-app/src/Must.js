import './Must.css';
import React from 'react';
import back from './img/back.png';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route, useNavigate} from 'react-router-dom';
import {useEffect,useState} from "react";
import { Mustinfo } from './components/Style';

const Must=()=>{
    function Must() {
        let navigate = useNavigate();
        const [data, setData] = useState([]);
        const formData = {
            studentID: "00957025",
          };
          useEffect(() => {
            if (!data) {
                fetch('/course_search', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
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
                    navigate("/Must", {
                        state: {
                          studentID: "00957025",
                          },});
            }
          }, [data]); // 添加依賴項data

        return (
            <div className="Must">    
                <div className='must_bg'>
                    <div>
                        <Link to='/Search'>
                            <img src={back} alt="回上一頁" className="must_backicon"/>
                        </Link>
                    </div>
                    <div className="must_title">
                        <label className="titleText">必選修課程</label>
                    </div>
                    <div className='mustLable'>
                        {data.map((item) => (<Mustinfo key={item.id}>{item.cname}&emsp;{item.cnumber}&emsp;{item.ccredit}</Mustinfo>))}
                    </div>
                    

                </div>
            </div>
        );
      }
  
      return ( 
          <Routes>
              <Route path="/" element={<Must />} />
          </Routes>
       
    );
}

export default Must;