import {XeFormComponent} from "../components/xe-form/xe-form.component";
import {XeBasicFormComponent} from "../components/xe-basic-form/xe-basic-form.component";
import {SelectionModel} from "@angular/cdk/collections";
import {XeEntity, XeEntityClass} from "../../business/entities/XeEntity";
import {MatTableDataSource} from "@angular/material/table";
import {ServiceResult} from "../../business/service/service-result";
import {InputMode, InputTemplate, LabelMode} from "./EnumStatus";
import {XeTableComponent} from "../components/xe-table/xe-table.component";
import {Observable} from "rxjs";

export interface EntityField {
  colSpan?: number;
  name?: string;
  lblKey?: string;
  value?: any;
  required?: boolean;
  hidden?: boolean;
  newOnly?: boolean;
  readonly?: boolean;
  clearOnSuccess?: boolean;
  selectOneMenu?: Observable<[]>;
  newIfNull?: boolean;
  template?: InputTemplate;
  mode?: InputMode;
  action?: {
    preChange?: (currentValue, comingValue, options?) => any,
    postChange?: (currentValue, oldValue, option?) => any
  };
  attachInlines?: string[];
}

export interface PathParam {
  name: string;
  value: string;
}

export interface EntityIdentifier<E extends XeEntity> {
  entity: E;
  clazz?: XeEntityClass<E>;
  idFields?: EntityField[];
  pathParam?: PathParam[];
}

export interface TicketInfo {
  phone: string;
  email: string;
}

export class ShareFormData<E extends XeEntity> {
  tableSource?: MatTableDataSource<E>;
  entity?: E;
  tableEntities?: E[];
  xeForm?: XeFormComponent;
  xeBasicForm?: XeBasicFormComponent<E>;
  selection?: SelectionModel<E> | any;
  custom?: any;
  tableComponent?: XeTableComponent<E>;

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
    noButton?: boolean;
    cancelBtn?: 'close' | 'cancel';
    grid?: boolean;
    bare?: boolean;
    columnNumber?: number;
    labelMode?: LabelMode;
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
    allowDelete?: boolean;
    allowAdd?: boolean;
  };
  mode?: {
    uncheckChanged?: boolean;
  };

  static fullFill(formData: XeFormData<any>) {
    if (!formData.share) formData.share = {};
    ShareFormData.fullFill(formData.share);
    if (!formData.display) formData.display = {};
    if (!formData.action) formData.action = {};
    if (!formData.control) formData.control = {};
    if (!formData.mode) formData.mode = {};
  }
}

