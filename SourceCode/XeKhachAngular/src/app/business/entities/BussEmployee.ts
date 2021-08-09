import {Employee} from "./Employee";
import {Buss} from "./Buss";
import {XeEntity} from "./XeEntity";
import {XeTableData} from "../../framework/model/XeTableData";
import {ObjectUtil} from "../../framework/util/object.util";

export class BussEmployee extends XeEntity {

  bussEmployeeId: number;

  employeeId: number;
  employee: Employee;

  bussId: number;
  buss: Buss;

  isLock: boolean;

  static bussEmployeeTable = (option: {}): XeTableData => {
    return ObjectUtil.assignEntityTable(option, BussEmployee._bussEmployeeTable());
  }

  private static _bussEmployeeTable = (): XeTableData => ({
    table: {
      basicColumns: [
        {field: {name: 'employee.user.fullName'}, type: "avatarString"},
        {field: {name: 'employee.user.username'}, type: "boldStringRole"},
        {
          field: {name: 'employee.user.email'}, type: "string", icon: {iconOnly: 'at'}, inline: true,
          subColumns: [{
            field: {name: 'employee.user.phoneNumber', css: 'd-block text-info'}, type: 'string', icon: {iconOnly: 'mobile-alt'},
          }]
        },
      ],
    },
    formData: {
      entityIdentifier: {
        className: "BussEmployee",
        idFields: () => [
          {name: "bussEmployeeId", value: 0},
          {name: "buss.bussId", value: 0},
          {name: "buss.bussType.bussTypeId", value: 0},

          {name: "employee.employeeId", value: 0, newIfNull: 'Employee'},
          {name: "employee.companyId", value: 0},

          {name: "employee.user.userId", value: 0, newIfNull: 'User'},
        ]
      },
      share: {},
      header: {
        profileImage: {name: 'employee.user.profileImageUrl'},
        titleField: {name: 'employee.user.fullName'},
        descField: {name: 'employee.user.phoneNumber'},
      },
      fields: [
        {name: "employee.user.username", required: true},
        {name: "employee.user.phoneNumber", required: true},
        {name: "employee.user.fullName", required: true},
        {name: "employee.user.email", required: true},
        {name: "employee.user.role", hidden: true},
        {name: "employee.user.password", required: false, clearOnSuccess: true},
      ]
    }
  })
}
