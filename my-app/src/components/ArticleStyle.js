
import styled from 'styled-components';
import backgroundImg from '../img/background.PNG';

const Page = styled.div`
    height: 100%;
    padding: 0;
    margin: 0;
`;

const Pagebg = styled.div`
    background-image: url(${backgroundImg});    
    height: 100%;
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
`;

const Title = styled.h1`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    padding-top: 20px;
    text-align: center;
    font-family: 'Noto Serif TC', serif;
`;

const PostArticleBtn = styled.button`
    border: 1px solid rgba(0, 0, 0, 0.125);
    border-radius: 5px;
    background-color: transparent; 
    padding: 10px;
    width: 100px;
    margin-left: 15px

`;

const ArticleList = styled.div`
    display: flex;
    flex-direction: column;
    margin-top: 30px;
`;

const ArticleContainer = styled.div`
    min-width: match-parent;
    text-align: center;
    border: transparent;
    padding: 10px;
    border-radius: 4px;
    margin-top: 10px;

`;

const ArticleText = styled.button`
    border-radius: 5px;
    width: 350px;
    border: 1px solid rgba(0, 0, 0, 0.125);
    padding: 16px;
    background-color: white;

`;



const ArticleAuthor = styled.div`
    margin-right: 250px;
    color: #232323;
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


export {Page, Pagebg, Title, PostArticleBtn, ArticleList, ArticleText, ArticleContainer, ArticleAuthor, ArticleBody, ArticleSubmitBtn, ArticleSubmitBtnPosition};
