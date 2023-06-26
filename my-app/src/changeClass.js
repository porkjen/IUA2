import './changeClass.css';
import React from 'react';
import {Page, Pagebg, Title, PostArticleBtn, ArticleList, ArticleText, ArticleContainer, ArticleAuthor, ArticleBody, PerChangeClassBtn}  from './components/ArticleStyle.js';
import { Routes ,Route,Link,useNavigate } from 'react-router-dom';
import {useState,useEffect} from "react";

const ChangeClass=()=> {
    let navigate = useNavigate();

    function Articleinfo({ author, text }) {
        return (
          <ArticleContainer>
            <ArticleText>
                <ArticleAuthor>{author}</ArticleAuthor>
                <ArticleBody>{text}</ArticleBody>
            </ArticleText>
          </ArticleContainer>
        );
      }


    function ChangeClass() {
      const [value, setValue] = useState('');

      useEffect(() => {
        console.log(value);
      }, [value]);

      const handleShowClassInfoSubmit = (timeValue) => {
        
        setValue(timeValue);
        console.log(value);
        //const student_id = loginUser();
                       //Form submission happens here
            navigate("/changeClassList", {
              state: {
                studentID: "00957025",
                time:timeValue,
                },});
      }

      return (
        <Page>
            <Pagebg>
                <Title>換課板</Title>
                <Link to='/postArticle'>
                  <PostArticleBtn>我要發文</PostArticleBtn>
                </Link>
                <div className='PerChangeClassTable'>
                  <div>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('101')}>101</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('201')}>201</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('301')}>301</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('401')}>401</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('501')}>501</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('601')}>601</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('701')}>701</PerChangeClassBtn>
                  </div>
                  <div>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('102')}>102</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('202')}>202</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('302')}>302</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('402')}>402</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('502')}>502</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('602')}>602</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('702')}>702</PerChangeClassBtn>
                  </div>
                  <div>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('103')}>103</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('203')}>203</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('303')}>303</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('403')}>403</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('503')}>503</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('603')}>603</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('703')}>703</PerChangeClassBtn>
                  </div>
                  <div>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('104')}>104</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('204')}>204</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('304')}>304</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('404')}>404</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('504')}>504</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('604')}>604</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('704')}>704</PerChangeClassBtn>
                  </div>
                  <div>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('105')}>105</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('205')}>205</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('305')}>305</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('405')}>405</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('505')}>505</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('605')}>605</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('705')}>705</PerChangeClassBtn>
                  </div>
                  <div>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('106')}>106</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('206')}>206</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('306')}>306</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('406')}>406</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('506')}>506</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('606')}>606</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('706')}>706</PerChangeClassBtn>
                  </div>
                  <div>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('107')}>107</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('207')}>207</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('307')}>307</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('407')}>407</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('507')}>507</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('607')}>607</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('707')}>707</PerChangeClassBtn>
                  </div>
                  <div>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('108')}>108</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('208')}>208</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('308')}>308</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('408')}>408</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('508')}>508</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('608')}>608</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('708')}>708</PerChangeClassBtn>
                  </div>
                  <div>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('109')}>109</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('209')}>209</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('309')}>309</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('409')}>409</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('509')}>509</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('609')}>609</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('709')}>709</PerChangeClassBtn>
                  </div>
                  <div>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('110')}>110</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('210')}>210</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('310')}>310</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('410')}>410</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('510')}>510</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('610')}>610</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('710')}>710</PerChangeClassBtn>
                  </div>
                  <div>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('111')}>111</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('211')}>211</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('311')}>311</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('411')}>411</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('511')}>511</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('611')}>611</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('711')}>711</PerChangeClassBtn>
                  </div>
                  <div>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('112')}>112</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('211')}>212</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('311')}>312</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('411')}>412</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('511')}>512</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('611')}>612</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('711')}>712</PerChangeClassBtn>
                  </div>
                  <div>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('113')}>113</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('213')}>213</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('313')}>313</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('413')}>413</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('513')}>513</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('613')}>613</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('713')}>713</PerChangeClassBtn>
                  </div>
                  <div>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('114')}>114</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('214')}>214</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('314')}>314</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('414')}>414</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('514')}>514</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('614')}>614</PerChangeClassBtn>
                  <PerChangeClassBtn onClick={() => handleShowClassInfoSubmit('714')}>714</PerChangeClassBtn>
                  </div>
                </div>
            </Pagebg>
        </Page>
      );
    }

    return (
           
                <Routes>
                    <Route path="/" element={<ChangeClass />} />
                </Routes>
             
    );
}

export default ChangeClass;
