import {XeTableData} from "../../framework/model/XeTableData";
import {EntityIdentifier} from "../../framework/model/XeFormData";

export declare interface XeEntityClass<E extends XeEntity> {
  otherMainIdNames: string[];
  mainIdName: string;
  pkMapFieldNames: string[];
  className: string;
  camelName: string;
  new: () => E;
  entityIdentifier: (e: E) => EntityIdentifier<E>;
  tableData: (option: XeTableData<E>, e: E) => XeTableData<E>;
}

export abstract class XeEntity {
  profileImageUrl: string;

  initProfileImage() {
    return "http://robohash.org/xekhach/" + this.constructor.name + "/0";
  }

}
