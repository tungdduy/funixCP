import {environment} from "../../environments/environment";
import {Authority} from "./auth.enum";
import {XeRole} from "./xe.role";
import {UrlConfig} from "../framework/url/url.config";

export const config = () => {
  return new UrlConfig();
};
const r = XeRole;
const a = Authority;

export const Url = {
  API_HOST: environment.apiHost,
  api: {
    USER: {
      __self: config(),
      LOGOUT: config(),
      LOGIN: config(),
      REGISTER: config(),
      FORGOT_PASSWORD: config(),
    }
  },
  APP_HOST: environment.appHost,
  app: {
    AUTH: {
      __self: config().auths([r.USER, a.CALLER_STAFF_WRITE]),
      REGISTER: config(),
      LOGIN: config(),
      FORGOT_PASSWORD: config(),
    },
    ADMIN: {
      __self: config(),
      BUSS_STAFF: config()
    },
  },
  DEFAULT_URL_AFTER_LOGIN: (): string => {
    return Url.app.AUTH.FORGOT_PASSWORD.full;
  }

};


