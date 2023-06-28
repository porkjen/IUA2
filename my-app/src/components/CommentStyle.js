
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

const CommentList = styled.div`
    display: flex;
    flex-direction: column;
    margin-top: 30px;
`;

const CommentContainer = styled.div`
    min-width: match-parent;
    text-align: center;
    border: transparent;
    padding: 10px;
    border-radius: 4px;
    margin-top: 15px;
    min-height:50px;
`;

const CommentText = styled.button`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    border-radius: 5px;
    width: 350px;
    padding: 13px;
    background-color: white;
    min-height:70px;
    border: 1px solid #FFA500;
    background-color: transparent; 
`;



const CommentAuthor = styled.div`
    margin-right: 250px;
    color: #232323;
`;

const CommentBody = styled.div`
    margin-top: 8px;
    word-break: break-all;
    white-space: pre-line;

`;

const CommentTimeRating = styled.div`
    margin-top: 8px;
    word-break: break-all;
    white-space: pre-line;
    text-align: right;
`;

const CommentRating = styled.div`
    margin-top: 8px;
    word-break: break-all;
    white-space: pre-line;
`;





export {Page, Pagebg, CommentList, CommentText, CommentContainer, CommentAuthor, CommentBody, CommentTimeRating, CommentRating};
