import {Component} from '@angular/core';
import {XeTableData} from "../../../abstract/XeTableData";
import {User} from "../../../entities/user";

@Component({
  selector: 'xe-buss',
  styles: [],
  templateUrl: 'buss.component.html',
})
export class BussComponent {
  bussTable: XeTableData = {
    table: {
      basicColumns: [
        {field: {name: 'profileImageUrl'}, type: "avatar"},
        {field: {name: 'fullName'}, type: "boldStringRole"},
        {field: {name: 'phoneNumber'}, type: "string"},
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
