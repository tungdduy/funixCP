import {XeFormComponent} from "../components/xe-form/xe-form.component";
import {XeBasicFormComponent} from "../components/xe-basic-form/xe-basic-form.component";
import {SelectionModel} from "@angular/cdk/collections";
import {XeEntity, XeEntityClass} from "../../business/entities/XeEntity";
import {MatTableDataSource} from "@angular/material/table";
import {SelectItem} from './SelectItem';
import {ServiceResult} from "../../business/service/service-result";
import {InputMode, InputTemplate} from "./EnumStatus";

export interface EntityField {
  name?: string;
  lblKey?: string;
  value?: any;
  required?: boolean;
  hidden?: boolean;
  newOnly?: boolean;
  readonly?: boolean;
  clearOnSuccess?: boolean;
  selectOneMenu?: () => SelectItem<any>[];
  newIfNull?: XeEntityClass<any>;
  template?: InputTemplate;
  mode?: InputMode;
}

export interface EntityIdentifier<E extends XeEntity> {
  entity: E;
  clazz?: XeEntityClass<E>;
  idFields?: () => EntityField[];
}

export class ShareFormData<E extends XeEntity> {
  tableSource?: MatTableDataSource<E>;
  entity?: E;
  tableEntities?: E[];
  xeForm?: XeFormComponent;
  xeBasicForm?: XeBasicFormComponent<E>;
  selection?: SelectionModel<E> | any;
  custom?: any;

  static fullFill(shareData: ShareFormData<any>) {
    if (!shareData.entity) shareData.entity = {};
    if (!shareData.tableEntities) shareData.tableEntities = [];
    if (!shareData.custom) shareData.custom = [];
  }
}

export class XeFormData<E extends XeEntity> {
  entityIdentifier?: EntityIdentifier<E>;
  share?: ShareFormData<E>;
  display?: {
    cancelBtn?: 'close' | 'cancel';
    grid?: boolean;
    bare?: boolean;
    columnNumber?: number;
  };
  header?: {
    profileImage?: EntityField,
    titleField?: EntityField,
    descField?: EntityField,
    subFields?: EntityField[]
  };
  fields?: EntityField[];
  action?: {
    postPersist?: (entity) => any;
    postUpdateProfile?: (entity) => any;
    postUpdate?: (entity) => any;
    preEdit?: (entity) => any;
    postCancel?: (entity) => any;
    preSubmit?: (entity) => ServiceResult;
    postDelete?: (entity) => any;
  };
  control?: {
    muteOnSuccess?: boolean;
    readMode?: boolean;
    allowDelete?: boolean;
    allowAdd?: boolean;
  };

  static fullFill(formData: XeFormData<any>) {
    if (!formData.share) formData.share = {};
    ShareFormData.fullFill(formData.share);
    if (!formData.display) formData.display = {};
    if (!formData.action) formData.action = {};
    if (!formData.control) formData.control = {};
  }
}
