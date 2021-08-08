export enum Role {
  ROLE_USER = "ROLE_USER",
  ROLE_BUSS_STAFF = "ROLE_BUSS_STAFF",
  ROLE_CALLER_STAFF = "ROLE_CALLER_STAFF",
  ROLE_BUSS_ADMIN = "ROLE_BUSS_ADMIN",
  ROLE_SYS_ADMIN = "ROLE_SYS_ADMIN",
}

export class XeRole {
  static ROLE_USER = {
    roles: [],
  };
  static ROLE_BUSS_STAFF = {
    roles: [Role.ROLE_USER],
  };
  static ROLE_CALLER_STAFF = {
    roles: [Role.ROLE_USER],
  };
  static ROLE_BUSS_ADMIN = {
    roles: [Role.ROLE_BUSS_STAFF, Role.ROLE_CALLER_STAFF],
  };
  static ROLE_SYS_ADMIN = {
    roles: [Role.ROLE_BUSS_ADMIN],
  };
}


