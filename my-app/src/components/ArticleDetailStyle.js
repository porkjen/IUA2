
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

const ArticleDetailAuthorArea= styled.div`
    display: flex;
    flex-direction: row ;
`;

const ArticleDetailAuthorImg = styled.img`
    width: 50px; /* 設定頭像的寬度和高度 */
    height: 50px;
    border-radius: 50%;
    overflow: hidden;
`;

const ArticleDetailAuthor = styled.h4`
    padding-top:10px;
    padding-left:5px;
    margin:5px;
`;

const ArticleDetailTitle = styled.h2`
    text-align:center;
    margin:5px;
`;

const ArticleDetailStar = styled.div`
    display:inline
`

const ArticleDetailPostDate = styled.h4`
    padding-left:280px;
    margin:5px;
`;

const ArticleDetailText = styled.h3`
    background-color:white;
`;

/*收藏 */
const ArticleDetailSavedBtn = styled.button`
    background-color: rgba(249, 231, 193, 0.951);
    width:60px;
    text-align:center;
    height:30px;
    margin-right:20px;
    border-radius: 6px; 
`;

/*已收藏 */
const ArticleDetailAlreadySavedBtn = styled.button`
    background-color: rgba(223, 223, 223);
    width:60px;
    text-align:center;
    height:30px;
    margin-right:20px;
    border-radius: 6px; 
`;

const ButtonContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  margin-left: auto;
`;


const ArticleDetailNormalBtn = styled.button`
    background-color: rgba(249, 231, 193, 0.951);
    width:80px;
    text-align:center;
    height:30px;
    margin-right:20px;
    border-radius: 6px; 
`;

/*評分*/
const ArticleDetailRatingdBtn = styled.button`
    background-color: rgba(249, 231, 193, 0.951);
    width:60px;
    text-align:center;
    height:30px;
    margin-right:20px;
    border-radius: 6px; 
`;

/*送出 */
const ArticleDetailContactdBtn = styled.button`
    background-color: rgba(249, 231, 193, 0.951);
    width:60px;
    text-align:center;
    height:30px;
    margin-left:60%;
    border-radius: 6px; 
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
    border-radius: 6px; 
`


export {ArticleDetailPage, ArticleDetailPosition, ArticleDetailAuthorArea, ArticleDetailAuthorImg, ArticleDetailAuthor, ArticleDetailStar, ArticleDetailTitle, ArticleDetailPostDate, ArticleDetailText, ButtonContainer, ArticleDetailSavedBtn, ArticleDetailNormalBtn,  ArticleDetailAlreadySavedBtn, ArticleDetailContactdBtn,  ArticleDetailComment, ArticleDetailPostCommentPosition, ArticleDetailCommentImg, ArticleDetailPostComment, ArticleDetailRatingdBtn, ArticleDetailPostBtn};
