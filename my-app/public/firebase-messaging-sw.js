importScripts("https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js")
importScripts("https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js")

const firebaseConfig = {
    apiKey: "AIzaSyCM-CHH1jYy8p862zp1n9vpmum6PvTZGxI",
    authDomain: "iua-project-2f2c5.firebaseapp.com",
    projectId: "iua-project-2f2c5",
    storageBucket: "iua-project-2f2c5.appspot.com",
    messagingSenderId: "361277289213",
    appId: "1:361277289213:web:89cf7bef7f58b1dd4b0494"
  };
firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

messaging.onBackgroundMessage(function(payload) {
  console.log('[firebase-messaging-sw.js] Received background message ', payload);
  // Customize notification here
  const notificationTitle = payload.notification.title;
  const notificationOptions = {
    body: payload.notification.body
  };

  self.registration.showNotification(notificationTitle, notificationOptions);
});

self.addEventListener('push', e => {
  console.log('[Service Worker] Push Received.');
  let title = 'Hello!';
  let options = {
    body: 'This is IUA.'
  };
  if(e.data){
    options = e.data.json();
    title = options.title;
  }
  e.waitUntil(self.registration.showNotification(title, options));
})

self.addEventListener('notificationclick', e => {
  const notification = e.notification;
  const action = e.action;
  const link = notification.data.link;
  if(action != 'close'){
      if(link){
        clients.openWindow(link);
      }
  }
  notification.close();
  console.log('notificationclick action');
})