// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {XeTableData} from "../../framework/model/XeTableData";
import {Company} from "./Company";
import {User} from "./User";
import {InputTemplate} from "../../framework/model/EnumStatus";
import {EntityUtil} from "../../framework/util/EntityUtil";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class Employee extends XeEntity {
    static get = (employee): Employee => EntityUtil.getFromCache("Employee", employee);
    static meta = EntityUtil.metas.Employee;
    static mapFields = EntityUtil.mapFields['Employee'];
    employeeId: number;
    companyId: number;
    userId: number;
    company: Company;
    user: User;
    countBusses: number;
    isLock: boolean;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (employee: Employee): EntityIdentifier<Employee> => ({
    entity: employee,
    clazz: Employee,
    idFields: [
      {name: "employeeId"},
      {name: "company.companyId"},
      {name: "user.userId"}
    ]
  })

  static new(option = {}) {
    const employee = new Employee();
    employee.company = new Company();
    employee.user = new User();
    EntityUtil.assignEntity(option, employee);
    return employee;
  }

  static tableData = (option: XeTableData<Employee> = {}, employee: Employee = Employee.new()): XeTableData<Employee> => {
    const table = Employee._employeeTable(employee);
    EntityUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _employeeTable = (employee: Employee): XeTableData<Employee> => {
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    const identifier = Employee.entityIdentifier(employee);
    identifier.idFields.forEach((field, idx) => {
      if (field.name === "user.userId") field['newIfNull'] = true;
    });
    return {
      table: {
        basicColumns: [
          // 0
          {field: {name: 'user.fullName'}, type: "avatarString"},
          // 1
          {field: {name: 'user.username'}, type: "boldStringRole"},
          { // 2
            field: {name: 'user.email'}, type: "string", display: {header: {icon: {iconOnly: 'at'}, inline: true}},
            subColumns: [{
              field: {name: 'user.phoneNumber', template: InputTemplate.phone},
              type: 'string',
              display: {header: {icon: {iconOnly: 'mobile-alt'}}, row: {css: 'd-block text-info'}}
            }]
          },
        ],
      },
      formData: {
        entityIdentifier: identifier,
        share: {entity: Employee.new(), selection: {isEmpty: () => true}},
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
    };
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

