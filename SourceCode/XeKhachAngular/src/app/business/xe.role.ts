import {Authority} from "./auth.enum";

export class XeRole {
  static ROLE_BUSS_ADMIN = {
    roles: [],
    authorities: [Authority.ADMIN_READ, Authority.ADMIN_WRITE]
  };
  static ROLE_BUSS_STAFF = {
    roles: [XeRole.ROLE_BUSS_ADMIN],
    authorities: [Authority.USER_WRITE, Authority.USER_READ]
  };
  static ROLE_CALLER_STAFF = {
    roles: [XeRole.ROLE_BUSS_ADMIN, XeRole.ROLE_BUSS_STAFF],
    authorities: [Authority.USER_WRITE, Authority.USER_READ, Authority.ADMIN_READ]
  };
  static ROLE_SYS_ADMIN = {
    roles: [],
    authorities: []
  };
  static ROLE_USER = {
    roles: [],
    authorities: []
  };
}


