import {XeRole} from "../../business/constant/xe.role";

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
    ROLE_BUSS_MANAGER: {icon: 'user-tie', status: 'danger'},
    ROLE_CALLER_STAFF: {icon: 'headset', status: 'success'},
    ROLE_BUSS_STAFF: {icon: 'user-friends', status: 'warning'},
    ROLE_SYSTEM_MANAGER: {icon: 'user-cog', status: 'primary'},
  };

  static allRoleToIcons(): RoleIcon[] {
    const result = [];
    Object.keys(XeRole).forEach(role => {
      result.push({name: role, activeClass: "inactive", icon: RoleUtil.roleIcon[role]});
    });
    return result;
  }
}
