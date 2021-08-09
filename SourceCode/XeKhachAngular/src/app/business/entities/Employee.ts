import {XeEntity} from "./XeEntity";
import {User} from "./User";
import {Company} from "./Company";
import {XeTableData} from "../../framework/model/XeTableData";
import {XeScreen} from "../../framework/components/xe-nav/xe-nav.component";
import {ObjectUtil} from "../../framework/util/object.util";
import {PhonePipe} from "../../framework/components/pipes/phone-pipe";

export class Employee extends XeEntity {
  companyId: number;
  employeeId: number;
  userId: number;
  isLock: boolean;
  user: User;
  company: Company;
  countBusses: number;

  profileImageUrl = this.initProfileImage();

  static employeeTable = (option: {} = {}): XeTableData => {
    return ObjectUtil.assignEntityTable(option, Employee._employeeTable());
  }

  private static _employeeTable = (): XeTableData => ({
    table: {
      basicColumns: [
        // 0
        {field: {name: 'user.fullName'}, type: "avatarString"},
        // 1
        {field: {name: 'user.username'}, type: "boldStringRole"},
        { // 2
          field: {name: 'user.email'}, type: "string", icon: {iconOnly: 'at'}, inline: true,
          subColumns: [{
            field: {name: 'user.phoneNumber', pipe: PhonePipe.instance, css: 'd-block text-info'}, type: 'string', icon: {iconOnly: 'mobile-alt'}
          }]
        },
      ],
    },
    formData: {
      entityIdentifier: {
        className: "Employee",
        idFields: () => [
          {name: "company.companyId", value: 0},
          {name: "user.userId", value: 0, newIfNull: 'User'},
          {name: "employeeId", value: 0},
        ]
      },
      share: {entity: new Employee(), selection: {isEmpty: () => true}},
      header: {
        profileImage: {name: 'user.profileImageUrl'},
        titleField: {name: 'user.fullName'},
        descField: {name: 'user.phoneNumber'},
      },
      fields: [
        {name: "user.username", required: true},
        {name: "user.phoneNumber", required: true},
        {name: "user.fullName", required: true},
        {name: "user.email", required: true},
        {name: "user.role", hidden: true},
        {name: "user.password", required: false, clearOnSuccess: true},
      ]
    }
  })
}
