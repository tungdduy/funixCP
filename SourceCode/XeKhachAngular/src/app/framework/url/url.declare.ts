import {environment} from "../../../environments/environment";
import {Authority} from "../../business/auth.enum";
import {XeRole} from "../../business/xe.role";
import {UrlConfig} from "./url.config";

export const config = () => {
  return new UrlConfig();
};
const r = XeRole;
const a = Authority;

export const Url = {
  API_HOST: environment.apiHost,
  APP_HOST: environment.appHost,
  DEFAULT_URL_AFTER_LOGIN: (): string => {
    return Url.app.CHECK_IN.FORGOT_PASSWORD.full;
  },
  // START of IMPORT SECTION ========
  // ****@@@****
  api: {
    USER: {
      __self: config(),
      LOGOUT: config(),
      LOGIN: config(),
      REGISTER: config(),
      FORGOT_PASSWORD: config(),
    }
  },
  app: {
    CHECK_IN: {
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
  opp: {
    CHECK_IN: config().auths([r.USER])
  }
  // ****@@@****
  // END OF IMPORT SECTION ==============

};


