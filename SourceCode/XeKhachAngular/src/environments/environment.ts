/**
 * @license
 * Copyright Akveo. All Rights Reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  apiHost: 'http://localhost:8081',
  appHost: 'http://localhost:4200',
  firebase: {
    apiKey: "AIzaSyBlQMD_5JaHI_2Q45z2gNkuLStNu2U7kLA",
    authDomain: "capstone-308403.firebaseapp.com",
    databaseURL: "https://capstone-308403-default-rtdb.asia-southeast1.firebasedatabase.app",
    projectId: "capstone-308403",
    storageBucket: "capstone-308403.appspot.com",
    messagingSenderId: "418538636793",
    appId: "1:418538636793:web:9703c0ae967a959da1a928",
    measurementId: "G-F1VMPD3XZZ"
  },
  VAPID: "BOjXxbOEy6KoGPWSgq5_RaTZFuANQ0nR3lxAmtl0ZmvsHrKVqVJ00PEUQstF3WfJdrCG4w3pbteslUEXJg1cb7s"
};
