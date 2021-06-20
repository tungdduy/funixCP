import {environment} from "../../environments/environment";

export const AppUrl = {
  LOGIN : "login",
  LOGOUT : "logout",
  REGISTER : "register",
  EMPTY: "",
  DEFAULT_URL_AFTER_LOGIN : "user/management",
}

export const ApiUrl = {
  HOST : environment.apiUrl,
  LOGOUT : `${environment.apiUrl}/user/logout`,
  LOGIN : `${environment.apiUrl}/user/login`,
  REGISTER : `${environment.apiUrl}/user/register`,
}
