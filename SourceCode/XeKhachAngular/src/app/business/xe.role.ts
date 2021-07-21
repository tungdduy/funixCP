import {Authority} from "./auth.enum";

export class XeRole {
  static ROLE_USER = {
    roles: [],
  };
  static ROLE_BUSS_STAFF = {
    roles: [],
  };
  static ROLE_CALLER_STAFF = {
    roles: [],
  };
  static ROLE_BUSS_ADMIN = {
    roles: [XeRole.ROLE_BUSS_STAFF, XeRole.ROLE_CALLER_STAFF],
  };
  static ROLE_SYS_ADMIN = {
    roles: [],
  };
}


