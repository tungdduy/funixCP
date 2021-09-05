import {XeTableData} from "../../framework/model/XeTableData";
import {EntityIdentifier} from "../../framework/model/XeFormData";

export declare interface ClassMeta {
  capName: string;
  camelName: string;
  pkMetas: () => ClassMeta[];
  mainIdName: string;
}

export declare interface XeEntityClass<E extends XeEntity> {
  new: () => E;
  entityIdentifier: (e: E) => EntityIdentifier<E>;
  tableData: (option: XeTableData<E>, e: E) => XeTableData<E>;
  meta: ClassMeta;
  mapFields: any;
}

export abstract class XeEntity {
  profileImageUrl: string;
  isFilled = false;
  initProfileImage() {
    return "https://robohash.org/xekhach/" + this.constructor.name + "/0";
  }

}
