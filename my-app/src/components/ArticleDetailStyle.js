
/*文章內文 */
import styled from 'styled-components';
import backgroundImg from '../img/background.PNG';

const ArticleDetailPage = styled.div`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    height: 100%;
    padding: 0;
    margin: 0;
    background-color:white;
`;

const ArticleDetailPosition = styled.div`
    height:85%;
    overflow: auto;
`;


const ArticleDetailAuthor = styled.h4`
    padding-top:10px;
    padding-left:20px
`;

const ArticleDetailTitle = styled.h2`
    text-align:center;
`;

const ArticleDetailPostDate = styled.h4`
    padding-left:280px;
`;

const ArticleDetailText = styled.h3`
    background-color:white;
`;

const ArticleDetailSavedBtn = styled.button`
    background-color: rgba(249, 231, 193, 0.951);
    width:60px;
    text-align:center;
    height:30px;
    margin-left:20px;
`;

const ArticleDetailRatingdBtn = styled.button`
    background-color: rgba(249, 231, 193, 0.951);
    width:60px;
    text-align:center;
    height:30px;
    margin-left:60%;
`;

const ArticleDetailContactdBtn = styled.button`
    background-color: rgba(249, 231, 193, 0.951);
    width:60px;
    text-align:center;
    height:30px;
    margin-left:60%;
`;

const ArticleDetailComment = styled.div`
    background-color:white;
`;

const ArticleDetailPostCommentPosition = styled.div`
    background-color:white;
    
`;

const ArticleDetailCommentArea = styled.img`
    width: 40px; /* 設定頭像的寬度和高度 */
    height: 40px;
    border-radius: 50%;
    overflow: hidden;
    margin-left: 20px;
    padding-top: 25px;
`;

const ArticleDetailCommentImg = styled.img`
    width: 40px; /* 設定頭像的寬度和高度 */
    height: 40px;
    border-radius: 50%;
    overflow: hidden;
    margin-left: 20px;
    padding-top: 25px;
`;


const ArticleDetailPostComment = styled.input`
    border: none;
    border-bottom: 1px solid rgb(35, 35, 35);
    border-radius: 0px;
    width: 60%;
    margin-right:20px;
    padding-top:35px;
    &:focus {
        outline: none;
      }
`

const ArticleDetailPostBtn = styled.button`
    background-color: rgba(249, 231, 193, 0.951);  
    height:40%;
    margin-top: 10%;
`


export {ArticleDetailPage, ArticleDetailPosition, ArticleDetailAuthor, ArticleDetailTitle, ArticleDetailPostDate, ArticleDetailText, ArticleDetailSavedBtn, ArticleDetailContactdBtn,  ArticleDetailComment, ArticleDetailPostCommentPosition, ArticleDetailCommentImg, ArticleDetailPostComment, ArticleDetailRatingdBtn, ArticleDetailPostBtn};
