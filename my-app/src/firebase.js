import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from "firebase/messaging";
// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyCM-CHH1jYy8p862zp1n9vpmum6PvTZGxI",
  authDomain: "iua-project-2f2c5.firebaseapp.com",
  projectId: "iua-project-2f2c5",
  storageBucket: "iua-project-2f2c5.appspot.com",
  messagingSenderId: "361277289213",
  appId: "1:361277289213:web:89cf7bef7f58b1dd4b0494"
};

initializeApp(firebaseConfig);

const firebaseApp = initializeApp(firebaseConfig);
const messaging = getMessaging(firebaseApp);

const firebase=()=>{
}

export const requestToken = (setTokenFound) => {
    return getToken(messaging, {vapidKey: 'BJvj7dXnLP_ORK8Tk1GEnnHzlQiIU94UlG2UN6S6uyzWkQqgAUxxxnzkHojO51RsnM7X1IwQDtQyuc95BdCJVm4'}).then((currentToken) => {
      if (currentToken) {
        console.log('current token for client: ', currentToken);
        setTokenFound(currentToken);
        // Track the token -> client mapping, by sending to backend server
        // show on the UI that permission is secured
      } else {
        console.log('No registration token available. Request permission to generate one.');
        setTokenFound('');
        // shows on the UI that permission is required 
      }
    }).catch((err) => {
      console.log('An error occurred while retrieving token. ', err);
      // catch error while creating client token
    });
    
}

export const onMessageListener = () =>
  new Promise((resolve) => {
    onMessage(messaging, (payload) => {
      resolve(payload);
    });
});

export default firebase;