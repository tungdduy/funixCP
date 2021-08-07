import {Role, XeRole} from "../../business/xe.role";

export interface RoleIcon {
  name: string;
  activeClass: 'active' | 'inactive';
  icon: {
    icon: string,
    status: 'danger' | 'success' | 'warning' | 'basic'
  };
}

export class RoleUtil {
  static roleIcon = {
    ROLE_BUSS_ADMIN: {icon: 'user-tie', status: 'danger'},
    ROLE_CALLER_STAFF: {icon: 'headset', status: 'success'},
    ROLE_BUSS_STAFF: {icon: 'user-friends', status: 'warning'},
    ROLE_SYS_ADMIN: {icon: 'user-cog', status: 'primary'},
  };

  static allRoleToIcons(): RoleIcon[] {
    const result = [];
    Object.keys(Role).forEach(role => {
      result.push({name: role, activeClass: "inactive", icon: RoleUtil.roleIcon[role]});
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
