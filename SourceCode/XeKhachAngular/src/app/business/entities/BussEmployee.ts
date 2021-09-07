// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {XeTableData} from "../../framework/model/XeTableData";
import {Buss} from "./Buss";
import {Employee} from "./Employee";
import {BussType} from "./BussType";
import {Company} from "./Company";
import {User} from "./User";
import {EntityUtil} from "../../framework/util/EntityUtil";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class BussEmployee extends XeEntity {
    static get = (bussEmployee): BussEmployee => EntityUtil.getFromCache("BussEmployee", bussEmployee);
    static meta = EntityUtil.metas.BussEmployee;
    static mapFields = EntityUtil.mapFields['BussEmployee'];
    bussEmployeeId: number;
    bussTypeId: number;
    employeeId: number;
    bussId: number;
    companyId: number;
    userId: number;
    buss: Buss;
    employee: Employee;
    isLock: boolean;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (bussEmployee: BussEmployee): EntityIdentifier<BussEmployee> => ({
    entity: bussEmployee,
    clazz: BussEmployee,
    idFields: [
      {name: "bussEmployeeId"},
      {name: "buss.bussId"},
      {name: "buss.bussType.bussTypeId"},
      {name: "buss.company.companyId"},
      {name: "employee.employeeId"},
      {name: "employee.company.companyId"},
      {name: "employee.user.userId"}
    ]
  })

  static new(option = {}) {
    const bussEmployee = new BussEmployee();
    bussEmployee.buss = new Buss();
    bussEmployee.buss.bussType = new BussType();
    bussEmployee.buss.company = new Company();
    bussEmployee.employee = new Employee();
    bussEmployee.employee.company = new Company();
    bussEmployee.employee.user = new User();
    EntityUtil.assignEntity(option, bussEmployee);
    return bussEmployee;
  }

  static tableData = (option: XeTableData<BussEmployee> = {}, bussEmployee: BussEmployee = BussEmployee.new()): XeTableData<BussEmployee> => {
    const table = BussEmployee._bussEmployeeTable(bussEmployee);
    EntityUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _bussEmployeeTable = (bussEmployee: BussEmployee): XeTableData<BussEmployee> => {
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    const identifier = BussEmployee.entityIdentifier(bussEmployee);
    identifier.idFields.forEach((field, idx) => {
      if (field.name === "employee.user.userId") field['newIfNull'] = true;
      if (field.name === "employee.employeeId") field['newIfNull'] = true;
    });
    return {
      table: {
        basicColumns: [
          {field: {name: 'employee.user.fullName'}, type: "avatarString"},
          {field: {name: 'employee.user.username'}, type: "boldStringRole"},
          {
            field: {name: 'employee.user.email'}, type: "string",
            display: {header: {icon: {iconOnly: 'at'}, inline: true}},
            subColumns: [{
              field: {name: 'employee.user.phoneNumber'},
              type: 'string',
              display: {
                row: {css: 'd-block text-info'},
                header: {icon: {iconOnly: 'mobile-alt'}}
              },
            }]
          },
        ],
      },
      formData: {
        entityIdentifier: identifier,
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
    };
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

