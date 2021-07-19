import {environment} from "../../../environments/environment";
import {XeRole} from "../../business/constant/xe.role";
import {UrlConfig} from "./url.config";

export const config = () => {
  return new UrlConfig();
};
const r = XeRole;

export const Url = {
  publicApi : [],
  API_HOST: environment.apiHost,
  APP_HOST: environment.appHost,
  getPublicApi: (apiUrls: any[]) => {
    apiUrls.forEach(apiUrl => {
      if (apiUrl instanceof UrlConfig) {
        if (apiUrl.isPublic()) {
          Url.publicApi.push(apiUrl.full);
        }
      } else {
        Url.getPublicApi(Object.values(apiUrl));
      }
    });
  },
  isPublicApi: (url: string) => {
    if (!Url.publicApi) {
      Url.publicApi = [];
      Url.getPublicApi(Object.values(Url.api));
    }
    return Url.publicApi.includes(url);
  },
  DEFAULT_URL_AFTER_LOGIN: (): string => {
    return Url.app.ADMIN._self.noHost;
  },
// ----------------------------------------------------------- //
// ================= IMPORT TO END OF FILE =================== //
// ----------------------------------------------------------- //
  api: {
    USER: {
      _self: config(),
      LOGIN: config(),
      REGISTER: config(),
      FORGOT_PASSWORD: config(),
      FORGOT_PASSWORD_SECRET_KEY: config(),
    },
    ADMIN: config(),
    CALLER_STAFF: config(),
    BUSS_STAFF: config(),
  },
  app: {
    CHECK_IN: {
      _self: config(),
      LOGIN: config(),
      REGISTER: config(),
      FORGOT_PASSWORD: config(),
      LOGOUT: config()
    },
    ADMIN: {
      _self: config().setRoles([r.ROLE_USER]),
      MY_ACCOUNT: config().setRoles([r.ROLE_USER]),
      MY_TRIP: config(),
      COMPANY_MANAGER: config(),
      CALLER_EMPLOYEE: config(),
      BUSS_TYPE: config(),
      BUSS: config(),
      BUSS_EMPLOYEE: config(),
      BUSS_STOP: config(),
      TICKET: config(),
    },
  }
};


