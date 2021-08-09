import {XeEntity} from "./XeEntity";
import {EntityIdentifier} from "../../framework/model/XeFormData";
import {XeTableData} from "../../framework/model/XeTableData";
import {ObjectUtil} from "../../framework/util/object.util";

export class Company extends XeEntity {
  companyId: number;

  companyName: string;
  companyDesc: string;
  hotLine: string;

  totalEmployees: number;
  totalBusses: number;
  totalBussEmployees: number;
  totalTrips: number;
  totalSchedules: number;

  profileImageUrl = this.initProfileImage();

  static identifier: EntityIdentifier = {
    className: "Company",
    idFields: () => [
      {name: "companyId", value: 0}
    ],
    idForSearchAll: {companyId: 0}
  };

  static companyTable (option: {}): XeTableData {
    return ObjectUtil.assignEntityTable(option, Company._companyTable());
  }

  private static _companyTable = (): XeTableData => ({
    table: {
      basicColumns: [
        /*0*/ {field: {name: 'companyName'}, type: "avatarString"},
        /*1*/ {field: {name: 'companyDesc'}, type: "string"},
        /*2*/ {field: {name: 'hotLine'}, type: "string", icon: {iconOnly: "phone"}},
        /*3*/ {field: {name: 'totalTrips'}, type: "string", icon: {iconOnly: "suitcase-rolling"}},
        /*4*/ {field: {name: 'totalSchedules'}, type: "string", icon: {iconOnly: "headset"}},
        /*5*/ {field: {name: 'totalBusses'}, type: "string", icon: {iconOnly: "bus"}},
        /*6*/ {field: {name: 'totalEmployees'}, type: "iconOption", rowIcon: {iconAfter: "users"}}
      ]
    },
    formData: {
      entityIdentifier: {
        className: "Company",
        idFields: () => [{name: "companyId", value: 0}],
      },
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
  })

}
