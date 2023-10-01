const TOKEN_NAME = "token";
const FCM_TOKEN_NAME = "fcmToken";

// 將 token 存到 localStorage
export const setAuthToken = (token) => {
  localStorage.setItem(TOKEN_NAME, token);
};

// 從 localStorage 讀取 token
export const getAuthToken = () => {
  return localStorage.getItem(TOKEN_NAME);
};

// 將 token 存到 localStorage
export const setFcmToken = (fcmToken) => {
  localStorage.setItem(FCM_TOKEN_NAME, fcmToken);
};

// 從 localStorage 讀取 token
export const getFcmToken = () => {
  return localStorage.getItem(FCM_TOKEN_NAME);
};