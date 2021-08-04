import {Component} from '@angular/core';
import {FormAbstract} from "../../../abstract/form.abstract";
import {User} from "../../../entities/user";
import {XeTableData} from "../../../abstract/XeTableData";

@Component({
  selector: 'xe-all-user',
  templateUrl: './all-user.component.html',
  styleUrls: ['./all-user.component.scss']
})
export class AllUserComponent extends FormAbstract {
  user = () => this.userTable.formData.share.entity;
  userTable: XeTableData = {
    table: {
      basicColumns: [
        {field: {name: 'profileImageUrl'}, type: "avatar"},
        {field: {name: 'username'}, type: "boldStringRole"},
        {field: {name: 'fullName'}, type: "string"},
        {field: {name: 'email'}, type: "string"},
      ],
    },
    formData: {
      entityIdentifier: {
        className: "User",
        idFields: () => [
          {name: "userId", value: 0}
        ],
      },
      share: {entity: User},
      header: {
        profileImage: {name: 'profileImageUrl'},
        titleField: {name: 'fullName'},
        descField: {name: 'phoneNumber'},
      },
      fields: [
        {name: "username", required: true},
        {name: "phoneNumber", required: true},
        {name: "fullName", required: true},
        {name: "email", required: true},
        {name: "password", clearOnSuccess: true},
        {name: "role", hidden: true},
      ]
    }
  };
}
