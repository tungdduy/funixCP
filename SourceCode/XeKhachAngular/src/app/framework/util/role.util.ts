import {Role, XeRole} from "../../business/xe.role";
import {XeLabel} from "../../business/i18n";

export interface RoleInfo {
  name: string;
  activeClass: 'active' | 'inactive';
  icon: {
    icon: string,
    status: 'danger' | 'success' | 'warning' | 'basic',
    hint: string
  };
}

export class RoleUtil {
  static roleInfo = {
    ROLE_BUSS_ADMIN: {icon: 'user-tie', status: 'danger', hint: XeLabel.ROLE_BUSS_STAFF},
    ROLE_CALLER_STAFF: {icon: 'headset', status: 'success', hint: XeLabel.ROLE_CALLER_STAFF},
    ROLE_BUSS_STAFF: {icon: 'user-friends', status: 'warning', hint: XeLabel.ROLE_BUSS_STAFF},
    ROLE_SYS_ADMIN: {icon: 'user-cog', status: 'primary', hint: XeLabel.ROLE_BUSS_ADMIN},
  };

  static getRolesInfo(rawRoles: string, exclude = ["ROLE_USER"]): RoleInfo[] {
    if (!rawRoles) return [];
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
