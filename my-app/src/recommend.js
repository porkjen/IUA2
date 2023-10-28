import './recommend.css';
import {Back}  from './components/Style.js';
import { BrowserRouter as Router,Link } from 'react-router-dom';//BrowserRouter
import { Routes ,Route } from 'react-router-dom';
import back from './img/back.png';

const Recommend=()=> {

    function Recommend() {
      return (
        <div className="Recommend"> 
          <Link to='/CourseSelection'>
              <Back src={back} alt="回上一頁" />
          </Link>
            <div className='Position'>
            <a >
                <Link to="/emptyResult">
                    <button className='Btn'>當學期課表空堂推薦</button>
                </Link>
            </a>
            <a >
                <Link to="/courseRate">
                    <button className='Btn' >已修過課程喜好推薦</button>
                </Link>
            </a>
            <a >
                <Link to="/history">
                    <button className='Btn'>歷史推薦結果</button>
                </Link>
            </a>
            </div>
           
        </div>
      );
    }

    return (

           
                <Routes>
                    <Route path="/" element={<Recommend />} />
                </Routes>
             
    );
}

export default Recommend;
