import {environment} from "../../../environments/environment";

export const AppUrl = {
  LOGIN : "auth/login",
  LOGOUT : "auth/logout",
  REGISTER : "auth/register",
  EMPTY: "",
  DEFAULT_URL_AFTER_LOGIN : "auth/forgot-password",
};

export const ApiUrl = {
  HOST : environment.apiUrl,
  LOGOUT : `${environment.apiUrl}/user/logout`,
  LOGIN : `${environment.apiUrl}/user/login`,
  REGISTER : `${environment.apiUrl}/user/register`,
};
