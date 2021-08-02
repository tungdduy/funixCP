import {XeEntity} from "./xe-entity";

export class User extends XeEntity {
  userId: string;
  username: string;
  email: string;
  phoneNumber: string;
  fullName: string;
  role: string;
  roles: string[];

  static setRole(user: any, roles: string[]) {
    if (!roles) {
      user.role = undefined;
    } else {
      user.role = roles.join(",");
    }
  }

  static getRoles(roleString: string) {
    if (!roleString) return [];
    return roleString.split(",").filter(s => s.startsWith("ROLE_"));
  }
}
