import {environment} from "../../../environments/environment";
import {UrlConfig} from "./url.config";
import {Role} from "../../business/xe.role";

export const config = () => {
  return new UrlConfig().public();
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
        if (apiUrl.isPublic() && apiUrl?.roles?.length === 0) {
          Url.publicApi.push(apiUrl.full.toLowerCase());
        }
      } else {
        Url.getPublicApi(Object.values(apiUrl));
      }
    });
    Url.publicApi.sort((u1, u2) => u2.length - u1.length);
  },
  isPublicApi: (url: string) => {
    if (!Url.publicApi || Url.publicApi.length === 0) {
      Url.publicApi = [];
      Url.getPublicApi(Object.values(Url.api));
    }
    return Url.publicApi.findIndex(u => url.toLowerCase().startsWith(u)) >= 0;
  },
  DEFAULT_URL_AFTER_LOGIN: () => Url.app.ADMIN.MY_ACCOUNT
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
      CHANGE_PASSWORD: uConfig(),
      UPDATE_PASSWORD: uConfig(),
    },
    TRIP: {
      _self: uConfig(),
      SEARCH_LOCATION: config(),
      FIND_BUSS_SCHEDULE: config(),
      FIND_SCHEDULED_LOCATIONS: config(),
      GET_TRIP_USERS: config(),
      GET_TRIP_BY_COMPANY_ID: config().setRoles([r.ROLE_BUSS_STAFF, r.ROLE_CALLER_STAFF]),
      GET_BUSS_SCHEDULES_BY_COMPANY_ID: config().setRoles([r.ROLE_BUSS_STAFF, r.ROLE_CALLER_STAFF]),
    },
    COMMON_UPDATE: {
      _self: uConfig(),
      TRIP: config().setRoles([r.ROLE_CALLER_STAFF, r.ROLE_BUSS_STAFF]),
      TRIP_USER: config(),
      USER: uConfig(),
      BUSS: uConfig(),
      BUSS_SCHEDULE: uConfig(),
      BUSS_SCHEDULE_POINT: uConfig(),
      BUSS_TYPE: config().setRoles([r.ROLE_SYS_ADMIN]),
      COMPANY: config().setRoles([r.ROLE_BUSS_ADMIN]),
      EMPLOYEE: uConfig(),
      LOCATION: config().setRoles([r.ROLE_SYS_ADMIN]),
      PATH: config().setRoles([r.ROLE_BUSS_ADMIN]),
      PATH_POINT: config().setRoles([r.ROLE_BUSS_ADMIN]),
      SEAT_GROUP: config().setRoles([r.ROLE_SYS_ADMIN]),
    },
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


