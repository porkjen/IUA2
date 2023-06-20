import cookie from 'react-cookies'

export const onLogin = (value) =>{
    cookie.save('id', value, {path: '/'}); //for all pages
}

export const loginUser = () => {
    return cookie.load('id');
}