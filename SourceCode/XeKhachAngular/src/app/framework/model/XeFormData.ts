import {XeFormComponent} from "../components/xe-form/xe-form.component";
import {XeBasicFormComponent} from "../components/xe-basic-form/xe-basic-form.component";
import {SelectionModel} from "@angular/cdk/collections";
import {XeEntity} from "../../business/entities/XeEntity";
import {MatTableDataSource} from "@angular/material/table";
import { SelectItem } from './SelectItem';

export interface EntityField {
  name: string;
  lblKey?: string;
  value?: any;
  required?: boolean;
  hidden?: boolean;
  newOnly?: boolean;
  readonly?: boolean;
  clearOnSuccess?: boolean;
  css?: string;
  selectOneMenu?: () => SelectItem<any>[];
  newIfNull?: string;
  pipe?: any;
}

export interface EntityIdentifier {
  className?: string;
  idFields?: () => EntityField[];
  idForSearchAll?: {};
}

export interface ShareFormData {
  tableSource?: MatTableDataSource<any>;
  entity?: any;
  tableEntities?: XeEntity[];
  xeForm?: XeFormComponent;
  xeBasicForm?: XeBasicFormComponent;
  selection?: SelectionModel<any> | any;
  custom?: any;
}

export declare interface XeFormData {
  entityIdentifier: EntityIdentifier;
  readonly ?: boolean;
  share?: ShareFormData;
  grid?: boolean;
  header?: {
    profileImage?: EntityField,
    titleField?: EntityField,
    descField?: EntityField,
  };
  fields?: EntityField[];
  onAvatarChange?: (entity) => void;
  onSuccess?: (entity) => void;
}

