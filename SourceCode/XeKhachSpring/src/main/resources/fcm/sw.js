importScripts('https://www.gstatic.com/firebasejs/7.10.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/7.10.0/firebase-messaging.js');

firebase.initializeApp({
    apiKey: "AIzaSyBlQMD_5JaHI_2Q45z2gNkuLStNu2U7kLA",
    authDomain: "capstone-308403.firebaseapp.com",
    databaseURL: "https://capstone-308403-default-rtdb.asia-southeast1.firebasedatabase.app",
    projectId: "capstone-308403",
    storageBucket: "capstone-308403.appspot.com",
    messagingSenderId: "418538636793",
    appId: "1:418538636793:web:9703c0ae967a959da1a928",
    measurementId: "G-F1VMPD3XZZ"
});

const messaging = firebase.messaging();
messaging.usePublicVapidKey('BOjXxbOEy6KoGPWSgq5_RaTZFuANQ0nR3lxAmtl0ZmvsHrKVqVJ00PEUQstF3WfJdrCG4w3pbteslUEXJg1cb7s');

self.addEventListener('push', async event => {
    console.log("push event");
    console.log(event.data.json().notification);
    const allClients = await clients.matchAll({ includeUncontrolled: true });
    for (const client of allClients) {
        client.postMessage({newData : event.data.json().notification});
    }
});

messaging.setBackgroundMessageHandler(function(payload) {
    console.log("BackgroundMessageHandler: "+payload);
    const notificationTitle = 'Background Title (client)';
    const notificationOptions = {
        body: 'Background Body (client)',
        icon: '/mail.png'
    };

    return self.registration.showNotification(notificationTitle,
        notificationOptions);
});


const CACHE_NAME = 'my-site-cache-v1';
const urlsToCache = [
    '/index.html',
    '/index.js',
    '/mail.png',
    '/mail2.png'
    //, '/manifest.json?_ijt=8bjk2lnll6rsvgtonqu35bf5ci'
];

// self.addEventListener('install', event => {
//     event.waitUntil(caches.open(CACHE_NAME)
//         .then(cache => cache.addAll(urlsToCache)));
// });

self.addEventListener('fetch', event => {
    console.log("fetch event")
    event.respondWith(
        caches.match(event.request)
            .then(response => {
                    if (response) {
                        return response;
                    }
                    return fetch(event.request);
                }
            )
    );
});