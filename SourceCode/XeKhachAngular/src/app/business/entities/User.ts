import {XeEntity} from "./XeEntity";
import {Employee} from "./Employee";
import {XeTableData} from "../../framework/model/XeTableData";
import {ObjectUtil} from "../../framework/util/object.util";
import {PhonePipe} from "../../framework/components/pipes/phone-pipe";

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
          {field: {name: 'fullName'}, type: "boldStringRole", inline: true,
            subColumns: [{
              field: {name: 'username', css: 'd-block text-info'}, type: 'string', icon: {iconOnly: 'id-card'}
            }]
          },
          { // 2
            field: {name: 'email'}, type: "string", icon: {iconOnly: 'at'}, inline: true,
            subColumns: [{
              field: {name: 'phoneNumber', pipe: PhonePipe.instance, css: 'd-block text-info'}, type: 'string', icon: {iconOnly: 'mobile-alt'}
            }]
          },
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
          // 0
          {name: "username", required: true},
          // 1
          {name: "phoneNumber", required: true},
          // 2
          {name: "fullName", required: true},
          // 3
          {name: "email", required: true},
          // 4
          {name: "password", clearOnSuccess: true, hidden: true},
          // 5
          {name: "role", hidden: true},
        ]
      }
    };
  }
}
