import {Component, Input, OnInit} from '@angular/core';
import {XeTableData} from "../../../business/abstract/XeTableData";
import {RoleIcon, RoleUtil} from "../../util/role.util";
import {User} from "../../../business/entities/user";
import {FormAbstract} from "../../../business/abstract/form.abstract";

@Component({
  selector: 'xe-user-table',
  templateUrl: './user-table.component.html',
  styleUrls: ['./user-table.component.scss']
})
export class UserTableComponent extends FormAbstract {

  @Input() userTable: XeTableData;
  @Input() user: () => User;

  private _roleIcons: RoleIcon[];
  get roleIcons(): RoleIcon[] {
    if (this._roleIcons === undefined) {
      this._roleIcons = RoleUtil.allRoleToIcons();
    }
    return this._roleIcons;
  }

  getRoleActiveClass(roleName) {
    return this.getSharingUserRoles()?.includes(roleName) ? 'active' : 'inactive';
  }

  getSharingUserRoles() {
    return User.getRoles(this.user()?.role);
  }

  toggleRole(role: RoleIcon) {
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
