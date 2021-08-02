import {Component} from '@angular/core';
import {FormAbstract} from "../../../abstract/form.abstract";
import {User} from "../../../model/user";
import {XeTableData} from "../../../abstract/XeTableData";
import {RoleIcon, RoleUtil} from "../../../../framework/util/role.util";

@Component({
  selector: 'xe-all-user',
  templateUrl: './all-user.component.html',
  styleUrls: ['./all-user.component.scss']
})
export class AllUserComponent extends FormAbstract {

  userTable: XeTableData = {
    share: {entity: User},
    className: "User",
    idColumns: {userId: 0},
    table: {
      basicColumns: [
        {name: 'profileImageUrl', type: "avatar"},
        {name: 'username', type: "boldStringRole"},
        {name: 'fullName', type: "string"},
        {name: 'email', type: "string"},
      ],
    },
    formData: {
      share: {entity: User},
      header: {
        titleKey: 'fullName',
        descKey: 'phoneNumber',
      },
      fields: [
        {name: "username", required: true},
        {name: "phoneNumber", required: true},
        {name: "fullName", required: true},
        {name: "email", required: true},
        {name: "password", required: true, newOnly: true},
        {name: "role", hidden: true},
      ]
    }
  };

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
    return User.getRoles(this.userTable.share.entity?.role);
  }

  toggleRole(role: RoleIcon) {
    const userRoles = this.getSharingUserRoles();
    if (!!userRoles) {
      for (let i = 0; i < userRoles.length; i++) {
        if (userRoles[i] === role.name) {
          userRoles.splice(i, 1);
          role.activeClass = 'inactive';
          User.setRole(this.userTable.share.entity, userRoles);
          return;
        }
      }
      role.activeClass = 'active';
      userRoles.push(role.name);
      User.setRole(this.userTable.share.entity, userRoles);
    }
  }
}
