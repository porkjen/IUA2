import './remain.css';
import React, { useEffect, useState } from 'react';
import logo from './img/IUAlogo.png';
import bee from './img/bee.PNG';
import bear from './img/bear.PNG';
import back from './img/back.png';
import {Back}  from './components/Style.js';
import { BrowserRouter as Router, Link } from 'react-router-dom';
import { Routes, Route } from 'react-router-dom';
import { Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleDCText, ArticleText, ArticlePostTimeRating,
    ArticleContainer, ArticleFoodContainer, ArticleAuthor, ArticlePostTime, ArticlePostRating, ArticleBody } from './components/ArticleStyle.js';
import { RemainTitle, RemainContainer, RemainList, RemainText, RemainBody } from './components/Style.js';
import { onLogin } from "./cookie.js";
import { loginUser } from './cookie';
import { getAuthToken } from "./utils";

const Remain = () => {
    const [data, setData] = useState(null);
    const [remainAll, setRemainAll] = useState(true);
    const [req, setReq] = useState(false);
    const [dept, setDept] = useState(false);
    const [opt, setOpt] = useState(false);
    const [general, setGeneral] = useState(false);
    const [kernal, setKernal] = useState(false);
    const [pe, setPE] = useState(false);
    const [showAlready, setshowAlready] = useState(false);
    const [selectedValue, setSelectedValue] = useState('');
    const [deptListData, setDeptListData] = useState([]);
    const [generalListData, setGeneralListData] = useState([]);
    const [kernalListData, setKernalListData] = useState([]);
    const [optListData, setOptListData] = useState([]);
    const [peListData, setPeListData] = useState([]);
    const [reqListData, setReqListData] = useState([]);
    const [AlreadyCredits, setAlreadyCredits] = useState();

    const userInfo = loginUser();
    const token = getAuthToken();

    const formData = {
        studentID: userInfo,
    };

    useEffect(() => {
        if (!data) {
            fetch('/remained_credits', {
                method: 'POST',
                headers: { 
                    'Content-Type': 'application/json' ,
                    'Authorization': `${token}`
                },
                body: JSON.stringify(formData)
            })
                .then(response => response.json())
                .then(data => {
                    setData(data);
                    console.log(data);
                    setDeptListData(data.deptList);
                    setGeneralListData(data.generalList);
                    setKernalListData(data.kernalList);
                    setOptListData(data.optList);
                    setPeListData(data.peList);
                    setReqListData(data.reqList);
                })
                .catch(error => {
                    console.error(error);
                });
        }
    }, [data]);

    const handleSelectChange = (event) => {
        setSelectedValue(event.target.value);
        console.log(selectedValue);
        if (event.target.value === "reamin_all") {
            setRemainAll(true);
            setReq(false);
            setDept(false);
            setOpt(false);
            setGeneral(false);
            setKernal(false);
            setPE(false);
            setshowAlready(false);
        } else if (event.target.value === "pre") {
            setRemainAll(false);
            setReq(true);
            setDept(false);
            setOpt(false);
            setGeneral(false);
            setKernal(false);
            setPE(false);
            setshowAlready(true);
        } else if (event.target.value === "dept") {
            setRemainAll(false);
            setReq(false);
            setDept(true);
            setOpt(false);
            setGeneral(false);
            setKernal(false);
            setPE(false);
            setshowAlready(true);
        } else if (event.target.value === "opt") {
            setRemainAll(false);
            setReq(false);
            setDept(false);
            setOpt(true);
            setGeneral(false);
            setKernal(false);
            setPE(false);
            setshowAlready(true);
        } else if (event.target.value === "general") {
            setRemainAll(false);
            setReq(false);
            setDept(false);
            setOpt(false);
            setGeneral(true);
            setKernal(false);
            setPE(false);
            setshowAlready(true);
        } else if (event.target.value === "kernal") {
            setRemainAll(false);
            setReq(false);
            setDept(false);
            setOpt(false);
            setGeneral(false);
            setKernal(true);
            setPE(false);
            setshowAlready(true);
        } else if (event.target.value === "pe") {
            setRemainAll(false);
            setReq(false);
            setDept(false);
            setOpt(false);
            setGeneral(false);
            setKernal(false);
            setPE(true);
            setshowAlready(true);
        }
    };

    function RemainCredits() {
        return (
            <div className='Remain_Course_credits'>
                <div className='c_Course'>
                    <div className='RemainTitle_position'>
                        {data && <RemainTitle>必修&emsp;{data.required}&emsp;學分</RemainTitle>}
                    </div>
                </div>
                <div className='ie_Course'>
                    <div className='RemainTitle_position'>
                        {data && <RemainTitle>系內選修&emsp;{data.deptOptional}&emsp;學分</RemainTitle>}
                    </div>
                </div>
                <div className='oe_Course'>
                    <div className='RemainTitle_position'>
                        {data && <RemainTitle>系外選修&emsp;{data.optional}&emsp;學分</RemainTitle>}
                    </div>
                </div>
                <div className='g_Course'>
                    <div className='RemainTitle_position'>
                        {data && <RemainTitle>通識&emsp;{data.general}&emsp;學分</RemainTitle>}
                    </div>
                </div>
                <div className='core_Course'>
                    <div className='RemainTitle_position'>
                        {data && <RemainTitle>核心選修&emsp;{data.kernal}&emsp;學分</RemainTitle>}
                    </div>
                </div>
                <div className='pe_Course'>
                    <div className='RemainTitle_position'>
                        {data && <RemainTitle>體育&emsp;{data.pe}&emsp;學分</RemainTitle>}
                    </div>
                </div>
                <div className='eng_Course'>
                    <div className='RemainTitle_position'>
                        {data && <RemainTitle>門檻-英文&emsp;{data.eng}</RemainTitle>}
                    </div>
                </div>
                <div className='swim_Course'>
                    <div className='RemainTitle_position'>
                        {data && <RemainTitle>門檻-游泳&emsp;{data.swimming}</RemainTitle>}
                    </div>
                </div>
            </div>
        );
    }

    function FCourseList({ list }) {
        const totalCredit = list.reduce((total, item) => total + item.credit, 0);
        setAlreadyCredits(totalCredit);
        return (
            <RemainList>
                <ArticleContainer>
                    {list.map((item, index) => (
                        <RemainBody key={index}>
                            <RemainText>{item.courseName}&nbsp;<br/>{item.credit}&nbsp;學分</RemainText>
                        </RemainBody>
                    ))}
                </ArticleContainer>
            </RemainList>
        );
    }

    function Remain() {
        return (
            <div className="Remain">
                <Link to='/Credit'>
                    <Back src={back} alt="回上一頁" />
                </Link>
                <div className='Remain_bg'>
                    <div className="Remain_title">
                        <Title>剩餘學分</Title>
                    </div>

                    <div className='Remain_Course'>
                        <select className='courseSelect' value={selectedValue} onChange={handleSelectChange}>
                            <option value="reamin_all">全部剩餘學分</option>
                            <option value="pre">已修過必修</option>
                            <option value="dept">已修過系內選修</option>
                            <option value="opt">已修過系外選修</option>
                            <option value="general">已修過通識</option>
                            <option value="kernal">已修過核心選修</option>
                            <option value="pe">已修過體育</option>
                        </select>
                        {showAlready && <label className='Remain_label'>已修過{AlreadyCredits}學分</label>}
                        {remainAll && <RemainCredits />}
                        {dept && <FCourseList list={deptListData} />}
                        {req && <FCourseList list={reqListData} />}
                        {opt && <FCourseList list={optListData} />}
                        {general && <FCourseList list={generalListData} />}
                        {kernal && <FCourseList list={kernalListData} />}
                        {pe && <FCourseList list={peListData} />}
                    </div>

                    <div className="bee">
                        <img src={bee} alt="IUA" />
                    </div>
                    <div className="bear">
                        <img src={bear} alt="IUA" />
                    </div>
                </div>
            </div>
        );
    }

    return (
        <Routes>
            <Route path="/" element={<Remain />} />
        </Routes>
    );
}

export default Remain;
