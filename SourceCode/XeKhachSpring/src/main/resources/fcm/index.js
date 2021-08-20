async function init() {

    const registration = await navigator.serviceWorker.register('sw.js');
    await navigator.serviceWorker.ready;
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
    messaging.useServiceWorker(registration);

    try {
        await messaging.requestPermission();
    } catch (e) {
        console.log('Unable to get permission', e);
        return;
    }

    navigator.serviceWorker.addEventListener('message', event => {
        console.log(event);
        if (event.data.hasOwnProperty("newData")) {
            alert(event.data.newData.body);
        }
    });

    const currentToken = await messaging.getToken();
    console.log("Current Token: "+currentToken);
    fetch('/subscribe', { method: 'post', body: currentToken });
    //
    messaging.onTokenRefresh(async () => {
        console.log('token refreshed');
        const newToken = await messaging.getToken();
        fetch('/subscribe', { method: 'post', body: currentToken });
    });

}

init();