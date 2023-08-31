import styled from 'styled-components';
import backgroundImg from '../img/background.PNG';

const RemainTitle = styled.label`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    color:#008CBA;
    margin-top:10px;
`;

const RemainContainer = styled.div`
    z-index: 2;
    margin-top: 15px;
    width: 230px;
    height: 50px;
    background-color: white;
    border-radius: 10px;
`;

const RemainList = styled.div`
    display: flex;
    flex-direction: column;
    margin-top: 30px;
    overflow: auto;
    height: 75%;
    will-change: transform;
    position: absolute;
    left: 50%;
    top: 55%;
    transform: translate(-50%, -50%);
`;

const RemainBody = styled.div`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    width: 230px;
    height: 50px;
    background-color: white;
    border-radius: 10px;
    padding: 16px;
    background-color: white;
    margin-top:2%;
    margin-left:7px;
`;

const RemainText = styled.div`
    padding-top: 12px;
    word-break: break-all;
    white-space: pre-line;
    font-size:14px;
    text-align:center;
`;

const Mustinfo = styled.label`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    color:#008CBA;
    margin-top:10px;
    margin-left: auto;
    margin-right: auto;
    z-index: 2;
    width: 350px;
    height: 60px;
    background-color: white;
    border-radius: 10px;
    display: flex;
    flex-wrap: wrap;
    padding-left: 10px;
`;

const BackBtn = styled.img`
    max-height: 40px;
    max-width: 40px;
    padding-top: 10px;
    position: absolute;
    z-index: 3;
`;

const Back = styled.img`
    max-height: 40px;
    max-width: 40px;
    padding-top: 10px;
    position: absolute;
    z-index: 3;
`;

const MyclassBody = styled.div`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    width: 300px;
    height: 60px;
    background-color: white;
    border-radius: 10px;
    padding: 16px;
    background-color: white;
    margin-top:2%;
    margin-left:7px;
`;

const MyclassText = styled.div`
    padding-top: 12px;
    word-break: break-all;
    white-space: pre-line;
    font-size:15px;
    text-align:center;
`;

const Coreinfo = styled.label`
    @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@300&display=swap');
    font-family: 'Noto Serif TC', serif;
    color:#008CBA;
    margin-top:5%;
    margin-left: auto;
    margin-right: auto;
    z-index: 2;
    width: 90%;
    height: 25%;
    background-color: white;
    border-radius: 10px;
    display: flex;
    flex-wrap: wrap;
    padding-left: 2%;
`;

const CoreField = styled.label`
    border-color:black;
    border-width:1px;
    border-style:solid;
    width:40%;
    height:20%;
    margin-top:2%;
    color:black;
`;

export {RemainTitle, RemainContainer, RemainList, Mustinfo, RemainText, RemainBody, BackBtn, MyclassBody, MyclassText, Back, Coreinfo, CoreField} ;
