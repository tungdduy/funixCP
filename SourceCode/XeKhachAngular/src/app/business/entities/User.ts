import {XeEntity} from "./XeEntity";
import {Employee} from "./Employee";
import {XeTableData} from "../../framework/model/XeTableData";
import {ObjectUtil} from "../../framework/util/object.util";

export class User extends XeEntity {
  userId: string;
  username: string;
  email: string;
  phoneNumber: string;
  fullName: string;
  role: string;
  roles: string[];
  employee: Employee;

  profileImageUrl = this.initProfileImage();

  constructor() {
    super();
  }

  static setRole(user: any, roles: string[]) {
    if (!roles) {
      user.role = undefined;
    } else {
      user.role = roles.join(",");
    }
  }

  static getRoles(roleString: string): string[] {
    if (!roleString) return [];
    return roleString.split(",").filter(s => s.startsWith("ROLE_"));
  }

  static userTable = (option: {} = {}): XeTableData => {
    return ObjectUtil.assignEntityTable(option, User._userTable());
  }

  private static _userTable = (): XeTableData => {
    return {
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
        share: {entity: new User()},
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
}
