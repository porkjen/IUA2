
/*文章內文 */
import styled from 'styled-components';
import backgroundImg from '../img/background.PNG';

const ArticleDetailPage = styled.div`
    height: 100%;
    padding: 0;
    margin: 0;
    background-color:white;
`;

const ArticleDetailAuthor = styled.h3`
    padding-top:10px;
    padding-left:20px
`;

const ArticleDetailTitle = styled.h2`
    text-align:center;
`;

const ArticleDetailText = styled.h3`
    background-color:white;
`;

const ArticleDetailSavedBtn = styled.button`
    background-color:green;
    width:60px;
    text-align:center;

`;

const ArticleDetailComment = styled.div`
    background-color:white;
`;



export {ArticleDetailPage, ArticleDetailAuthor, ArticleDetailTitle, ArticleDetailText, ArticleDetailSavedBtn, ArticleDetailComment};
