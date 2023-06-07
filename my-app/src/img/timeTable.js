import './timeTable.css';
import React from 'react';
import dialog from './img/chatBubble.png';
import cookie from './img/cookie.png';
import toast from './img/toast.png';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';
import {useState} from "react";

const TimeTable=()=> {
    function TimeTable() {

        const [data, setData] = useState(null);

        fetch("")
        .then((res)=>res.json())
        .then((data)=>{
            setData(data)
        })
      
      return (
        <div className="TimeTable">    
            <div className="TimeTablebg">
                <div className="TimeTable_draw"> 
                    <div className="TimeTablecookie">
                        <img src={cookie} alt="IUA" />
                    </div>
                    <div className="TimeTabledialog">
                        <img src={dialog} alt="IUA" />
                    </div>
                    <div className="TimeTabletoast">
                        <img src={toast} alt="IUA" />
                    </div>
                    <div className="TimeTable_title">
                        <label>課表查詢</label>
                    </div>
                </div>

                <br/>
                <div className="timeTable_info_place">
                    <table className="timeTable_info">
                        <tr>
                            <th></th>
                            <th>星期一</th>
                            <th>星期二</th>
                            <th>星期三</th>
                            <th>星期四</th>
                            <th>星期五</th>
                            <th>星期六</th>
                            <th>星期日</th>
                        </tr>
                        <br/>
                        <tr>
                            <th>8:20-9:10</th>
                            <td>星期一好累喔</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                        </tr>
                        <br/>
                        <tr>
                            <th>9:20-10:10</th>
                            <td>星期一好累喔</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                        </tr>
                        <br/>
                        <tr>
                            <th>10:20-11:10</th>
                            <td>星期一好累喔</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                        </tr>
                        <br/>
                        <tr>
                            <td colspan="8" className="break">休息時間</td>
                        </tr>
                        <br/>
                        <tr>
                            <th>13:10-14:00</th>
                            <td>星期一好累喔星期一好累喔</td>
                            <td>星期一好累喔星期一好累喔</td>
                            <td>星期一星期一好累喔</td>
                            <td>星期一星期一好累喔</td>
                            <td>星期一好累喔星期一好累喔</td>
                            <td>星期一星期一好累喔</td>
                            <td>星期一星期一好累喔</td>
                        </tr>
                        <br/>
                        <tr>
                            <th>14:10-15:00</th>
                            <td>星期一好累喔</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一好累喔</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                        </tr>
                        <br/>
                        <tr>
                            <th>15:10-16:00</th>
                            <td>星期一好累喔</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一好累喔</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                        </tr>
                        <br/>
                        <tr>
                            <th>16:05-16:55</th>
                            <td>星期一好累喔</td>
                            <td>排球初</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                        </tr>
                        <br/>
                        <tr>
                            <th>17:30-18:20</th>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一好累喔</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                        </tr>
                        <br/>
                        <tr>
                            <th>18:30-19:20</th>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一好累喔</td>
                        </tr>
                        <br/>
                        <tr>
                            <th>19:25-20:15</th>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一好累喔</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                        </tr>
                        <br/>
                        <tr>
                            <th>20:20-21:10</th>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                        </tr>
                        <br/>
                        <tr>
                            <th>21:15-22:05</th>
                            <td>星期一好累喔</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一</td>
                            <td>星期一好累喔</td>
                            <td>星期一好累喔</td>
                            <td>星期一好累喔</td>
                        </tr>

                    </table>
                </div>
            </div>
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
