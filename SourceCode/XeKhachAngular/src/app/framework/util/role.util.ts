import {Role, XeRole} from "../../business/xe.role";

export interface RoleInfo {
  name: string;
  activeClass: 'active' | 'inactive';
  icon: {
    icon: string,
    status: 'danger' | 'success' | 'warning' | 'basic'
  };
}

export class RoleUtil {
  static roleInfo = {
    ROLE_BUSS_ADMIN: {icon: 'user-tie', status: 'danger'},
    ROLE_CALLER_STAFF: {icon: 'headset', status: 'success'},
    ROLE_BUSS_STAFF: {icon: 'user-friends', status: 'warning'},
    ROLE_SYS_ADMIN: {icon: 'user-cog', status: 'primary'},
  };

  static getRolesInfo(rawRoles: string, exclude = ["ROLE_USER"]): RoleInfo[] {
    const roles = rawRoles.split(",");
    const result: RoleInfo[] = [];
    roles.forEach(role => {
      const info: RoleInfo = {
        name: role,
        activeClass: "active",
        icon: RoleUtil.roleInfo[role],
      };
      if (!exclude || !exclude.includes(info.name)) {
        result.push(info);
      }
    });
    return result;
  }

  static allRolesInfo(exclude = ["ROLE_USER"]): RoleInfo[] {
    const result = [];
    Object.keys(Role).forEach(role => {
      if (!exclude || !exclude.includes(role)) {
        result.push({name: role, activeClass: "inactive", icon: RoleUtil.roleInfo[role]});
      }
    });
    return result;
  }

  static flatRoles(roles: Role[]): Role[] {
    const result = new Set<Role>();
    this._flatRoles(roles, result);
    return Array.from(result);
  }

  private static _flatRoles(roles: Role[], resultSet: Set<Role>) {
    if (!Array.isArray(roles)) {
      return [];
    }
    const r = [];
    for (const role of roles) {
      if (role !== undefined) {
        resultSet.add(role);
      }
      if (Array.isArray(XeRole[role]?.roles)) {
        this._flatRoles(XeRole[role].roles, resultSet);
      }
    }
  }

}
