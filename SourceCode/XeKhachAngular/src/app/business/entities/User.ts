// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {Employee} from "./Employee";
import {TripUser} from "./TripUser";
import {InputTemplate} from "../../framework/model/EnumStatus";
import {EntityUtil} from "../../framework/util/EntityUtil";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class User extends XeEntity {
    static meta = EntityUtil.metas.User;
    userId: number;
    employee: Employee;
    email: string;
    phoneNumber: string;
    password: string;
    username: string;
    fullName: string;
    role: string;
    nonLocked: boolean;
    secretPasswordKey: string;
    profileImageUrl = this.initProfileImage();
// ____________________ ::BODY_SEPARATOR:: ____________________ //
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
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (user: User): EntityIdentifier<User> => ({
    entity: user,
    clazz: User,
    idFields: [
      {name: "userId"},
    ]
  })

  static new(option = {}) {
    return new User();
  }

  static tableData = (option: XeTableData<User> = {}, user: User = User.new()): XeTableData<User> => {
    const table = User._userTable(user);
    EntityUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _userTable = (user: User): XeTableData<User> => {
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    return {
      table: {
        basicColumns: [
          {field: {name: 'profileImageUrl'}, type: "avatar"},
          {
            field: {name: 'fullName'}, type: "boldStringRole", display: {header: {inline: true}},
            subColumns: [{
              field: {name: 'username'},
              type: 'string',
              display: {header: {icon: {iconOnly: 'id-card'}}, row: {css: 'd-block text-info'}}
            }]
          },
          { // 2
            field: {name: 'email'}, type: "string", display: {header: {icon: {iconOnly: 'at'}, inline: true}},
            subColumns: [{
              field: {name: 'phoneNumber', template: InputTemplate.phone},
              display: {row: {css: 'd-block text-info'}, header: {icon: {iconOnly: 'mobile-alt'}}},
              type: 'string',
            }]
          },
        ],
      },
      formData: {
        entityIdentifier: User.entityIdentifier(user),
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
          {name: "password", clearOnSuccess: true},
          // 5
          {name: "role", hidden: true},
        ]
      }
    };
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

