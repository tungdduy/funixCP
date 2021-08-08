import {XeFormComponent} from "../../framework/components/xe-form/xe-form.component";
import {XeBasicFormComponent} from "../../framework/components/xe-basic-form/xe-basic-form.component";
import {SelectionModel} from "@angular/cdk/collections";
import {XeEntity} from "../entities/xe-entity";
import {MatTableDataSource} from "@angular/material/table";

export interface EntityField {
  name: string;
  value?: any;
  required?: boolean;
  hidden?: boolean;
  newOnly?: boolean;
  clearOnSuccess?: boolean;
  css?: string;
}

export interface EntityIdentifier {
  className?: string;
  idFields?: () => EntityField[];
}

export interface ShareFormData {
  tableSource?: MatTableDataSource<any>;
  entity: any;
  entities?: XeEntity[];
  xeForm?: XeFormComponent;
  xeBasicForm?: XeBasicFormComponent;
  selection?: SelectionModel<any>;
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
  fields: EntityField[];
  onAvatarChange?: (entity) => void;
  onSuccess?: (entity) => void;
}

