import {Company} from "./Company";
import {XeEntity} from "./XeEntity";
import {BussType, BussTypeUtil} from "./BussType";
import {XeTableData} from "../../framework/model/XeTableData";
import {AuthUtil} from "../../framework/auth/auth.util";
import {ObjectUtil} from "../../framework/util/object.util";

export class Buss extends XeEntity {
  bussId: number;

  bussTypeId: number;
  bussType: BussType;

  companyId: number;
  company: Company;

  bussLicense: string;
  bussDesc: string;

  totalBussEmployees: number;

  static bussTableData(obj: {}): XeTableData {
    return ObjectUtil.assignEntityTable(obj, Buss._bussTable());
  }

  private static _bussTable = (): XeTableData => ({
    table: {
      basicColumns: [
        { // 0
          field: {name: 'bussType.profileImageUrl'}, type: "avatar"
        },
        { // 1
          field: {name: 'bussType.bussTypeName'}, type: "boldStringRole",
          subColumns: [
            {field: {name: 'bussType.bussTypeDesc', css: 'd-block'}, type: "string"}
          ]
        },
        { // 2
          field: {name: 'bussLicense'}, type: "boldStringRole",
          subColumns: [
            {field: {name: 'bussDesc', css: 'd-block'}, type: "string"}
          ]
        },
        { // 3
          field: {name: 'bussType.totalSeats'},
          type: "iconOption",
          rowIcon: {iconAfter: "couch"}
        },
        { // 4
          field: {name: 'totalBussEmployees'},
          type: "iconOption",
          rowIcon: {iconAfter: "users-cog"}
        },
      ],
    },
    formData: {
      entityIdentifier: {
        className: "Buss",
        idFields: () => [
          {name: "bussTypeId", value: 0},
          {name: "bussId", value: 0},
          {name: "companyId", value: AuthUtil.instance.user?.employee?.companyId}
        ],
      },
      share: {},
      header: {
        profileImage: {name: 'bussType.profileImageUrl', readonly: true},
        titleField: {name: 'bussType.bussTypeName'},
        descField: {name: 'bussType.bussTypeDesc'},
      },
      fields: [
        {name: "bussTypeId", required: true, newOnly: true, selectOneMenu: () => BussTypeUtil.bussTypeIdSelectItems},
        {name: "bussLicense", required: true},
        {name: "bussDesc", required: true},
      ]
    }
  })

}
