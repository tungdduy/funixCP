import {environment} from "../../../environments/environment";
import {UrlConfig} from "./url.config";
import {Role} from "../../business/xe.role";

export const config = () => {
  return new UrlConfig();
};
export const uConfig = () => {
  return new UrlConfig().setRoles([r.ROLE_USER]);
};
const r = Role;

export const Url = {
  publicApi: [],
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
  DEFAULT_URL_AFTER_LOGIN: () => Url.app.ADMIN._self
  ,
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
      CHANGE_PASSWORD: config(),
      UPDATE_PASSWORD: config(),
      SUBSCRIBE: config(),
      UNSUBSCRIBE: config(),
    },
    TRIP: {
      _self: config(),
      SEARCH_LOCATION: config(),
      FIND_BUSS_SCHEDULES: config(),
      FIND_SCHEDULED_LOCATIONS: config(),
    },
    CALLER_STAFF: config(),
    BUSS_STAFF: config(),
  },
  app: {
    CHECK_IN: {
      _self: config(),
      LOGIN: config(),
      REGISTER: config(),
      FORGOT_PASSWORD: config(),
      LOGOUT: config(),
    },
    ADMIN: {
      _self: config(),
      MY_ACCOUNT: uConfig(),
      MY_COMPANY: config().setRoles([r.ROLE_BUSS_ADMIN]),
      ALL_USER: config().setRoles([r.ROLE_SYS_ADMIN]),
      MY_TRIP: config(),
      FIND_TRIP: config(),
      COMPANY_MANAGER: config().setRoles([r.ROLE_SYS_ADMIN]),
      BUSS_TYPE: config().setRoles([r.ROLE_SYS_ADMIN]),
      BUSS: config().setRoles([r.ROLE_BUSS_STAFF, r.ROLE_CALLER_STAFF]),
      EMPLOYEE: config().setRoles([r.ROLE_BUSS_ADMIN]),
      PATH: config().setRoles([r.ROLE_BUSS_STAFF, r.ROLE_CALLER_STAFF]),
      TICKET: config().setRoles([r.ROLE_BUSS_STAFF, r.ROLE_CALLER_STAFF]),
    },
  }
};


