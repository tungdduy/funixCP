// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //
import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {ObjectUtil} from "../../framework/util/object.util";
import {XeTableData} from "../../framework/model/XeTableData";
import {BussType} from "./BussType";
import {Company} from "./Company";
import {BussType, BussTypeUtil} from "./BussType";
import {InputTemplate} from "../../framework/model/EnumStatus";
// ____________________ ::TS_IMPORT_SEPARATOR:: ____________________ //

// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //
// ____________________ ::UNDER_IMPORT_SEPARATOR:: ____________________ //

export class Buss extends XeEntity {
    static className = 'Buss';
    static camelName = 'buss';
    static otherMainIdNames = ['bussTypeId', 'companyId'];
    static mainIdName = 'bussId';
    static pkMapFieldNames = ['bussType', 'company'];
    bussTypeId: number;
    bussId: number;
    companyId: number;
    bussType: BussType;
    company: Company;
    totalBussEmployees: number;
    totalSchedules: number;
    bussLicense: string;
    bussDesc: string;
// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

  static entityIdentifier = (buss: Buss): EntityIdentifier<Buss> => ({
    entity: buss,
    clazz: Buss,
    idFields: () => [
      {name: "bussId", value: buss.bussId},
      {name: "bussType.bussTypeId", value: buss.bussType?.bussTypeId},
      {name: "company.companyId", value: buss.company?.companyId}
    ]
  })

  static new(option = {}) {
    const buss = new Buss();
    buss.bussType = new BussType();
    buss.company = new Company();
    ObjectUtil.assignEntity(option, buss);
    return buss;
  }

  static tableData = (option: XeTableData<Buss> = {}, buss: Buss = Buss.new()): XeTableData<Buss> => {
    const table = Buss._bussTable(buss);
    ObjectUtil.assignEntity(option, table);
    XeTableData.fullFill(table);
    return table;
  }

  private static _bussTable = (buss: Buss): XeTableData<Buss> => ({
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
    table: {
      basicColumns: [
        { // 0
          field: {name: 'bussType.profileImageUrl'}, type: "avatar"
        },
        { // 1
          field: {name: 'bussType.bussTypeName'}, type: "boldStringRole",
          subColumns: [
            {field: {name: 'bussType.bussTypeDesc'}, type: "string", display: {row: {css: 'd-block'}}}
          ]
        },
        { // 2
          field: {name: 'company.companyName'}, type: "boldStringRole",
          subColumns: [
            {field: {name: 'company.companyDesc'}, type: "string", display: {row: {css: 'd-block'}}}
          ]
        },
        { // 3
          field: {name: 'bussLicense'}, type: "boldStringRole",
          subColumns: [
            {field: {name: 'bussDesc'}, type: "string"}
          ]
        },
        { // 4
          field: {name: 'bussType.totalSeats'},
          type: "iconOption",
          display: {row: {icon: {iconAfter: "couch"}}}
        },
        { // 5
          field: {name: 'totalBussEmployees'},
          type: "iconOption",
          display: {row: {icon: {iconAfter: "users-cog"}}}
        },
        { // 6
          field: {name: 'totalSchedules'},
          type: "iconOption",
          display: {row: {icon: {iconAfter: "clock"}}}
        },
      ],
    },
    formData: {
      entityIdentifier: Buss.entityIdentifier(buss),
      header: {
        profileImage: {name: 'bussType.profileImageUrl', readonly: true},
        titleField: {name: 'bussType.bussTypeName'},
        descField: {name: 'bussType.bussTypeDesc'},
        subFields: [
          {name: 'bussLicense'}, {name: 'bussDesc'}
        ]
      },
      fields: [
        {name: "bussTypeId", template: InputTemplate.selectOneMenu,
          required: true,
          newOnly: true,
          selectOneMenu: () => BussTypeUtil.bussTypeSelectItem},
        {name: "bussLicense", required: true},
        {name: "bussDesc", required: true},
      ]
    }
// ____________________ ::ENTITY_TABLE_SEPARATOR:: ____________________ //
  })
}
