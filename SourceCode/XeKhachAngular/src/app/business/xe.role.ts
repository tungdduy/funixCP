import {Authority} from "./auth.enum";

export const XeRole = {
  USER: {
    roles: [],
    authorities: [Authority.USER_READ, Authority.USER_WRITE]
  },
  ADMIN: {
    roles: [],
    authorities: [Authority.ADMIN_READ, Authority.ADMIN_WRITE]
  },
  BUSS_STAFF: {
    roles: [],
    authorities: [Authority.BUSS_STAFF_READ, Authority.BUSS_STAFF_WRITE]
  },
  CALLER_STAFF: {
    roles: [],
    authorities: [Authority.CALLER_STAFF_READ, Authority.CALLER_STAFF_WRITE]
  }
};
