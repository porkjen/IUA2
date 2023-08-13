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

export {RemainTitle, RemainContainer, RemainList, Mustinfo} ;