/*版面*/
import styled from 'styled-components';
import backgroundImg from '../img/background.PNG';

const Page = styled.div`
    height: 100%;
    padding: 0;
    margin: 0;
    overflow-y:hidden;
    
`;
/*background-image: url(${backgroundImg});   */
const Pagebg = styled.div`
    background-color: rgba(249, 236, 207, 0.951);  
    height: 100%;
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
`;

const Title = styled.h1`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    padding-top: 20px;
    position: absolute;
    left: 50%;
    top: 5%;
    transform: translate(-50%, -50%);
`;

/*我要發文按鈕*/
const PostArticleBtn = styled.button`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    border: 1px solid rgba(0, 0, 0, 0.125);
    border-radius: 5px;
    background-color: white; 
    padding: 10px;
    width: 100px;
    margin-left: 5%;
    position: absolute;
    margin-top: 30%;
`;

/*篩選條件按鈕*/
const ChooseArticleBtn = styled.button`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    border: 1px solid rgba(0, 0, 0, 0.125);
    border-radius: 5px;
    background-color: white; 
    padding: 10px;
    width: 100px;
    margin-left: 70%;
    position: absolute;
    margin-top: 30%;

`;

const ArticleList = styled.div`
    display: flex;
    flex-direction: column;
    margin-top: 30px;
    overflow: auto;
    height: 73%;
    will-change: transform;
    position: absolute;
    left: 50%;
    top: 58%;
    transform: translate(-50%, -50%);
`;

const ArticleContainer = styled.div`
    min-width: match-parent;
    text-align: center;
    border: transparent;
    padding: 10px;
    border-radius: 4px;
    margin-top: 20px;
    min-height:50px;
`;

const ArticleText = styled.button`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    border-radius: 5px;
    width: 350px;
    border: 1px solid rgba(0, 0, 0, 0.125);
    padding: 16px;
    background-color: white;
    min-height:70px;
`;

const ArticleAuthor = styled.div`
    margin-right: 250px;
    color: #232323;
`;

const ArticlePostTime = styled.div`
    margin-top: 8px;
    word-break: break-all;
    white-space: pre-line;

`;

const ArticleBody = styled.div`
    margin-top: 8px;
    word-break: break-all;
    white-space: pre-line;

`;


const ArticleSubmitBtn = styled.button`
    border: 1px solid black;
    border-radius: 5px;
    background-color: transparent; 
    padding: 10px;
    width: 100px;
`;

const ArticleSubmitBtnPosition = styled.div`
    text-align: center;
`;


export {Page, Pagebg, Title, PostArticleBtn, ChooseArticleBtn, ArticleList, ArticleText,ArticlePostTime, ArticleContainer, ArticleAuthor, ArticleBody, ArticleSubmitBtn, ArticleSubmitBtnPosition};
