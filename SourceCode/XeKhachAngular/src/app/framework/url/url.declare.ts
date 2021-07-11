import {environment} from "../../../environments/environment";
import {Authority} from "../../business/constant/auth.enum";
import {XeRole} from "../../business/constant/xe.role";
import {UrlConfig} from "./url.config";

export const config = () => {
  return new UrlConfig();
};
const r = XeRole;
const a = Authority;

export const Url = {
  publicApi : [],
  API_HOST: environment.apiHost,
  APP_HOST: environment.appHost,
  getPublicApi: (apiUrls: UrlConfig[]) => {
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
    return Url.app.CHECK_IN.FORGOT_PASSWORD.full;
  },
// ----------------------------------------------------------- //
// ================= IMPORT TO END OF FILE =================== //
// ----------------------------------------------------------- //
  api: {
    USER: config(),
    ADMIN: config(),
    CALLER_STAFF: config(),
    BUSS_STAFF: config(),
  },
  app: {
    CHECK_IN: {
      __self: config(),
      LOGIN: config(),
      REGISTER: config(),
      FORGOT_PASSWORD: config().auths([r.ROLE_BUSS_ADMIN, a.ADMIN_READ]),
    },
    ADMIN: {
      __self: config(),
      BUSS_STAFF: config(),
    },
  }
};


