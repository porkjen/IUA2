
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
    padding: 5px;
    border-radius: 4px;
    margin-top: 10px;

`;

const CommentText = styled.button`
    border-radius: 5px;
    width: 350px;
    border: 1px solid rgba(0, 0, 0, 0.125);
    padding: 16px;
    background-color: white;

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




export {Page, Pagebg, CommentList, CommentText, CommentContainer, CommentAuthor, CommentBody};
