import {Component, Input} from '@angular/core';
import {XeTableData} from "../../model/XeTableData";
import {RoleInfo, RoleUtil} from "../../util/role.util";
import {User} from "../../../business/entities/User";
import {FormAbstract} from "../../model/form.abstract";
import {XeScreen} from "../xe-nav/xe-nav.component";

@Component({
  selector: 'xe-user-table',
  templateUrl: './user-table.component.html',
  styleUrls: ['./user-table.component.scss']
})
export class UserTableComponent extends FormAbstract {

  @Input() xeScreen: XeScreen;
  @Input() userTable: XeTableData<any>;
  @Input() user: () => User = () => {
    const entity = () => this.userTable?.formData?.share?.entity;
    switch (this.userTable?.formData?.entityIdentifier?.clazz?.className) {
      case 'User':
        return entity();
      case 'Employee':
        return entity().user;
      case 'BussEmployee':
        return entity()?.employee?.user;
      default: return entity();
    }
  }
  @Input() excludeRoles: string[] = ['ROLE_SYS_ADMIN'];

  private _roleIcons: RoleInfo[];
  get roleIcons(): RoleInfo[] {
    if (this._roleIcons === undefined) {
      this._roleIcons = RoleUtil.allRolesInfo(this.excludeRoles);
    }
    return this._roleIcons;
  }

  getRoleActiveClass(roleName) {
    return this.getSharingUserRoles()?.includes(roleName) ? 'active' : 'inactive';
  }

  getSharingUserRoles() {
    return User.getRoles(this.user()?.role);
  }

  toggleRole(role: RoleInfo) {
    const userRoles = this.getSharingUserRoles();
    if (!!userRoles) {
      for (let i = 0; i < userRoles.length; i++) {
        if (userRoles[i] === role.name) {
          userRoles.splice(i, 1);
          role.activeClass = 'inactive';
          User.setRole(this.user(), userRoles);
          return;
        }
      }
      role.activeClass = 'active';
      userRoles.push(role.name);
      User.setRole(this.user(), userRoles);
    }
  }


}
