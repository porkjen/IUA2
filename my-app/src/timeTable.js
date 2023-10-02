import './timeTable.css';
import React from 'react';
import Modal from "./components/Modal";
import cat2 from './img/SignIn2.PNG';
import back from './img/back.png';
import {Back}  from './components/Style.js';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route, useLocation } from 'react-router-dom';
import {useState, useEffect} from "react";
import { loginUser } from './cookie';
import { useCookies } from 'react-cookie';
import { getAuthToken } from "./utils";
import {ChangeClassCategorySelect}  from './components/ArticleStyle.js';
import { Page, Pagebg, Title,ArticleContainer} from './components/ArticleStyle.js';
import { RemainTitle, RemainContainer, RemainList, RemainText, RemainBody } from './components/Style.js';

const TimeTable=()=> {

    
    function TimeTable() {

        const userInfo = loginUser();
        const location = useLocation();
        const [data, setData] = useState(null);
        const [openModal, setOpenModal] = useState(false);
        const [courseName, setCourseName] = useState('');
        const [tt, setTT] = useState(true);
        const [Info, setInfo] = useState(false);
        const [waiting, setWaiting] = useState(false);
        const [cookies, setCookie] = useCookies(['token']);
        const token = getAuthToken();

        useEffect(() => {
          if (!data) {
            setWaiting(true);
            fetch(`/curriculum_search?studentID=${userInfo}`, {
              headers: {
                'Authorization': `${token}`  // 將 token 添加到請求頭中
              }
            })
            .then(response => {
              console.log(response.status);
              if (response.status === 200) {
                setWaiting(false);
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
        
          
          function generateTableRows() {
            const rows = [];
             const timeSlots = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14',
            ];

            const timeSlotsMon = [
                '101', '102', '103', '104', '105', '106', '107', '108', '109', '110', '111', '112', '113', '114'
            ];

            const timeSlotsTue = [
                '201', '202', '203', '204', '205', '206', '207', '208', '209', '210', '211', '212', '213', '214'
           ];

            const timeSlotsWed = [
                '301', '302', '303', '304', '305', '306', '307', '308', '309', '310', '311', '312', '313', '314'
            ];

            const timeSlotsThr = [
                '401', '402', '403', '404', '405', '406', '407', '408', '409', '410', '411', '412', '413', '414'
            ];

            const timeSlotsFri = [
                '501', '502', '503', '504', '505', '506', '507', '508', '509', '510', '511', '512', '513', '514'
            ];
             const timeSlotsSat = [
                '601', '602', '603', '604', '605', '606', '607', '608', '609', '610', '611', '612', '613', '614'
            ];

            const timeSlotsSun = [
                '701', '702', '703', '704', '705', '706', '707', '708', '709', '710', '711', '712', '713', '714'
            ];

            const handleButtonClick = (event) => {
              
           };
          
            for (let i = 0; i < timeSlots.length; i++) {
              const timeSlot = timeSlots[i];
              const timeSlotMon = timeSlotsMon[i];
              const timeSlotTue = timeSlotsTue[i];
              const timeSlotWed = timeSlotsWed[i];
              const timeSlotThr = timeSlotsThr[i];
              const timeSlotFri = timeSlotsFri[i];
              const timeSlotSat = timeSlotsSat[i];
              const timeSlotSun = timeSlotsSun[i];
              let timeContent = '';
              let timeContentMon = '';
              let timeContentTue = '';
              let timeContentWed = '';
              let timeContentThr = '';
              let timeContentFri = '';
              let timeContentSat = '';
              let timeContentSun = '';
              let nextRow = false; 
          
              for (const key in data) {
                if (data.hasOwnProperty(key) && data[key].time && data[key].time.hasOwnProperty('0')) {
                  const timeValues = Object.values(data[key].time);
                  if (timeValues.includes(timeSlotMon)) {
                    timeContentMon += data[key].name + ' (' + data[key].classroom + ')' + '\n'+ data[key].teacher;
                  }
                  if (timeValues.includes(timeSlotTue)) {
                    timeContentTue += data[key].name + ' (' + data[key].classroom + ')' + '\n'+ data[key].teacher;
                  }
                  if (timeValues.includes(timeSlotWed)) {
                    timeContentWed += data[key].name + ' (' + data[key].classroom + ')' + '\n'+ data[key].teacher;
                  }
                  if (timeValues.includes(timeSlotThr)) {
                    timeContentThr += data[key].name + ' (' + data[key].classroom + ')' + '\n'+ data[key].teacher;
                  }
                  if (timeValues.includes(timeSlotFri)) {
                    timeContentFri += data[key].name + ' (' + data[key].classroom + ')' + '\n'+ data[key].teacher;
                  }
                  if (timeValues.includes(timeSlotSat)) {
                    timeContentSat += data[key].name + ' (' + data[key].classroom + ')' + '\n'+ data[key].teacher;
                  }
                  if (timeValues.includes(timeSlotSun)) {
                    timeContentSun += data[key].name + ' (' + data[key].classroom + ')' + '\n'+ data[key].teacher;
                  }
                }
              }
          
              rows.push(
                <tr key={timeSlot}>
                  <th>{getTimeSlotLabel(timeSlot)}</th>
                  <td id={timeSlotMon}>{timeContentMon}</td>
                  <td id={timeSlotTue}>{timeContentTue}</td>
                  <td id={timeSlotWed}>{timeContentWed}</td>
                  <td id={timeSlotThr}>{timeContentThr}</td>
                  <td id={timeSlotFri}>{timeContentFri}</td>
                  <td id={timeSlotSat}>{timeContentSat}</td>
                  <td id={timeSlotSun}>{timeContentSun}</td>
                </tr>
              );
              
              
            }
          
            return rows;
          }
          
          

          function getTimeSlotLabel(timeSlot) {
            const timeLabels = {
              '1': '8:20-9:10',
              '2': '9:20-10:10',
              '3': '10:20-11:00',
              '4': '11:10-12:00',
              '5': '13:10-14:00',
              '6': '13:10-14:00',
              '7': '14:10-15:00',
              '8': '15:10-16:00',
              '9': '16:05-16:55',
              '10': '17:30-18:20',
              '11': '18:30-19:20',
              '12': '19:25-20:15',
              '13': '20:20-21:10',
              '14': '21:15-22:05',
            };
        
            return timeLabels[timeSlot] || '';
          }



          const handleTTFunctionSelect  = event => {

            if(event.target.value==='timeTable'){
              setTT(true);
              setInfo(false);
            }
            else if(event.target.value==='courseInfo'){
              setInfo(true);
              setTT(false);
            }
    
          };

          function handleCourseSelect(CName) {
            setCourseName(CName);
            setOpenModal(true);
          };

          function CourseList({ list }) {
            return (
                <RemainList>
                    <ArticleContainer>
                        {list.map((item, index) => (
                            <RemainBody key={index}>
                                <RemainText>{item.name}&nbsp;<br/><button className='courseInfo' onClick={() => handleCourseSelect(item.name)}>課程資訊</button></RemainText>
                            </RemainBody>
                        ))}
                    </ArticleContainer>
                </RemainList>
            );
        }

        
      
      return (
        <div className="TimeTable">  
        {openModal && <Modal closeModal={setOpenModal} type={"courseInfo"} CName={courseName}/> } 
        {!openModal &&  
          <Link to='/timeTableChoose'>
              <Back src={back} alt="回上一頁" />
          </Link>
        }
        {waiting && 
         <div className='timeTableLoadingText'>
          <div class="timeTablePreloader">
            正在抓取資料
            <div class="timeTableDot1"></div>
            <div class="timeTableDot2"></div>
            <div class="timeTableDot3"></div>
          </div>
        </div>

        }
        {!openModal && !waiting &&
          <div className="TimeTablebg">
                <select className='timeTableSelect' onChange={handleTTFunctionSelect}>
                <option>功能選單</option>
                  <option value='timeTable'>課表</option>
                  <option value='courseInfo'>課程資訊</option>
                </select>
                <div className="TimeTable_draw"> 
                        <img src={cat2} alt="IUA"  className="TimeTableCat2"/>
                    <div className="TimeTable_title">
                        <label className="TimeTable_title_font">課表查詢</label>
                    </div>
                </div>

                <br/>
                {tt &&
                  <div className="timeTable_info_place">
                    <table className="timeTable_info">
                    <tr>
                            <th>時間</th>
                            <th>星期一</th>
                            <th>星期二</th>
                            <th>星期三</th>
                            <th>星期四</th>
                            <th>星期五</th>
                            <th>星期六</th>
                            <th>星期日</th>
                    </tr>
                    {generateTableRows()}
                    </table>
                  </div>
                }
                {Info && 
                  <CourseList list={data} />
                }
                
            </div>

        }
            
        </div>
      );
    }

    return (

                <Routes>
                    <Route path="/" element={<TimeTable />} />
                </Routes>
             
    );
}

export default TimeTable;
