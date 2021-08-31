// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {XeTableData} from "../../framework/model/XeTableData";
import {EntityUtil} from "../../framework/util/EntityUtil";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class Company extends XeEntity {
    static get = (company): Company => EntityUtil.getFromCache("Company", company);
    static meta = EntityUtil.metas.Company;
    static mapFields = EntityUtil.mapFields['Company'];
    companyId: number;
    totalEmployees: number;
    totalBusses: number;
    totalBussEmployees: number;
    totalTrips: number;
    totalSchedules: number;
    companyName: string;
    companyDesc: string;
    hotLine: string;
    isLock: boolean;
    profileImageUrl = this.initProfileImage();
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (company: Company): EntityIdentifier<Company> => ({
    entity: company,
    clazz: Company,
    idFields: [
      {name: "companyId"},
    ]
  })

  static new(option = {}) {
    return new Company();
  }

  static tableData = (option: XeTableData<Company> = {}, company: Company = Company.new()): XeTableData<Company> => {
    const table = Company._companyTable(company);
    EntityUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _companyTable = (company: Company): XeTableData<Company> => {
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    return {
      table: {
        basicColumns: [
          /*0*/ {field: {name: 'companyName'}, type: "avatarString"},
          /*1*/ {field: {name: 'companyDesc'}, type: "string"},
          /*2*/ {field: {name: 'hotLine'}, type: "string", display: {header: {icon: {iconOnly: "phone"}}}},
          /*3*/ {
            field: {name: 'totalTrips'},
            type: "string",
            display: {header: {icon: {iconOnly: "suitcase-rolling"}}}
          },
          /*4*/ {field: {name: 'totalSchedules'}, type: "string", display: {header: {icon: {iconOnly: "headset"}}}},
          /*5*/ {field: {name: 'totalBusses'}, type: "string", display: {header: {icon: {iconOnly: "bus"}}}},
          /*6*/ {field: {name: 'totalEmployees'}, type: "iconOption", display: {row: {icon: {iconAfter: "users"}}}}
        ]
      },
      formData: {
        entityIdentifier: Company.entityIdentifier(company),
        share: {entity: new Company()},
        header: {
          profileImage: {name: 'profileImageUrl'},
          titleField: {name: 'companyName'},
          descField: {name: 'companyDesc'},
        },
        fields: [
          {name: "companyName", required: true},
          {name: "companyDesc", required: true},
          {name: "hotLine", required: false},
        ]
      }
    };
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  }
}

