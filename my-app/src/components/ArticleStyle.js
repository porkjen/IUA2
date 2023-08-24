/*版面*/
import styled from 'styled-components';
import backgroundImg from '../img/background.PNG';

const Page = styled.div`
    height: 100%;
    padding: 0;
    margin: 0;
    overflow-y:hidden;
    overflow: hidden;
    
`;
/*background-image: url(${backgroundImg});   */
const Pagebg = styled.div`
    background-color: rgba(249, 236, 207);  
    height: 100%;
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
    overflow: hidden;
`;

const Title = styled.h1`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    padding-top: 20px;
    position: absolute;
    left: 50%;
    top: 5%;
    transform: translate(-50%, -50%);
    z-index:2;
`;


/*我要發文按鈕*/
const PostArticleBtn = styled.button`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    border: 1px solid #FFA500;
    border-radius: 5px;
    background-color: transparent; 
    padding: 10px;
    width: 100px;
    margin-left: 5%;
    position: absolute;
    margin-top: 30%;
    z-index: 2;
`;

/*篩選條件按鈕*/
const ChooseArticleBtn = styled.button`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    border: 1px solid #FF8C00;
    border-radius: 5px;
    background-color: transparent; 
    padding: 10px;
    width: 100px;
    margin-left: 70%;
    position: absolute;
    margin-top: 30%;
    z-index: 2;
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
    top: 63%;
    transform: translate(-50%, -50%);
`;


const ArticleContainer = styled.div`
    min-width: match-parent;
    border: transparent;
    padding: 5px;
    border-radius: 4px;
    margin-bottom: 5px;
    
`;

const ArticleRentContainer = styled.div`
    min-width: match-parent;
    text-align: center;
    border: transparent;
    padding: 5px;
    border-radius: 4px;
    margin-bottom: 5px;
    
`;

const ArticleFoodContainer = styled.div`
    min-width: match-parent;
    border: transparent;
    padding: 5px;
    border-radius: 4px;
    margin-bottom: 5px;
    
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

const AlreadyArticleText = styled.button`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    border-radius: 5px;
    width: 350px;
    border: 1px solid rgba(0, 0, 0, 0.125);
    padding: 16px;
    background-color: 	#D3D3D3;
    min-height:70px;
`;

const ArticleDCText = styled.button`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    border-radius: 5px;
    width: 350px;
    border: 1px solid rgba(0, 0, 0, 0.125);
    padding: 16px;
    background-color: white;
    min-height:70px;
    margin-top:2%;
    margin-left:7px;
`;

const ArticleSelect= styled.select`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    border: 1px solid #FF8C00;
    border-radius: 5px;
    background-color: transparent; 
    padding: 10px;
    width: 110px;
    margin-left: 60%;
    position: absolute;
    margin-top: 45%;
    z-index: 2;
`;

const ArticleDistance= styled.div`
    border-color:black;
    border-width:1px;
    border-style:solid;
    width:40%;
    height:20%;
    margin-left:200px;
`;

const ArticleBaiDistance= styled.div`
    border-color:black;
    border-width:1px;
    border-style:solid;
    width:40%;
    height:20%;
    margin-left:200px;
    background-color:cornsilk ;
`;

const ArticleXiangDistance= styled.div`
    border-color:black;
    border-width:1px;
    border-style:solid;
    width:40%;
    height:20%;
    margin-left:200px;
    background-color:lavenderblush;
`;

const ArticleXiDistance= styled.div`
    border-color:black;
    border-width:1px;
    border-style:solid;
    width:40%;
    height:20%;
    margin-left:200px;
    background-color:floralwhite;
`;

const ArticleZhongDistance= styled.div`
    border-color:black;
    border-width:1px;
    border-style:solid;
    width:40%;
    height:20%;
    margin-left:200px;
    background-color:azure;
`;

const ArticleXingDistance= styled.div`
    border-color:black;
    border-width:1px;
    border-style:solid;
    width:40%;
    height:20%;
    margin-left:200px;
    background-color:gainsboro;
`;




const ArticleAuthorArea= styled.div` 
    display: flex;
    flex-direction: row ;
    width:80%;
   
`;


const ArticleAuthorImg = styled.img`
    width: 15%; /* 設定頭像的寬度和高度 */
    height: 15%;
    border-radius: 50%;
    overflow: hidden;
`;

const ArticleAuthor = styled.div`
    width:100px;
    color: #232323;
    margin-left:-20px;
    margin-top:10px;
`;


const ArticlePostTime = styled.div`
    margin-top: 4px;
    word-break: break-all;
    white-space: pre-line;
    text-align :right;
`;

const ArticlePostRating = styled.div`
    margin-top: 4px;
    word-break: break-all;
    white-space: pre-line;
    margin-right: -200px;
`;

const ArticleBody = styled.div`
    margin-top: -5px;
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

const ModalSubmitBtn = styled.button`
    border: 1px solid black;
    border-radius: 5px;
    background-color: transparent; 
    padding: 10px;
    width: 100px;
    margin-left:33%;
    margin-bottom:5%;
    border: 1px solid #FFA500;
`;


const ArticleSubmitBtnPosition = styled.div`
    text-align: center;
`;

const PerChangeClassBtn = styled.button`
    width:53px;
    height:50px;
    border: 1px solid rgba(0, 0, 0, 0.125);
    border-radius: 5px;
    background-color: white; 
    padding: 10px;
    
`;

const PerHaveChangeClassBtn = styled.button`
    width:53px;
    height:50px;
    border: 1px solid rgba(0, 0, 0, 0.125);
    border-radius: 5px;
    background-color: white; 
    padding: 10px;
    background-color:#DEAD73;
`;
const ChangeClassCategorySelect = styled.select`
    font-family: 'Noto Serif TC', serif;
    border: 1px solid #FFA500;
    border-radius: 5px;
    background-color: transparent; 
    padding: 10px;
    width: 100px;
    margin-left: 68%;
    position: absolute;
    margin-top: 30%;
    z-index: 2;
`;


export {Page, Pagebg, Title, PostArticleBtn, ArticleDCText, ChooseArticleBtn, ArticleList, ArticleText, AlreadyArticleText, ArticlePostTime, ArticleContainer, ArticleRentContainer, ArticleFoodContainer, ArticleDistance,
     ArticleAuthorArea, ArticleAuthor, ArticleAuthorImg, ArticleBody, ArticlePostRating, ArticleSubmitBtn, ModalSubmitBtn, ArticleSubmitBtnPosition, PerChangeClassBtn, ArticleSelect,PerHaveChangeClassBtn,
     ChangeClassCategorySelect, ArticleBaiDistance, ArticleXiangDistance, ArticleXiDistance, ArticleZhongDistance,ArticleXingDistance};
