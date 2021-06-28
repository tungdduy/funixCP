import {environment} from "../../../environments/environment";

const DeclaredUrl = {
  _api: {
    HOST: environment.apiUrl,
    USER: {
      index: 'user',
      LOGOUT: 'logout',
      LOGIN: 'login',
      REGISTER: 'register',
      FORGOT_PASSWORD: 'forgot-password',
    }
  },
  _app: {
    HOST: environment.appUrl,
    ACCOUNT: {
      index: 'auth',
      REGISTER: "register",
      LOGIN: "login",
      FORGOT_PASSWORD: "forgot-password",
    },
    EMPTY: "",
    DEFAULT_URL_AFTER_LOGIN: "forgot-password",
    ADMIN: {
      index: "admin",
      BUSS_STAFF: "buss-staff"
    },
  }

};

export const XeUrl = {

  short: {
    app: DeclaredUrl._app,
    api: DeclaredUrl._api
  },

  full: {
    app: Object.assign({}, DeclaredUrl._app) ,
    api: Object.assign({}, DeclaredUrl._api) ,
  },

  noHost: {
    app: Object.assign({}, DeclaredUrl._app) ,
    api: Object.assign({}, DeclaredUrl._api) ,
  },

  publicApi: [
    DeclaredUrl._api.USER.LOGIN,
    DeclaredUrl._api.USER.LOGOUT,
    DeclaredUrl._api.USER.REGISTER,
  ]

};


